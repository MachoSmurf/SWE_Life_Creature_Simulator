package UserPackage;
import DataMediatorPackage.DatabaseMediator;

/**
 * Controls the login/logout procedure and stores the user object
 */
public class UserController extends User implements IUserController
{
    private boolean isLoggedIn;

    public UserController(String username, String password, boolean simUser)
    {
        super(username, password, simUser);
    }

    @Override
    public boolean Login(String username, String password)
    {
        DatabaseMediator databaseMediator = new DatabaseMediator();

        try
        {
            databaseMediator.loadUser(username, password);
            isLoggedIn = true;
            return isLoggedIn;
        }
        catch(Exception e)
        {
            isLoggedIn = false;
            return isLoggedIn;
        }
    }

    @Override
    public void Logout()
    {
        isLoggedIn = false;

    }

    @Override
    public boolean getRights()
    {
        User user = new User();
        if (user.isSimUser() == true)
        {
            return true;
        }
        else
            {
                return false;
            }

    }
}
