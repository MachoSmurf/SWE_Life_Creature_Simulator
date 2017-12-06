package UserPackage;

public interface IUserController {

    /**
     * Checks the login credentials of the user
     * @param username The users login name
     * @param password The users Password
     * @return True if credentials correct, false if incorrect or if the login process failed for some other reason
     */
    public boolean Login(String username, String password);

    /**
     * Logs the user out of the system
     */
    public void Logout();

    /**
     * Gets the rights for the user currently logged in
     * @return true if the user may access life simulations, false if the user may only access simulation results
     */
    public boolean getRights();
}
