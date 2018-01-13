package LifePackage;

import DataMediatorPackage.FileMediator;
import ModelPackage.*;
import ViewPackage.FXMLSimulatorController;

import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;


/**
 * Controls the status of a (running) simulation and functions as a intermediate between the UI and the simulation
 */
public class Simulation implements ILifeController {

    private boolean simulationIsRunningStep;
    private double simulationSpeed;
    private World world;
    private FileMediator fileMediator = new FileMediator();
    private FXMLSimulatorController viewController;
    private Timer stepTimer;
    private boolean stopSimulation;
    private int simNumber;
    private int stepCounter;
    private StepResult stepResult;

    public Simulation(int energyPlant, int howManyPlants, int energyCarnivore, int staminaCarnivore, int legsCarnivore, int reproductionThresholdCarnivore, int reproductionCostCarnivore, int strengthCarnivore, int swimThresholdCarnivore, int motionThresholdCarnivore, int howManyCarnivore,
                      int energyHerbivore, int staminaHerbivore, int legsHerbivore, int reproductionThresholdHerbivore, int reproductionCostHerbivore, int strengthHerbivore, int swimThresholdHerbivore, int motionThresholdHerbivore, int howManyHerbivore,
                      int energyNonivore, int staminaNonivore, int legsNonivore, int reproductionThresholdNonivore, int reproductionCostNonivore, int strengthNonivore, int swimThresholdNonivore, int motionThresholdNonivore, int howManyNonivore,
                      int energyOmnivore, int digestionBalanceOmnivore, int staminaOmnivore, int legsOmnivore, int reproductionThresholdOmnivore, int reproductionCostOmnivore, int strengthOmnivore, int swimThresholdOmnivore, int motionThresholdOmnivore, int howManyOmnivore,
                      Grid simulationGrid, FXMLSimulatorController simController, int simNumber) {
        world = new World(energyPlant, howManyPlants, energyCarnivore, staminaCarnivore, legsCarnivore, reproductionThresholdCarnivore, reproductionCostCarnivore, strengthCarnivore, swimThresholdCarnivore, motionThresholdCarnivore, howManyCarnivore,
                energyHerbivore, staminaHerbivore, legsHerbivore, reproductionThresholdHerbivore, reproductionCostHerbivore, strengthHerbivore, swimThresholdHerbivore, motionThresholdHerbivore, howManyHerbivore,
                energyNonivore, staminaNonivore, legsNonivore, reproductionThresholdNonivore, reproductionCostNonivore, strengthNonivore, swimThresholdNonivore, motionThresholdNonivore, howManyNonivore,
                energyOmnivore, digestionBalanceOmnivore, staminaOmnivore, legsOmnivore, reproductionThresholdOmnivore, reproductionCostOmnivore, strengthOmnivore, swimThresholdOmnivore, motionThresholdOmnivore, howManyOmnivore,
                simulationGrid);
        viewController = simController;

        simulationIsRunningStep = false;
        stepTimer = new Timer();
        stopSimulation = false;
        stepCounter = 0;
        this.simNumber = simNumber;
    }

    @Override
    public void setSimulationSpeed(double stepsPerSecond) {
        if (stepsPerSecond == 0) {
            this.simulationSpeed = 0;
        } else if (stepsPerSecond == 100) {
            this.simulationSpeed = 1;
        } else {
            this.simulationSpeed = (1 / stepsPerSecond) * 1000;
        }

    }

    @Override
    public void startSimulation() {
        //start the timer
        if (simulationSpeed != 0) {
            stepTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    step();
                }
            }, Math.round(simulationSpeed));
        }
    }

    @Override
    public void stopSimulation() {
        //stop the timer
        stopSimulation = true;
    }


    @Override
    public void saveStepResult(String resultName) {
        fileMediator.saveSimulationResult(stepResult, resultName);
    }

    @Override
    public StepResult loadStepResult(String resultName) {
        return fileMediator.loadSimulationResult(resultName);
    }

    protected void step() {

        //check if the simulation was paused while waiting for the next step
        if (simulationSpeed != 0) {
            stepCounter++;
            if (!simulationIsRunningStep) {
                simulationIsRunningStep = true;
                stepResult = world.doStep();
                //push stepResult back to UIController
                viewController.updateSimulationResults(stepResult, simNumber);
                simulationIsRunningStep = false;
            }
            System.out.println("Did a step!");
            //viewController.updateSimulationResults(new StepResult(getTestingGrid(), 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, stepCounter), simNumber);

            if (!stopSimulation) {
                //start the timer for the next step
                //TODO: Zorg dat dit telt vanaf het startpunt van de vorige, nu wordt de looptijd van één stap opgeteld bij de wachttijd. Dit is een quick-and-dirty implementatie
                startSimulation();
            }
        }
    }

    private Grid getTestingGrid() {

        ///DEBUG ONLY ZOLANG WORLD NIET WERKT
        int testGridWidth = 20;
        int testGridHeight = 20;

        Grid grid = new Grid(testGridWidth, testGridHeight);

        //island 1
        grid.setPointType(new Point(2, 2), GridPointType.Ground);
        grid.setPointType(new Point(2, 3), GridPointType.Ground);
        grid.setPointType(new Point(2, 4), GridPointType.Ground);
        grid.setPointType(new Point(2, 5), GridPointType.Ground);
        grid.setPointType(new Point(3, 2), GridPointType.Ground);
        grid.setPointType(new Point(3, 3), GridPointType.Ground);
        grid.setPointType(new Point(3, 4), GridPointType.Ground);
        grid.setPointType(new Point(3, 5), GridPointType.Ground);
        grid.setPointType(new Point(4, 2), GridPointType.Ground);
        grid.setPointType(new Point(4, 3), GridPointType.Ground);
        grid.setPointType(new Point(4, 4), GridPointType.Ground);
        grid.setPointType(new Point(4, 5), GridPointType.Ground);
        grid.setPointType(new Point(5, 2), GridPointType.Ground);
        grid.setPointType(new Point(5, 3), GridPointType.Ground);
        grid.setPointType(new Point(5, 4), GridPointType.Ground);
        grid.setPointType(new Point(5, 5), GridPointType.Ground);

        //island 2
        grid.setPointType(new Point(10, 2), GridPointType.Ground);
        grid.setPointType(new Point(10, 3), GridPointType.Ground);
        grid.setPointType(new Point(10, 4), GridPointType.Ground);
        grid.setPointType(new Point(10, 5), GridPointType.Ground);
        grid.setPointType(new Point(11, 2), GridPointType.Ground);
        grid.setPointType(new Point(11, 3), GridPointType.Ground);
        grid.setPointType(new Point(11, 4), GridPointType.Ground);
        grid.setPointType(new Point(11, 5), GridPointType.Obstacle);
        grid.setPointType(new Point(12, 2), GridPointType.Obstacle);
        grid.setPointType(new Point(12, 3), GridPointType.Ground);
        grid.setPointType(new Point(12, 4), GridPointType.Ground);
        grid.setPointType(new Point(12, 5), GridPointType.Ground);
        grid.setPointType(new Point(13, 2), GridPointType.Ground);
        grid.setPointType(new Point(13, 3), GridPointType.Ground);
        grid.setPointType(new Point(13, 4), GridPointType.Ground);
        grid.setPointType(new Point(13, 5), GridPointType.Ground);

        //island 3
        grid.setPointType(new Point(10, 10), GridPointType.Ground);
        grid.setPointType(new Point(10, 11), GridPointType.Ground);
        grid.setPointType(new Point(10, 12), GridPointType.Ground);
        grid.setPointType(new Point(10, 13), GridPointType.Ground);
        grid.setPointType(new Point(11, 10), GridPointType.Ground);
        grid.setPointType(new Point(11, 11), GridPointType.Ground);
        grid.setPointType(new Point(11, 12), GridPointType.Ground);
        grid.setPointType(new Point(11, 13), GridPointType.Ground);
        grid.setPointType(new Point(12, 10), GridPointType.Ground);
        grid.setPointType(new Point(12, 11), GridPointType.Ground);
        grid.setPointType(new Point(12, 12), GridPointType.Ground);
        grid.setPointType(new Point(12, 13), GridPointType.Ground);
        grid.setPointType(new Point(13, 10), GridPointType.Ground);
        grid.setPointType(new Point(13, 11), GridPointType.Ground);
        grid.setPointType(new Point(13, 12), GridPointType.Ground);
        grid.setPointType(new Point(13, 13), GridPointType.Ground);

        return grid;
    }

}
