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

    @Override
    public User loadUser(String username, String password)
    {
        Connection con;
        try {

            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/SweLifeCreature", "Admin", "Admin");
            Statement st = con.createStatement();
            String sql = ("SELECT * FROM users WHERE DBusername = username and DBpassword = password");

            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                databaseUsername = rs.getString("username");
                databasePassword = rs.getString("password");
                databaseSimUser = rs.getBoolean("simUser");
            }

            User dbUser = new User(databaseUsername, databasePassword, databaseSimUser);

            con.close();

            System.out.println("Successful login!");
            return dbUser;


        } catch (SQLException e) {
            System.out.println("Oops, something went wrong while loading user!");
            e.printStackTrace();
        }


        return null;
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
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/SweLifeCreature", "Admin", "Admin");
            Statement st = con.createStatement();
            String sql = ("INSERT INTO users VALUES username, password, isSimUser");
        }
        catch (SQLException e)
        {
            System.out.println("Oops, something went wrong while saving user!");
            e.printStackTrace();
        }

    }

    @Override
    public Grid loadGrid(String gridName)
    {
        // FileMediator
        return null;
    }

    @Override
    public void saveGrid(Grid grid, String gridName)
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
