package LifePackage;

import ModelPackage.IWorld;
import ModelPackage.StepResult;

public interface ILifeController {

    /**
     * Sets the speed of the simulation in steps per second
     * @param stepsPerSecond steps to be simulated per second
     */
    public void setSimulationSpeed(int stepsPerSecond);

    /**
     * Starts the simulation
     */
    public void startSimulation();

    /**
     * Stops the simulation
     */
    public void stopSimulation();

    /**
     * Saves the unfinished simulations
     * @param simName The reference name
     * @param runningSimulation The instance of the running simulation
     */
    public void saveSimulation(String simName, IWorld runningSimulation);

    /**
     * Loads an unfinished simulation
     * @param simName the name used to reference the simulation
     * @return IWorld instance that was saved
     */
    public IWorld loadSimulation(String simName);

    /**
     * Saves the result of a finished simulation
     * @param resultName The name used to reference the simulation result
     * @param finishedResult A StepResult containing the data of the finished result
     */
    public void saveStepResult(String resultName, StepResult finishedResult);

    /**
     * Loads a saved result of a finished simulation
     * @param resultName The name used to reference this simulation
     * @return A StepResult object containing the data of a finished simulation
     */
    public StepResult loadStepResult(String resultName);
}
