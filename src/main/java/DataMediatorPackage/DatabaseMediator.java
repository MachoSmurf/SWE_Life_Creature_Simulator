package DataMediatorPackage;

import ModelPackage.*;
import UserPackage.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Responsible for writing and reading data to and from a MySQL Database
 */
public class DatabaseMediator implements IDataMediator {

    private String databaseUsername = "";
    private String databasePassword = "";
    private boolean databaseSimUser = false;
    private String dbPort;
    private String dbUser;
    private String dbPassword;


    public DatabaseMediator() {
        this.dbPort = "3306";
        this.dbUser = "root";
        this.dbPassword = "root";
    }

    /**
     * Loads a user from the datasource using the given credentials
     *
     * @param username String containing username
     * @param password String containing password
     * @return User object for given credentials
     */
    @Override
    public User loadUser(String username, String password) {
        Connection con;
        try {

            String driver = "com.mysql.jdbc.Driver";
            Class.forName(driver);
            con = DriverManager.getConnection("jdbc:mysql://localhost:" + dbPort + "/life", dbUser, dbPassword);
            PreparedStatement ps = con.prepareStatement("SELECT * FROM users WHERE DBusername = ? and DBpassword = ?");
            ps.setObject(1, username);
            ps.setObject(2, password);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                databaseUsername = rs.getString("DBusername");
                databasePassword = rs.getString("DBpassword");
                databaseSimUser = rs.getBoolean("DBsimUser");
            }

            if (databaseUsername.contentEquals(username) && databasePassword.contentEquals(password)) {
                User dbUser = new User(databaseUsername, databasePassword, databaseSimUser);

                con.close();

                return dbUser;

            } else {
                con.close();
                return null;
            }


        } catch (SQLException e) {
            System.out.println("Oops, something went wrong while loading user!");
            e.printStackTrace();
            return null;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Saves a user object to the datasource
     *
     * @param user User object containing credentials
     */
    @Override
    public void saveUser(User user) {
        String username = user.getUsername();
        String password = user.getPassword();
        boolean isSimuser = user.isSimUser();


        Connection con;

        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:" + dbPort + "/life", dbUser, dbPassword);
            PreparedStatement ps = con.prepareStatement("SELECT * FROM users WHERE DBusername = ? and DBpassword = ?");
            ps.setObject(1, username);
            ps.setObject(2, password);

        } catch (SQLException e) {
            System.out.println("Oops, something went wrong while saving user!");
            e.printStackTrace();
        }

    }

    /**
     * Loads an GridClone from the datasource containing a map layout
     *
     * @param gridName the name to use for reference
     * @return GridClone to access the grid contents
     */
    @Override
    public GridClone loadGrid(String gridName) {
        // FileMediator
        return null;
    }

    /**
     * Saves grid to the datasource, using a gridname for reference
     *
     * @param grid     The instance of the IGrid that has to be saved
     * @param gridName The name used to reference the IGrid
     */
    @Override
    public void saveGrid(GridClone grid, String gridName) {
        // FileMediator

    }

    /**
     * Load the result of a finished simulation
     *
     * @param simResultName The name for referencing the simulation result
     * @return StepResult object containing the data of the last step of a simulation
     */
    @Override
    public StepResult loadSimulationResult(String simResultName) {
        // FileMediator
        return null;
    }

    /**
     * Saves the result of a finished simulation
     *
     * @param resultFrame   The StepResult of the last frame of the simulation. Containing the data that has to be saved.
     * @param simResultName The name used to reference this simulation result
     */
    @Override
    public void saveSimulationResult(StepResult resultFrame, String simResultName) {
        // FileMediator

    }


    /**
     * return a list of users of the simulation
     *
     * @return a list of all users of the simulation
     */
    @Override
    public List<User> getUsers() {
        List<User> allUsers = new ArrayList<User>();
        Connection con;

        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:" + dbPort + "/life", dbUser, dbPassword);
            PreparedStatement ps = con.prepareStatement("SELECT * FROM users ");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                databaseUsername = rs.getString("DBusername");
                databasePassword = rs.getString("DBpassword");
                databaseSimUser = rs.getBoolean("DBsimUser");
                User dbUser = new User(databaseUsername, databasePassword, databaseSimUser);

                allUsers.add(dbUser);
            }
            return allUsers;

        } catch (SQLException e) {
            System.out.println("Oops, something went wrong while get users!");
            e.printStackTrace();
        }
        return null;
    }
}
