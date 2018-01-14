package DataMediatorPackage;

import ModelPackage.*;
import UserPackage.User;

import java.util.List;

public interface IDataMediator {

    /**
     * Loads a user from the datasource using the given credentials
     *
     * @param username String containing username
     * @param password String containing password
     * @return User object for given credentials
     */
    User loadUser(String username, String password);

    /**
     * Saves a user object to the datasource
     *
     * @param username String containing username
     * @param password String containing password
     * @param isSimUer boolean containing is simUser
     */
    void saveUser(String username, String password, boolean isSimUer);

    /**
     * Loads an GridClone from the datasource containing a map layout
     *
     * @param gridName the name to use for reference
     * @return GridClone to access the grid contents
     */
    GridClone loadGrid(String gridName);

    /**
     * Saves grid to the datasource, using a gridname for reference
     *
     * @param grid     The instance of the IGrid that has to be saved
     * @param gridName The name used to reference the IGrid
     */
    void saveGrid(GridClone grid, String gridName);

    /**
     * Load the result of a finished simulation
     *
     * @param simResultName The name for referencing the simulation result
     * @return StepResult object containing the data of the last step of a simulation
     */
    StepResult loadSimulationResult(String simResultName);

    /**
     * Saves the result of a finished simulation
     *
     * @param resultFrame   The StepResult of the last frame of the simulation. Containing the data that has to be saved.
     * @param simResultName The name used to reference this simulation result
     */
    void saveSimulationResult(StepResult resultFrame, String simResultName);


    /**
     * return a list of users of the simulation
     *
     * @return a list of all users of the simulation
     */
    List<User> getUsers();
}
