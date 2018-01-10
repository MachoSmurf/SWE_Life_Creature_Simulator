package ViewPackage;

import ModelPackage.StepResult;

public interface ILifeResult {

    /**
     * Entrypoint for simulationController to push updates of the simulation
     * @param simStatus StepResult object that holds all relevant information concerning the running simulation
     */
    public void updateSimulationResults(StepResult simStatus, int SimulationNumber);
}
