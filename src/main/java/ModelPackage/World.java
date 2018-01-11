package ModelPackage;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Acts as a mediator between the outside of the model and the inside. Keeps track of the motionplanner and communicates it to the simobjects
 */
public class World implements Serializable, IWorld {

    private Grid grid;
    private MovementPlanner mPlanner;
    private List<SimObject> simObjects;
    private List<ArrayList<Point>> livingAreas;
    private boolean worldInitialized;
    private Random rnd;
    private int stepCount;
    protected List<SimObject> newSimObjectList; // to know which SimObject we already had when trying to eat or mate, without always choosing yourself

    /**
     * Create a world with the parameters we get from LifePackage.
     * creates a couple of lists with creatures and plants.
     * sets the initial start and target point of the creature.
     * gets the route to go to the target from the class motionplanner.
     */
    public World(int energyPlant, int howManyPlants, int energyCarnivore, int staminaCarnivore, int legsCarnivore, int reproductionThresholdCarnivore, int reproductionCostCarnivore, int strengthCarnivore, int swimThresholdCarnivore, int motionThresholdCarnivore, int howManyCarnivore,
                 int energyHerbivore, int staminaHerbivore, int legsHerbivore, int reproductionThresholdHerbivore, int reproductionCostHerbivore, int strengthHerbivore, int swimThresholdHerbivore, int motionThresholdHerbivore, int howManyHerbivore,
                 int energyNonivore, int staminaNonivore, int legsNonivore, int reproductionThresholdNonivore, int reproductionCostNonivore, int strengthNonivore, int swimThresholdNonivore, int motionThresholdNonivore, int howManyNonivore,
                 int energyOmnivore, int digestionBalanceOmnivore, int staminaOmnivore, int legsOmnivore, int reproductionThresholdOmnivore, int reproductionCostOmnivore, int strengthOmnivore, int swimThresholdOmnivore, int motionThresholdOmnivore, int howManyOmnivore,
                 Grid simulationGrid) {

        if (digestionBalanceOmnivore > 100){
            throw new IllegalArgumentException("DigestionBalanceOmnivore is out of range (must be <=100)");
        }

        this.grid = simulationGrid;
        mPlanner = new MovementPlanner();
        try {
            if (!mPlanner.initializePlanner(grid)) {
                throw new Exception("Failed to initialize movementplanner");
            }
            livingAreas = mPlanner.getLivingAreas();
        } catch (Exception e) {
            System.out.println("Failed to generate grid");
            e.printStackTrace();
        }

        rnd = new Random();
        simObjects = new ArrayList<>();
        stepCount = 0;

        //check if there are more plants than available land
        int landCount = 0;
        for (int c = 1; c < livingAreas.size(); c++) {
            landCount += livingAreas.get(c).size();
        }
        if(landCount >= howManyPlants){
            //generate plants
            for (int i = 0; i < howManyPlants; i++) {
                Point spawnPoint = findAvailableSpawnPoint();
                //check if a plant is already present at this position
                boolean found = true;
                while(found){
                    if (simObjects.size() == 0) {
                        found = false;
                        break;
                    }
                    for (SimObject so : simObjects){
                        if ((so.getPoint().getX() != spawnPoint.getX()) | (so.getPoint().getY() != spawnPoint.getY())){
                            found = false;
                        }
                    }
                    spawnPoint = findAvailableSpawnPoint();
                }
                //not found, add to list
                simObjects.add(new Plant(spawnPoint, energyPlant));
            }
        }

        //add creatures
        for (int i = 0; i<howManyCarnivore; i++){
            simObjects.add(new Creature(findAvailableSpawnPoint(), energyCarnivore, Digestion.Carnivore, 100,
                    staminaCarnivore, legsCarnivore, reproductionThresholdCarnivore, reproductionCostCarnivore, strengthCarnivore,
                    swimThresholdCarnivore, motionThresholdCarnivore, this));
        }
        for (int i = 0; i<howManyHerbivore; i++){
            simObjects.add(new Creature(findAvailableSpawnPoint(), energyHerbivore, Digestion.Herbivore, 0,
                    staminaHerbivore, legsHerbivore, reproductionThresholdHerbivore, reproductionCostHerbivore, strengthHerbivore,
                    swimThresholdHerbivore, motionThresholdHerbivore, this));
        }
        for (int i = 0; i<howManyNonivore; i++){
            simObjects.add(new Creature(findAvailableSpawnPoint(), energyNonivore, Digestion.Nonivore, 0,
                    staminaNonivore, legsNonivore, reproductionThresholdNonivore, reproductionCostNonivore, strengthNonivore,
                    swimThresholdNonivore, motionThresholdNonivore, this));
        }
        for (int i = 0; i<howManyOmnivore; i++){
            simObjects.add(new Creature(findAvailableSpawnPoint(), energyOmnivore, Digestion.Omnivore, digestionBalanceOmnivore,
                    staminaOmnivore, legsOmnivore, reproductionThresholdOmnivore, reproductionCostOmnivore, strengthOmnivore,
                    swimThresholdOmnivore, motionThresholdNonivore, this));
        }

        System.out.println(simObjects.size() + " objects added to simobjects");
    }

    @Override
    public StepResult doStep() {
        //collect data
        int energyNonivore = 0;
        int energyCarnivore = 0;
        int energyOmnivore = 0;
        int energyHerbivore = 0;
        int energyPlants = 0;
        int nonivoreCount = 0;
        int herbivoreCount = 0;
        int carnivoreCount = 0;
        int omnivoreCount = 0;
        int plantCount = 0;
        newSimObjectList = new ArrayList<SimObject>();

        stepCount++;


        for (SimObject so : simObjects){
            StatusObject statusObject = so.step();
            if (statusObject.getColor() == null){
                System.out.println("hierzo null");
            }
            grid.setColor(so.getPoint(), statusObject.getColor());
            if (so instanceof Creature){
                switch (((Creature) so).getDigestion()){
                    case Carnivore:
                        if (statusObject.getAlive()){
                            carnivoreCount++;
                            energyCarnivore += statusObject.getEnergy();
                        }
                        break;
                    case Omnivore:
                        if (statusObject.getAlive()){
                            omnivoreCount++;
                            energyOmnivore += statusObject.getEnergy();
                        }
                        break;
                    case Herbivore:
                        if (statusObject.getAlive()){
                            herbivoreCount++;
                            energyHerbivore += statusObject.getEnergy();
                        }
                        break;
                    case Nonivore:
                        if (statusObject.getAlive()){
                            nonivoreCount++;
                            energyNonivore += statusObject.getEnergy();
                        }
                        break;
                }
            }
            if (so instanceof Plant){
                if (statusObject.getAlive()){
                    plantCount++;
                    energyPlants += statusObject.getEnergy();
                }
            }
            newSimObjectList.add(so);
        }

        //revert empty gridpoints to original color
        for (GridPoint gridPoint : grid.getPointList()){
            boolean occupied = false;
            for (SimObject simObject : simObjects){
                if ((simObject.getPoint().getX() == gridPoint.getX()) && (simObject.getPoint().getY() == gridPoint.getY())){
                    occupied = true;
                    break;
                }
            }
            if(!occupied){
                gridPoint.resetColor();
            }
        }

        //TODO: Return GridClone instead of this Grid
        return new StepResult(this.grid, nonivoreCount, herbivoreCount, carnivoreCount, omnivoreCount, plantCount, energyNonivore, energyCarnivore, energyOmnivore, energyHerbivore, energyPlants, stepCount);
    }

    @Override
    public void resetExtinction() {

    }

    @Override
    public void disableExtinction() {

    }

    @Override
    public void activateExtinctionNow() {

    }

    public List<Point> findSimObjectTarget(Point currentLocation, Digestion searcherDigestion, boolean wantsToSwim){
        if (searcherDigestion.equals(Digestion.Nonivore)){
            return null;
        }

        SimObject target = null;
        int selectArea = -1;
        if(!wantsToSwim){
            //find out what livingarea the creature is in
            for (int i=0; i<livingAreas.size(); i++){
                if (livingAreas.get(i).contains(currentLocation)){
                    selectArea = i;
                }
            }
        }

        //return Route to first available simobject
        try{
            for (SimObject simObject : simObjects){
                if (selectArea != -1){
                    if (!livingAreas.get(selectArea).contains(simObject.getPoint())){
                        continue;
                    }
                }
                switch (searcherDigestion){
                    case Herbivore:
                        if (simObject instanceof Plant){
                            target = simObject;
                        }
                        break;
                    case Carnivore:
                        if (simObject instanceof Creature){
                            target = simObject;
                        }
                        break;
                    case Omnivore:
                        //eats both, but should decide what to eat in the creature
                        throw new Exception("Omnivore Creature should decide what it wants to eat");
                }
            }
            if (target != null){
                return mPlanner.findPath(currentLocation, target.getPoint(), !wantsToSwim);
            }
        }
        catch (Exception e)
        {
            System.out.println(e);
            e.printStackTrace();
        }
    return null;
    }


    private Point findAvailableSpawnPoint() {
        //exclude area 1 (water)
        int areaNumber = rnd.nextInt(livingAreas.size() - 1) + 1;
        List<Point> area = livingAreas.get(areaNumber);

        //include all points
        int pointNumber = rnd.nextInt(area.size() - 1);
        return area.get(pointNumber);
    }

    public Color getColor(Point point) {
      return grid.getColor(point);
    }

}
