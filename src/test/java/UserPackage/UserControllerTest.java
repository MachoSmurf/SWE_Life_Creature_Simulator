package UserPackage;

import DataMediatorPackage.DatabaseMediator;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserControllerTest
{
    private User testUser;
    private UserController userController;
    private boolean result;


    @BeforeEach
    void setUp()
    {
        testUser = new User("Imke", "test", true);
        userController = new UserController("Imke", "test", true);

    }
    @Test
    void loginTrue()
    {
        result = userController.Login("Imke", "test");
        boolean expResult = true;

        Assert.assertEquals(expResult, result);
    }

    @Test
    void loginFalse()
    {
        result = userController.Login("Imke2", "test2");

        boolean expResult = false;

        Assert.assertEquals(expResult, result);
    }

    @Test
    void logout() {
    }

    @Test
    void getRights() {
    }
}