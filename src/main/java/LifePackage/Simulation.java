package LifePackage;

import ModelPackage.IWorld;
import ModelPackage.StepResult;

/**
 * Controls the status of a (running) simulation and functions as a intermediate between the UI and the simulation
 */
public class Simulation implements ILifeController {

    public Simulation() {
    }

    @Override
    public void setSimulationSpeed(int stepsPerSecond) {

    }

    @Override
    public void startSimulation() {

    }

    @Override
    public void stopSimulation() {

    }

    @Override
    public void saveSimulation(String simName, IWorld runningSimulation) {

    }

    @Override
    public IWorld loadSimulation(String simName) {
        return null;
    }

    @Override
    public void saveStepResult(String resultName, StepResult finishedResult) {

    }

    @Override
    public StepResult loadStepResult(String resultName) {
        return null;
    }
}
