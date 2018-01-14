package UserPackage;

import DataMediatorPackage.DatabaseMediator;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserControllerTest {
    private User testUser;
    private User testUser2;
    private UserController testUserController;
    private UserController testUserController2;
    private UserController testUserController3;
    private boolean testResult;
    private boolean expResult;
    private List<User> testAllUsers;
    private List<User> testResultList;
    private DatabaseMediator testdatabaseMediator;


    @BeforeEach
    void setUp() {

        // Create a test user and a testUserController
        testUser = new User("Imke", "Test", true);
        testUser2 = new User("Natascha", "Test2", false);
        testUserController = new UserController("Imke", "Test", true);
        testUserController2 = new UserController("Natascha", "Test2", false);
        testUserController3 = new UserController("Kai", "Test3", true);
        testAllUsers = new ArrayList<User>();

        testAllUsers.add(testUser);
        testAllUsers.add(testUser2);

    }

    @Test
    void loginTrue() {
        // Login with username and password of the testUser
        testResult = testUserController.Login("Imke", "Test");
        // Expected result
        expResult = true;

        // check or expResult and testResult are equal
        Assert.assertEquals(expResult, testResult);
    }

    @Test
    void loginFalse() {
        // login with false username and password.
        testResult = testUserController.Login("Imke2", "test2");

        // expected result
        expResult = false;

        //check or expResult and testResult are equal
        Assert.assertEquals(expResult, testResult);
    }

    @Test
    void logout() {
        // login with username and password of the testUser
        testUserController.Login("Imke", "Test");

        // Logout the testUser
        testResult = testUserController.Logout();

        // expected result
        expResult = false;

        // check of expectedResult and testResult are equal
        Assert.assertEquals(expResult, testResult);

    }

    @Test
    void getRightsTrue() {
        // get rights of de user
        testResult = testUserController.getRights();

        //expected result
        expResult = true;

        // check or expResult and testResult are equal
        Assert.assertEquals(expResult, testResult);

    }

    @Test
    void getRightsFalse() {
        // get rights of de user
        testResult = testUserController2.getRights();

        //expected result
        expResult = false;

        // check or expResult and testResult are equal
        Assert.assertEquals(expResult, testResult);

    }


    @Test
    void getUsers() {
        // get all users of the simulation
        testResultList = testUserController.getUsers();
        boolean areEqual;

        // Check or testAllUsers and testResultList are equal
        if (testResultList.containsAll(testAllUsers)) {
            areEqual = true;
            Assert.assertTrue(areEqual);

        } else {
            areEqual = false;
            Assert.assertFalse(areEqual);
        }
    }

    @Test
    void saveUserNew() {
        String testUsername = "Kai";
        String testPassword = "Test3";
        boolean testIsSimUser = true;
        User testUser3 = new User(testUsername, testPassword, testIsSimUser);

        testdatabaseMediator = new DatabaseMediator();

        testUserController3.saveUser(testUsername, testPassword, testIsSimUser);
        boolean areEqual;
        User dbUser = testdatabaseMediator.loadUser(testUsername, testPassword);
        if (testUser3.equals(dbUser)) {
            areEqual = true;
            Assert.assertTrue(areEqual);
        } else {
            areEqual = false;
            Assert.assertFalse(areEqual);
        }


    }

    @Test
    void saveUserUpdate() {
        String testUsername = "Kai";
        String testPassword = "Test4";
        boolean testIsSimUser = false;
        User testUser3 = new User(testUsername, testPassword, testIsSimUser);

        testdatabaseMediator = new DatabaseMediator();

        testUserController3.saveUser(testUsername, testPassword, testIsSimUser);
        boolean areEqual;
        User dbUser = testdatabaseMediator.loadUser(testUsername, testPassword);
        if (testUser3.equals(dbUser)) {
            areEqual = true;
            Assert.assertTrue(areEqual);
        } else {
            areEqual = false;
            Assert.assertFalse(areEqual);
        }


    }
}