package net.ziemers.swxercise.ui;

import java.util.ArrayList;
import java.util.Collection;

import net.ziemers.swxercise.lg.model.user.User;
import net.ziemers.swxercise.lg.testdatabuilder.user.UserDtoTestDataBuilder;
import net.ziemers.swxercise.lg.user.dto.UserDto;
import net.ziemers.swxercise.lg.user.service.User.UserService;
import net.ziemers.swxercise.ui.enums.ResponseState;

import net.ziemers.swxercise.ui.utils.RestResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.slf4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserViewControllerTest {

    private static String USER_FIRSTNAME = "Hein";
    private static Long EXISTING_USER_ID = 2L;

    /*
     * You have the @InjectMocks annotation which tries to do constructor,
     * method and field dependency injection based on the type.
     */
    @InjectMocks
    private UserViewController underTest;

    // das Logging interessiert uns nicht; deshalb mocken wir es weg
    @Mock
    private Logger logger;

    /*
     * Mockito allows to configure the return values of its mocks via a fluent API.
     * Unspecified method calls return "empty" values
     */
    @Mock
    private UserService userService;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpSession session;

    private UserDto userDto;

    private RestResponse actual;

    private Collection<User> actualCollection;

    private User actualUser;

    @Test
    public void testJUnitFrameworkSucceeds() {
        assertTrue(true);
    }

    /**
     * Hier sieht man eine erwartete Exception
     */
    @Test(expected = AssertionError.class)
    public void testJUnitFrameworkThrowsException() {
        assertTrue(false);
    }

    @Test
    public void testGetAllUsersReturnsUsersJson() {

        doing()
                .getAllUsers();

        then()
                .assertGetAllUsersSucceeded();
    }

    @Test
    public void testGetUserByIdReturnsUserJson() {

        doing()
                .getUserById(EXISTING_USER_ID);

        then()
                .assertGetUserByIdSucceeded();
    }

    @Test
    public void testGetSessionUserReturnsSessionUserJson() {

        doing()
                .getUser();

        then()
                .assertGetUserSucceeded();
    }

    @Test
    public void testCreateUserSucceeds() {

        given()
                .userDto();

        doing()
                .createUser(true);

        then()
                .assertCreateUserSucceeded();
    }

    @Test
    public void testCreateUserFails() {

        given()
                .userDto();

        doing()
                .createUser(false);

        then()
                .assertCreateUserFailed();
    }

    @Test
    public void testUpdateSessionUserSucceeds() {

        given()
                .userDto();

        doing()
                .updateUser();

        then()
                .assertUpdateUserSucceeded();
    }

    @Test
    public void testUpdateUserByIdSucceeds() {

        given()
                .userDto();

        doing()
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
    public void testLoginUserSucceeds() {

        given()
                .userDto();

        doing()
                .loginUser(true);

        then()
                .assertLoginUserSucceeded();
    }

    @Test
    public void testLoginUserFails() {

        given()
                .userDto();

        doing()
                .loginUser(false);

        then()
                .assertLoginUserFailed();
    }

    @Test
    public void testLogoutUserSucceeds() {

        doing()
                .logoutUser();

        then()
                .assertLogoutUserSucceeded();
    }

    // given

    private UserViewControllerTest given() {
        return this;
    }

    private UserViewControllerTest userDto() {
        userDto = new UserDtoTestDataBuilder().build();
        return this;
    }

    // doing (when)

    private UserViewControllerTest doing() {
        return this;
    }

    private void createUser(final boolean result) {
        /*
         * The when().thenReturn() method chain is used to specify
         * a return value for a method call with pre-defined parameters.
         */
        when(userService.createUser(userDto)).thenReturn(result);

        actual = underTest.createUser(userDto);
    }

    private void getAllUsers() {
        when(userService.findAllUsers()).thenReturn(new ArrayList<User>());

        actualCollection = underTest.getAllUsers();
    }

    private void getUserById(final Long id) {
        when(userService.findUser(id)).thenReturn(new User(USER_FIRSTNAME, ""));

        actualUser = underTest.getUser(id);
    }

    private void getUser() {
        when(userService.findUser()).thenReturn(new User(USER_FIRSTNAME, ""));

        actualUser = underTest.getUser();
    }

    private void updateUser() {
        when(userService.updateUser(userDto)).thenReturn(true);

        actual = underTest.updateUser(userDto);
    }

    private void updateUser(final Long id) {
        when(userService.updateUser(id, userDto)).thenReturn(true);

        actual = underTest.updateUser(id, userDto);
    }

    private void loginUser(final boolean result) {
        final String SESSION_ID = "testSessionId";

        when(userService.loginUser(userDto, SESSION_ID)).thenReturn(result);

        // während der Tests besitzen wir keinen HTTP-Request, also mocken wir ihn weg
        when(request.getSession(false)).thenReturn(session);
        when(session.getId()).thenReturn(SESSION_ID);

        actual = underTest.loginUser(request, userDto);
    }

    private void logoutUser() {
        when(userService.logoutUser()).thenReturn(true);

        actual = underTest.logoutUser();
    }

    // then

    private UserViewControllerTest then() {
        return this;
    }

    private void assertCreateUserSucceeded() {
        final RestResponse expected = new RestResponse();
        assertEquals(expected, actual);
    }

    private void assertCreateUserFailed() {
        final RestResponse expected = new RestResponse(ResponseState.ALREADY_EXISTING);
        assertEquals(expected, actual);
    }

    private void assertGetAllUsersSucceeded() {
        final Collection<User> expectedCollection = new ArrayList<>();
        assertEquals(expectedCollection, actualCollection);
    }

    private void assertGetUserByIdSucceeded() {
        assertEquals(USER_FIRSTNAME, actualUser.getFirstname());
    }

    private void assertGetUserSucceeded() {
        assertEquals(USER_FIRSTNAME, actualUser.getFirstname());
    }

    private void assertUpdateUserSucceeded() {
        final RestResponse expected = new RestResponse();
        assertEquals(expected, actual);
    }

    private void assertLoginUserSucceeded() {
        final RestResponse expected = new RestResponse();
        assertEquals(expected, actual);
    }

    private void assertLoginUserFailed() {
        final RestResponse expected = new RestResponse(ResponseState.FAILED);
        assertEquals(expected, actual);
    }

    private void assertLogoutUserSucceeded() {
        final RestResponse expected = new RestResponse();
        assertEquals(expected, actual);
    }

}
