package DataMediatorPackage;

import ModelPackage.*;
import UserPackage.User;

import java.sql.*;

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


    public DatabaseMediator()
    {
        this.dbPort = "3306";
        this.dbUser = "root";
        this.dbPassword = "root";
    }

    public DatabaseMediator(String dbPort, String dbUser, String dbPassword)
    {
        this.dbPort = dbPort;
        this.dbUser = dbUser;
        this.dbPassword = dbPassword;
    }

    @Override
    public User loadUser(String username, String password)
    {
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

            User dbUser = new User(databaseUsername, databasePassword, databaseSimUser);

            con.close();

            System.out.println("Successful login!");
            return dbUser;


        } catch (SQLException e) {
            System.out.println("Oops, something went wrong while loading user!");
            e.printStackTrace();
            return null;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void saveUser(User user)
    {
        String username = user.getUsername();
        String password = user.getPassword();
        boolean isSimuser = user.isSimUser();


        Connection con;

        try
        {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/life", "root", "root");
            Statement st = con.createStatement();
            String sql = ("INSERT INTO users VALUES username, password, isSimuser");
        }
        catch (SQLException e)
        {
            System.out.println("Oops, something went wrong while saving user!");
            e.printStackTrace();
        }

    }

    @Override
    public GridClone loadGrid(String gridName)
    {
        // FileMediator
        return null;
    }

    @Override
    public void saveGrid(GridClone grid, String gridName)
    {
        // FileMediator

    }

    @Override
    public StepResult loadSimulationResult(String simResultName)
    {
        // FileMediator
        return null;
    }

    @Override
    public void saveSimulationResult(StepResult resultFrame, String simResultName)
    {
        // FileMediator

    }

    @Override
    public World loadSimulation(String simulationName)
    {
        // FileMediator
        return null;
    }

    @Override
    public void saveSimulation(World Simulation, String simulationName)
    {
        // FileMediator

    }
}
