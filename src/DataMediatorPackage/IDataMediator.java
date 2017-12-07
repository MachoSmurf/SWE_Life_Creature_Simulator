package DataMediatorPackage;

import ModelPackage.IGrid;
import ModelPackage.IWorld;
import ModelPackage.StepResult;
import ModelPackage.World;
import UserPackage.User;

public interface IDataMediator {

    /**
     * Loads a user from the datasource using the given credentials
     * @param username String containing username
     * @param password String containing password
     * @return User object for given credentials
     */
    public User loadUser(String username, String password);

    /**
     * Saves a user object to the datasource
     * @param user User object containing credentials
     */
    public void saveUser(User user);

    /**
     * Loads an IGrid from the datasource containing a map layout
     * @param gridName the name to use for reference
     * @return IGrid to access the grid contents
     */
    public IGrid loadGrid(String gridName);

    /**
     * Saves grid to the datasource, using a gridname for reference
     * @param grid The instance of the IGrid that has to be saved
     * @param gridName The name used to reference the IGrid
     */
    public void saveGrid(IGrid grid, String gridName);

    /**
     * Load the result of a finished simulation
     * @param simResultName The name for referencing the simulation result
     * @return StepResult object containing the data of the last step of a simulation
     */
    public StepResult loadSimulationResult(String simResultName);

    /**
     * Saves the result of a finished simulation
     * @param resultFrame The StepResult of the last frame of the simulation. Containing the data that has to be saved.
     * @param simResultName The name used to reference this simulation result
     */
    public void saveSimulationResult(StepResult resultFrame, String simResultName);

    /**
     * Loads an unfinished simulation
     * @param simulationName The name used to reference the unfinished sumulation
     * @return IWorld object containing all a grid and the simulation objects in it
     */
    public IWorld loadUnfinishedSimulation(String simulationName);

    /**
     * Saves an unfinished simulation to the relevant data source
     * @param unfinishedSimulation The IWorld object that needs to be saved
     * @param simulationName The name used to reference the unsaved simulation
     */
    public void saveUnfinishedSimulation(IWorld unfinishedSimulation, String simulationName);
}
