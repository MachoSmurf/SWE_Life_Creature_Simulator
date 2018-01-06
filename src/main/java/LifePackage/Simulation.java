package LifePackage;
import DataMediatorPackage.FileMediator;
import ModelPackage.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


/**
 * Controls the status of a (running) simulation and functions as a intermediate between the UI and the simulation
 */
public class Simulation implements ILifeController, IWorld {

    private final int stepCount;

    private final Grid grid;
    private final List<SimObject> plantList;
    private final List<SimObject> creatureList;
    private final List<StatusObject > objectList;
    private final MovementPlanner movement;
    private List<ArrayList<Point>> livingAreas;
    Random rnd;

    private Thread thread;
    private int simulationSpeed;

    FileMediator fileMediator = new FileMediator();

    public Simulation(int energyPlant, int howManyPlants, int energyCarnivore, Digestion digestionCarnivore, int digestionBalanceCarnivore, int staminaCarnivore, int legsCarnivore, int reproductionThresholdCarnivore, int reproductionCostCarnivore, int strengthCarnivore, int swimThresholdCarnivore, int motionThresholdCarnivore, int howManyCarnivore,
                      int energyHerbivore, Digestion digestionHerbivore, int digestionBalanceHerbivore, int staminaHerbivore, int legsHerbivore, int reproductionThresholdHerbivore, int reproductionCostHerbivore, int strengthHerbivore, int swimThresholdHerbivore, int motionThresholdHerbivore, int howManyHerbivore,
                      int energyNonivore, Digestion digestionNonivore, int digestionBalanceNonivore, int staminaNonivore, int legsNonivore, int reproductionThresholdNonivore, int reproductionCostNonivore, int strengthNonivore, int swimThresholdNonivore, int motionThresholdNonivore, int howManyNonivore,
                      int energyOmnivore, Digestion digestionOmnivore, int digestionBalanceOmnivore, int staminaOmnivore, int legsOmnivore, int reproductionThresholdOmnivore, int reproductionCostOmnivore, int strengthOmnivore, int swimThresholdOmnivore, int motionThresholdOmnivore, int howManyOmnivore,
                      Grid simulationGrid)
    {
        // parameters
        stepCount = 0;
        plantList = new ArrayList<>();
        creatureList = new ArrayList<>();
        objectList = new ArrayList<>();
        movement = new MovementPlanner();
        grid = simulationGrid;

        boolean worked = movement.initializePlanner(grid);
        if (!worked) {
            System.out.println("Something went wrong while initialising the motionPlanner");
            return;
        }
        int gridWidth = grid.getWidth();
        int gridHeight = grid.getHeight();
        rnd = new Random();
        livingAreas = new ArrayList<ArrayList<Point>>();
        try
        {
            livingAreas = movement.getLivingAreas();
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        /**
         * If the random point is in a Living area and there is not another plant at that point, create a new plant
         *  do this until you have as many plants as described in "howManyPlants".
         */
        for (int i = 0; i < howManyPlants; ) {
            int x = rnd.nextInt(gridWidth);
            int y = rnd.nextInt(gridHeight);
            boolean alreadyAPlant = false;
            for (ArrayList<Point> livingArea : livingAreas) {
                for (Point gridPoint : livingArea) {
                    if (x == gridPoint.x && y == gridPoint.y) {
                        for (SimObject sim : plantList) {
                            if (sim.point.x == x && sim.point.y == y) {
                                alreadyAPlant = true;
                            }
                        }
                        if (!alreadyAPlant) {
                            plantList.add(new Plant(new Point(x, y), energyPlant));
                            i++;
                        }

                    }
                }
            }
        }

        createCreatures(gridWidth, gridHeight, energyNonivore, digestionNonivore, digestionBalanceNonivore, staminaNonivore, legsNonivore, reproductionThresholdNonivore, reproductionCostNonivore, strengthNonivore, swimThresholdNonivore, motionThresholdNonivore, howManyNonivore);
        createCreatures(gridWidth, gridHeight, energyHerbivore, digestionHerbivore, digestionBalanceHerbivore, staminaHerbivore, legsHerbivore, reproductionThresholdHerbivore, reproductionCostHerbivore, strengthHerbivore, swimThresholdHerbivore, motionThresholdHerbivore, howManyHerbivore);
        createCreatures(gridWidth, gridHeight, energyCarnivore, digestionCarnivore, digestionBalanceCarnivore, staminaCarnivore, legsCarnivore, reproductionThresholdCarnivore, reproductionCostCarnivore, strengthCarnivore, swimThresholdCarnivore, motionThresholdCarnivore, howManyCarnivore);
        createCreatures(gridWidth, gridHeight, energyOmnivore, digestionOmnivore, digestionBalanceOmnivore, staminaOmnivore, legsOmnivore, reproductionThresholdOmnivore, reproductionCostOmnivore, strengthOmnivore, swimThresholdOmnivore, motionThresholdOmnivore, howManyOmnivore);

        thread = new Thread();

    }





    @Override
    public void setSimulationSpeed(int stepsPerSecond)
    {
        this.simulationSpeed = stepsPerSecond;

    }

    @Override
    public void startSimulation()
    {
        thread.start();

    }

    @Override
    public void stopSimulation()
    {
        thread = null;

    }


    @Override
    public void saveSimulation(String simName, World runningSimulation)
    {
        fileMediator.saveSimulation(runningSimulation, simName);

    }

    @Override
    public World loadSimulation(String simName)
    {
        return fileMediator.loadSimulation(simName);
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

    @Override
    public StepResult doStep() {
        StepResult stepResult = new StepResult(currentGrid, nonivoreCount, herbivoreCount, carnivoreCount, omnivoreCount, plantCount, energyNonivore, energyCarnivore, energyOmnivore, energyHerbivore, energyPlants, stepCount);
        try
        {
            thread.sleep(simulationSpeed);
            return stepResult;

        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
            return null;
        }

    }
}
