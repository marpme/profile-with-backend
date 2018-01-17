package net.ziemers.swxercise.lg.user.service;

import java.util.Collections;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import javax.inject.Inject;
import javax.naming.InitialContext;

import net.ziemers.swxercise.db.dao.user.UserDao;
import net.ziemers.swxercise.db.utils.JpaTestUtils;
import net.ziemers.swxercise.lg.model.user.User;
import net.ziemers.swxercise.lg.testdatabuilder.user.UserDtoTestDataBuilder;
import net.ziemers.swxercise.lg.user.dto.UserDto;

import static org.junit.Assert.*;

import net.ziemers.swxercise.lg.user.service.User.SessionContext;
import net.ziemers.swxercise.lg.user.service.User.UserService;
import org.jglue.cdiunit.CdiRunner;
import org.jglue.cdiunit.InRequestScope;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Testing your Java CDI application with CDI-Unit couldn't be easier.
 * Just specify @RunWith(CdiRunner.class) on your JUnit test class
 * to enable injection directly into the test class.
 *
 * To test classes in isolation we shouldn't be using their dependencies.
 * Instead we should be using a mock. There are many mocking libraries out
 * there, however CDI-Unit has extra support for Mockito @Mock annotations.
 *
 * Quelle: http://jglue.org/cdi-unit-user-guide/
 */
@RunWith(CdiRunner.class)
public class UserServiceTest extends JpaTestUtils {

    private static String USERNAME_TEST = "username_test";
    private static String EXISTING_USERNAME_TEST = "username_profile";
    private static String EXISTING_PASSWORD_TEST = "secret";
    private static Long EXISTING_USER_ID = 2L;

    private static boolean dbInitialized;

    private UserDto userDto;

    private boolean actual;

    private Collection<User> actualUsers;

    private User actualUser;

    @Inject
    private UserDao userDao;

    @Inject
    private SessionContext sessionContext;

    @Inject
    private UserService underTest;

    @Before
    public void setUp() throws Exception {
        // wird vor jedem Test ausgeführt

        if (!dbInitialized) {
            cleanDb();
            initDbWith("UserServiceTestData.xml");
            dbInitialized = true;
        }
    }

    @After
    public void tearDown() throws Exception {
        // wird nach jedem Test ausgeführt

        /*
         * java.lang.IllegalStateException: WELD-ENV-002000: Weld SE container STATIC_INSTANCE is already running!
         *
         * Jetty initialisiert einen eigenen Weld SE Container, der mit demjenigen
         * vom CDI-Runner kollidiert. Wir müssen ihn deshalb hier entfernen.
         * Quelle: https://groups.google.com/forum/#!topic/cdi-unit/2e-XnyduXcs
         */
        InitialContext initialContext = new InitialContext();
        initialContext.unbind("java:comp/BeanManager");
    }

    /*
     * In order to inject @SessionScoped beans, one has to annotate the function or class with @InRequestScope
     * as only this annotation guarantees to have the session scope active always.
     * Quelle: https://github.com/BrynCooke/cdi-unit/issues/82
     */
    @Test
    @InRequestScope
    public void testLoginUserSucceeds() {

        given()
                .userDto(EXISTING_USERNAME_TEST);

        when()
                .loginUser(EXISTING_PASSWORD_TEST);

        then()
                .assertLoginUserSucceeded();
    }

    @Test
    @InRequestScope
    public void testLogoutUserSucceeds() {

        given()
                .userDto(EXISTING_USERNAME_TEST)
                .loginUser(EXISTING_PASSWORD_TEST);

        when()
                .logoutUser();

        then()
                .assertLogoutUserSucceeded();
    }

    @Test
    public void testFindUserByIdSucceeds() {

        when()
                .findUser(EXISTING_USER_ID);

        then()
                .assertFindUserByIdSucceeded(EXISTING_USER_ID);
    }

    @Test
    @InRequestScope
    public void testFindSessionUserSucceeds() {

        given()
                .userDto(EXISTING_USERNAME_TEST)
                .loginUser(EXISTING_PASSWORD_TEST);

        when()
                .findUser();

        then()
                .assertFindUserByIdSucceeded(EXISTING_USER_ID);
    }

    @Test
    public void testFindAllUsersSucceeds() {

        when()
                .findAllUsers();

        then()
                .assertFindAllUsersSucceeded();
    }

    @Test
    public void testCreateUserSucceeds() {

        given()
                .userDto(USERNAME_TEST);

        when()
                .createUser();

        then()
                .assertCreateUserSucceeded();
    }

    @Test
    public void testCreateAlreadyExistingUserFails() {

        given()
                .userDto(EXISTING_USERNAME_TEST);

        when()
                .createUser();

        then()
                .assertCreateUserFailed();
    }

    @Test
    public void testUpdateUserDoesntUpdateUsername() {

        given()
                .userDto(USERNAME_TEST);

        when()
                .updateUser(EXISTING_USER_ID);

        then()
                .assertUpdateUserSucceeded();
    }

    @Test
    public void testDeleteUserByIdSucceeds() {
        // TODO Test ist noch zu implementieren
    }

    @Test
    public void testDeleteUnknownUserByIdFails() {
        // TODO Test ist noch zu implementieren
    }

    @Test
    public void testDeleteSessionUserSucceeds() {
        // TODO Test ist noch zu implementieren
    }

    @Test
    @InRequestScope
    public void testIsNotLoggedInUserAllowedWithNotLoggedInRightSucceeds() {

        when()
                .isUserAllowed(new HashSet<>(Collections.singletonList("NOT_LOGGED_IN")));

        then()
                .assertIsUserAllowedSucceeded();
    }

    @Test
    @InRequestScope
    public void testIsNotLoggedInUserAllowedWithLoggedInRightFails() {

        when()
                .isUserAllowed(new HashSet<>(Collections.singletonList("LOGGED_IN")));

        then()
                .assertIsUserAllowedFailed();
    }

    @Test
    @InRequestScope
    public void testIsSessionUserAllowedWithLoggedInRightSucceeds() {

        given()
                .userDto(EXISTING_USERNAME_TEST)
                .loginUser(EXISTING_PASSWORD_TEST);

        when()
                .isUserAllowed(new HashSet<>(Collections.singletonList("LOGGED_IN")));

        then()
                .assertIsUserAllowedSucceeded();
    }

    @Test
    @InRequestScope
    public void testIsSessionUserAllowedWithNotLoggedInRightFails() {

        given()
                .userDto(EXISTING_USERNAME_TEST)
                .loginUser(EXISTING_PASSWORD_TEST);

        when()
                .isUserAllowed(new HashSet<>(Collections.singletonList("NOT_LOGGED_IN")));

        then()
                .assertIsUserAllowedFailed();
    }

    @Test
    @InRequestScope
    public void testIsAdminUserAllowedWithAdminRightSucceeds() {

      given()
              .userDto(EXISTING_USERNAME_TEST)
              .loginUser(EXISTING_PASSWORD_TEST);

      when()
              .isUserAllowed(new HashSet<>(Collections.singletonList("ADMIN")));

      then()
              .assertIsUserAllowedSucceeded();
    }

    @Test
    @InRequestScope
    public void testIsUserWithUnknownRoleAllowedFails() {

      given()
              .userDto(EXISTING_USERNAME_TEST)
              .loginUser(EXISTING_PASSWORD_TEST);

      when()
              .isUserAllowed(new HashSet<>(Collections.singletonList("UNKNOWN_ROLE")));

      then()
              .assertIsUserAllowedFailed();
    }

    // given

    private UserServiceTest given() {
        return this;
    }

    private UserServiceTest userDto(final String username) {
        userDto = new UserDtoTestDataBuilder()
                .withUsername(username)
                .build();
        return this;
    }

    // when

    private UserServiceTest when() {
        return this;
    }

    private UserServiceTest loginUser(final String password) {
        final String SESSION_ID = "testSessionId";

        userDto.withPassword(password);
        actual = underTest.loginUser(userDto, SESSION_ID);
        return this;
    }

    private UserServiceTest logoutUser() {
        actual = underTest.logoutUser();
        return this;
    }

    private UserServiceTest createUser() {
        txBegin();
        actual = underTest.createUser(userDto);
        txCommit();

        return this;
    }

    private UserServiceTest updateUser(final Long id) {
        txBegin();
        actual = underTest.updateUser(id, userDto);
        txCommit();

        return this;
    }

    private UserServiceTest findUser(final Long id) {
        actualUser = underTest.findUser(id);
        return this;
    }

    private UserServiceTest findUser() {
        actualUser = underTest.findUser();
        return this;
    }

    private UserServiceTest findAllUsers() {
        actualUsers = underTest.findAllUsers();
        return this;
    }

    private UserServiceTest isUserAllowed(final Set<String> rightsSet) {
        actual = underTest.isUserAllowed(rightsSet);
        return this;
    }

    // then

    private UserServiceTest then() {
        return this;
    }

    private void assertLoginUserSucceeded() {
        // die Login-Methode lieferte Erfolg zurück, und es gibt einen angemeldeten Benutzer
        assertTrue(actual);
        assertNotNull(sessionContext.getUser());
    }

    private void assertLogoutUserSucceeded() {
      // die Logout-Methode lieferte Erfolg zurück, und es gibt keinen angemeldeten Benutzer
        assertTrue(actual);
        assertNull(sessionContext.getUser());
    }

    private void assertFindUserByIdSucceeded(final Long expectedId) {
        assertEquals(expectedId, actualUser.getId());
    }

    private void assertFindAllUsersSucceeded() {
        // in unseren Testdaten befinden sich zwei Benutzer
        final int EXISTING_USERS_COUNT = 2;
        assertEquals(EXISTING_USERS_COUNT, actualUsers.size());
    }

    private void assertCreateUserSucceeded() {
        // wir suchen den soeben erstellten Benutzer; wenn er existiert, ist alles gut
        final User user = userDao.findByUsername(USERNAME_TEST);
        assertNotNull(user);
    }

    private void assertCreateUserFailed() {
        // es darf kein neuer Benutzer mit identischem Benutzernamen erstellt worden sein
        assertFalse(actual);
    }

    private void assertUpdateUserSucceeded() {
        // wir suchen den soeben aktualisierten Benutzer; wenn sein Benutzername unverändert ist, ist alles gut
        final User user = userDao.findById(EXISTING_USER_ID);
        assertEquals(EXISTING_USERNAME_TEST, user.getProfile().getUsername());
    }

    private void assertIsUserAllowedSucceeded() {
        assertTrue(actual);
    }

    private void assertIsUserAllowedFailed() {
        assertFalse(actual);
    }

}
