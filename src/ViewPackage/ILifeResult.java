package ViewPackage;

import ModelPackage.StepResult;

public interface ILifeResult {

    /**
     * Entrypoint for simulationController to push updates of the simulation
     * @param simStatus StepRsult object that holds all relevant information concerning the running simulation
     */
    public void updateSimulationResults(StepResult simStatus);
}
