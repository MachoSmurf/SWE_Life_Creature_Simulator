package LifePackage;

import ModelPackage.StepResult;
import ModelPackage.World;

public interface ILifeController {

    /**
     * Sets the speed of the simulation in steps per second
     *
     * @param stepsPerSecond steps to be simulated per second
     */
    void setSimulationSpeed(double stepsPerSecond);

    /**
     * Starts the simulation
     */
    void startSimulation();

    /**
     * Stops the simulation
     */
    void stopSimulation();

    void saveStepResult(String resultName);

    /**
     * Loads a saved result of a finished simulation
     *
     * @param resultName The name used to reference this simulation
     * @return A StepResult object containing the data of a finished simulation
     */
    StepResult loadStepResult(String resultName);

}
