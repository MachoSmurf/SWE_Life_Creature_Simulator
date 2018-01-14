package UserPackage;

import DataMediatorPackage.DatabaseMediator;

import java.util.ArrayList;
import java.util.List;

/**
 * Controls the login/logout procedure and stores the user object
 */
public class UserController extends User implements IUserController {

    private String username;
    private String password;
    private boolean isSimUser;
    private boolean isLoggedIn;
    private DatabaseMediator databaseMediator;
    private List<User> allUsers;

    public UserController(String username, String password, boolean simUser) {
        super(username, password, simUser);
        this.username = username;
        this.password = password;
        this.isLoggedIn = simUser;
    }

    /**
     * Checks the login credentials of the user
     *
     * @param username The users login name
     * @param password The users Password
     * @return True if credentials correct, false if incorrect or if the login process failed for some other reason
     */
    @Override
    public boolean Login(String username, String password) {
        databaseMediator = new DatabaseMediator();

        try {
            if (databaseMediator.loadUser(username, password) == null) {
                return isLoggedIn = false;
            } else {
                System.out.println("Successful login!");
                return isLoggedIn = true;
            }


        } catch (Exception e) {
            return isLoggedIn = false;
        }
    }

    /**
     * Logs the user out of the system
     */
    @Override
    public boolean Logout() {
        System.out.println("Logout Successful!");

        return isLoggedIn = false;

    }

    @Override
    public boolean getRights() {
        databaseMediator = new DatabaseMediator();
        User user = databaseMediator.loadUser(username, password);
        if (user.isSimUser() == true) {
            isSimUser = true;
        } else {
            isSimUser = false;
        }

        if (user.isSimUser() == true && isSimUser == true) {
            System.out.println("User is a simulation user!");
            return true;
        } else {
            System.out.println("user is not a simulation user!");
            return false;
        }

    }

    /**
     * Saves a user object to the datasource
     *
     * @param username String containing username
     * @param password String containing password
     * @param isSimUer boolean containing is simUser
     */
    public void saveUser(String username, String password, boolean isSimUer) {
        databaseMediator = new DatabaseMediator();
        databaseMediator.saveUser(username, password, isSimUer);

    }

    /**
     * return a list of users of the simulation
     *
     * @return a list of all users of the simulation
     */
    @Override
    public List<User> getUsers() {
        databaseMediator = new DatabaseMediator();
        allUsers = new ArrayList<User>();

        if (!databaseMediator.getUsers().isEmpty()) {
            allUsers.addAll(databaseMediator.getUsers());
            return allUsers;
        } else {
            return null;
        }

    }
}
