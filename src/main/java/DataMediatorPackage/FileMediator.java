package DataMediatorPackage;

import ModelPackage.*;
import UserPackage.User;

import java.io.*;
import java.util.List;

/**
 * Responsible for writing and reading data to and from flatfiles, if necessary using some form of encoding
 */
public class FileMediator implements IDataMediator {

    public FileMediator() {

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
        // DatabaseMediator
        return null;
    }

    /**
     * Saves a user object to the datasource
     *
     * @param user User object containing credentials
     */
    @Override
    public void saveUser(User user) {
        // DatabaseMediator

    }

    /**
     * Loads an GridClone from the datasource containing a map layout
     *
     * @param gridName the name to use for reference
     * @return GridClone to access the grid contents
     */
    @Override
    public GridClone loadGrid(String gridName) {
        return this.<GridClone>LoadFile(gridName);
    }

    /**
     * Saves grid to the datasource, using a gridname for reference
     *
     * @param grid     The instance of the IGrid that has to be saved
     * @param gridName The name used to reference the IGrid
     */
    @Override
    public void saveGrid(GridClone grid, String gridName) {
        this.<GridClone>SaveFile(grid, gridName);
    }

    /**
     * Load the result of a finished simulation
     *
     * @param simResultName The name for referencing the simulation result
     * @return StepResult object containing the data of the last step of a simulation
     */
    @Override
    public StepResult loadSimulationResult(String simResultName) {
        return this.<StepResult>LoadFile(simResultName);
    }

    /**
     * Saves the result of a finished simulation
     *
     * @param resultFrame   The StepResult of the last frame of the simulation. Containing the data that has to be saved.
     * @param simResultName The name used to reference this simulation result
     */
    @Override
    public void saveSimulationResult(StepResult resultFrame, String simResultName) {
        this.<StepResult>SaveFile(resultFrame, simResultName);
    }


    @Override
    public List<User> getUsers() {
        // DatabaseMediator
        return null;
    }

    /**
     * Help generic method to save files to the relevant data source
     *
     * @param obj  The name of the given object
     * @param name The name used to reference the given object
     * @param <T>  The type of the given object
     */

    public static <T> void SaveFile(T obj, String name) {
        File file;

        try {

            file = new File("C:\\Users\\Public\\Documents\\" + name + ".txt");
            if (!file.exists()) {
                file.createNewFile();
            }
            FileOutputStream fos = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(obj);
            oos.close();
            fos.close();
        } catch (Exception e) {
            System.out.println("Error while saving simulation result");
        }

    }

    /**
     * Help generic method to save files to the relevant data source
     *
     * @param name The name used to reference the given object
     * @param <T>  The type of the return object
     * @return Object and content of that object
     */
    public static <T> T LoadFile(String name) {
        String FileName = "C:\\Users\\Public\\Documents\\" + name + ".txt";
        try {
            FileInputStream fin = new FileInputStream(FileName);
            ObjectInputStream ois = new ObjectInputStream(fin);

            return (T) ois.readObject();

        } catch (Exception e) {
            e.printStackTrace();

        }

        return null;
    }

}
