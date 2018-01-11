package UserPackage;

import DataMediatorPackage.DatabaseMediator;

/**
 * Controls the login/logout procedure and stores the user object
 */
public class UserController extends User implements IUserController {
    private boolean isLoggedIn;

    public UserController(String username, String password, boolean simUser) {
        super(username, password, simUser);
    }

    @Override
    public boolean Login(String username, String password) {
        DatabaseMediator databaseMediator = new DatabaseMediator();

        try {
            if (databaseMediator.loadUser(username, password) == null) {
                return isLoggedIn = false;
            } else {
                return isLoggedIn = true;
            }


        } catch (Exception e) {
            return isLoggedIn = false;
        }
    }

    @Override
    public void Logout() {
        isLoggedIn = false;

    }

    @Override
    public boolean getRights() {
        User user = new User();
        if (user.isSimUser() == true) {
            return true;
        } else {
            return false;
        }

    }
}
