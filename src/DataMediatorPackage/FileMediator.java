package DataMediatorPackage;

import ModelPackage.IGrid;
import ModelPackage.IWorld;
import ModelPackage.StepResult;
import UserPackage.User;

/**
 * Responsible for writing and reading data to and from flatfiles, if necessary using some form of encoding
 */
public class FileMediator implements IDataMediator {

    public FileMediator(){

    }

    @Override
    public User loadUser(String username, String password) {
        return null;
    }

    @Override
    public void saveUser(User user) {

    }

    @Override
    public IGrid loadGrid(String gridName) {
        return null;
    }

    @Override
    public void saveGrid(IGrid grid, String gridName) {

    }

    @Override
    public StepResult loadSimulationResult(String simResultName) {
        return null;
    }

    @Override
    public void saveSimulationResult(StepResult resultFrame, String simResultName) {

    }

    @Override
    public IWorld loadUnfinishedSimulation(String simulationName) {
        return null;
    }

    @Override
    public void saveUnfinishedSimulation(IWorld unfinishedSimulation, String simulationName) {

    }
}
