package UserPackage;

/**
 * Containing data about user credentials and rights
 */
public class User {
    private String username;
    private String password;
    private boolean simUser;

    public User()
    {

    }
    public User(String username, String password, boolean simUser)
    {
        this.username = username;
        this.password = password;
        this.simUser = simUser;
    }

    public void setUsername(String username)
    {

        this.username = username;
    }


    public String getUsername()
    {
        return username;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public void setSimUser(boolean simUser)
    {
        this.simUser = simUser;
    }


    public boolean isSimUser()
    {
        return simUser;
    }

}
