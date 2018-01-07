package LifePackage;
import DataMediatorPackage.FileMediator;
import ModelPackage.*;
import ViewPackage.FXMLSimulatorController;


/**
 * Controls the status of a (running) simulation and functions as a intermediate between the UI and the simulation
 */
public class Simulation implements ILifeController {

    private int simulationSpeed;
    private World world;
    FileMediator fileMediator = new FileMediator();
    FXMLSimulatorController viewController;

    public Simulation(int energyPlant, int howManyPlants, int energyCarnivore, Digestion digestionCarnivore, int digestionBalanceCarnivore, int staminaCarnivore, int legsCarnivore, int reproductionThresholdCarnivore, int reproductionCostCarnivore, int strengthCarnivore, int swimThresholdCarnivore, int motionThresholdCarnivore, int howManyCarnivore,
                      int energyHerbivore, Digestion digestionHerbivore, int digestionBalanceHerbivore, int staminaHerbivore, int legsHerbivore, int reproductionThresholdHerbivore, int reproductionCostHerbivore, int strengthHerbivore, int swimThresholdHerbivore, int motionThresholdHerbivore, int howManyHerbivore,
                      int energyNonivore, Digestion digestionNonivore, int digestionBalanceNonivore, int staminaNonivore, int legsNonivore, int reproductionThresholdNonivore, int reproductionCostNonivore, int strengthNonivore, int swimThresholdNonivore, int motionThresholdNonivore, int howManyNonivore,
                      int energyOmnivore, Digestion digestionOmnivore, int digestionBalanceOmnivore, int staminaOmnivore, int legsOmnivore, int reproductionThresholdOmnivore, int reproductionCostOmnivore, int strengthOmnivore, int swimThresholdOmnivore, int motionThresholdOmnivore, int howManyOmnivore,
                      Grid simulationGrid, FXMLSimulatorController simController)
    {
        world = new World(energyPlant, howManyPlants, energyCarnivore, digestionCarnivore, digestionBalanceCarnivore, staminaCarnivore, legsCarnivore, reproductionThresholdCarnivore, reproductionCostCarnivore, strengthCarnivore, swimThresholdCarnivore, motionThresholdCarnivore, howManyCarnivore,
        energyHerbivore, digestionHerbivore, digestionBalanceHerbivore, staminaHerbivore, legsHerbivore, reproductionThresholdHerbivore, reproductionCostHerbivore, strengthHerbivore, swimThresholdHerbivore, motionThresholdHerbivore, howManyHerbivore,
        energyNonivore, digestionNonivore, digestionBalanceNonivore, staminaNonivore, legsNonivore, reproductionThresholdNonivore, reproductionCostNonivore, strengthNonivore, swimThresholdNonivore, motionThresholdNonivore, howManyNonivore,
        energyOmnivore, digestionOmnivore, digestionBalanceOmnivore, staminaOmnivore, legsOmnivore, reproductionThresholdOmnivore, reproductionCostOmnivore, strengthOmnivore, swimThresholdOmnivore, motionThresholdOmnivore, howManyOmnivore,
        simulationGrid);
        viewController = simController;
    }

    @Override
    public void setSimulationSpeed(int stepsPerSecond)
    {
        this.simulationSpeed = stepsPerSecond;
    }

    @Override
    public void startSimulation()
    {
        //start the timer
    }

    @Override
    public void stopSimulation()
    {
        //stop the timer

    }


    @Override
    public void saveSimulation(String simName, World runningSimulation)
    {
        fileMediator.saveSimulation(runningSimulation, simName);

    }

    @Override
    public void loadSimulation(String simName)
    {
        world = fileMediator.loadSimulation(simName);
    }

    @Override
    public void saveStepResult(String resultName, StepResult finishedResult)
    {
        fileMediator.saveSimulationResult(finishedResult, resultName);
    }

    @Override
    public StepResult loadStepResult(String resultName)
    {
        return fileMediator.loadSimulationResult(resultName);
    }

    private void step(){
        StepResult stepResult = world.doStep();
        //push stepResult back to UIController
        viewController.updateSimulationResults(stepResult);
    }
}
