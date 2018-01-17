package net.ziemers.swxercise.ui;

import net.ziemers.swxercise.lg.user.service.User.RoleService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class RoleViewControllerTest {

    /*
     * You have the @InjectMocks annotation which tries to do constructor,
     * method and field dependency injection based on the type.
     */
    @InjectMocks
    private RoleViewController underTest;

    /*
     * Mockito allows to configure the return values of its mocks via a fluent API.
     * Unspecified method calls return "empty" values
     */
    @Mock
    private RoleService roleService;

    @Test public void testSomething() {

        given();

        doing();

        then();
    }

    // given

    private RoleViewControllerTest given() {
        return this;
    }

    // doing (when)

    private RoleViewControllerTest doing() {
        return this;
    }

    // then

    private RoleViewControllerTest then() {
        return this;
    }

}
