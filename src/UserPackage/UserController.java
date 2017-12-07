package UserPackage;


/**
 * Controls the login/logout procedure and stores the user object
 */
public class UserController implements IUserController {
    @Override
    public boolean Login(String username, String password) {
        return false;
    }

    @Override
    public void Logout() {

    }

    @Override
    public boolean getRights() {
        return false;
    }
}
