package UserPackage;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest
{
    User testUser;
    User testUser2;
    String testUsername;
    String testPassword;
    boolean testSimUser;


    @BeforeEach
    void setUp()
    {
        testUser = new User("Imke", "test", true);
        testUser2 = new User();
    }

    @Test
    void setUsername()
    {
        // create a username
        String username = "Natascha";

        // set testUsername
        testUser.setUsername(username);

        // check if testUsername and username are equal
        Assert.assertEquals(username,testUser.getUsername());

    }

    @Test
    void getUsername()
    {
        // tested in method: setUsername().
    }

    @Test
    void getPassword()
    {
        // tested in method: setPassword().
    }

    @Test
    void setPassword()
    {
        // Create a password
        String password = "Life";

        //Set testPassword
        testUser.setPassword(password);

        // check if testPassword and password are equal
        Assert.assertEquals(password, testUser.getPassword());
    }

    @Test
    void setSimUser()
    {
        // create simUser
        boolean simUser = false;

        // set testSimuser
        testUser.setSimUser(simUser);

        //check if testSimUser and simUser are equal FALSE
        Assert.assertEquals(simUser, testUser.isSimUser());
    }

    @Test
    void isSimUser()
    {
        // tested in method: setSimUser().
    }
}