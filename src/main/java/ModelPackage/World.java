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

    private List<SimObject> plantList; //A List of all plants in this world
    protected List<SimObject> creatureList; // A list of all creatures in this world
    private List<StatusObject> objectList; // A List of all step items in this world.
    private IGrid iGrid;
    private int stepCount;
    MovementPlanner movement;
    List<ArrayList<Point>> livingAreas;
    Random rnd;

    /**
     *  Create a world with the parameters we get from LifePackage.
     *  creates a couple of lists with creatures and plants.
     *  sets the initial start and target point of the creature.
     *  gets the route to go to the target from the class motionplanner.
     */
    public World(int energyPlant, int howManyPlants, int energyCarnivore, Digestion digestionCarnivore,int digestionBalanceCarnivore,int staminaCarnivore, int legsCarnivore, int reproductionThresholdCarnivore, int reproductionCostCarnivore, int strengthCarnivore, int swimThresholdCarnivore, int motionThresholdCarnivore, int howManyCarnivore,
                 int energyHerbivore, Digestion digestionHerbivore, int digestionBalanceHerbivore, int staminaHerbivore, int legsHerbivore, int reproductionThresholdHerbivore, int reproductionCostHerbivore, int strengthHerbivore, int swimThresholdHerbivore, int motionThresholdHerbivore, int howManyHerbivore,
                 int energyNonivore, Digestion digestionNonivore, int digestionBalanceNonivore, int staminaNonivore, int legsNonivore, int reproductionThresholdNonivore, int reproductionCostNonivore, int strengthNonivore, int swimThresholdNonivore, int motionThresholdNonivore, int howManyNonivore,
                 int energyOmnivore, Digestion digestionOmnivore, int digestionBalanceOmnivore, int staminaOmnivore, int legsOmnivore, int reproductionThresholdOmnivore, int reproductionCostOmnivore, int strengthOmnivore, int swimThresholdOmnivore, int motionThresholdOmnivore, int howManyOmnivore){

        // parameters
        stepCount = 0;
        plantList = new ArrayList<>();
        creatureList = new ArrayList<>();
        objectList = new ArrayList<>();
        movement = new MovementPlanner();


        boolean worked = movement.initializePlanner(iGrid);
        if (!worked) {
            System.out.println("Something went wrong while initialising the motionPlanner");
            return;
        }
        int gridWidth = iGrid.getWidth();
        int gridHeight = iGrid.getHeight();
        rnd = new Random();
        livingAreas = new ArrayList<ArrayList<Point>>();
        try {
            livingAreas = movement.getLivingAreas();
        } catch (Exception e) {
            e.printStackTrace();
        }

        /**
         * If the random point is in a Living area and there is not another plant at that point, create a new plant
         *  do this until you have as many plants as described in "howManyPlants".
         */
        for (int i = 1; i == howManyPlants; ) {
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

    }

    /**
     *  acts as an one stop place for doing a step.
     *  It returns StepResult witch is al the information the GUI needs to display this step.
     * @return StepResult
     */
    @Override
    public StepResult doStep() {
        List<SimObject> newCreatureList = new ArrayList<>();
        List<SimObject> newPlantList = new ArrayList<>();
        List<StatusObject> newObjectList = new ArrayList<>();
        int nonivores = 0;
        int omnivores = 0;
        int carnivores = 0;
        int herbivores = 0;
        int plants = 0;
        int energyNonivores = 0;
        int energyOmnivores = 0;
        int energyCarnivores = 0;
        int energyHerbivores = 0;
        int energyPlants = 0;
        StatusObject object = null;

        for(SimObject sim1 : creatureList) { // compare each creature in the CreatureList.
            boolean didThing = false;
            Creature sim = (Creature) sim1; //make creature of a SimObject
            for (SimObject simOtherCreature : newCreatureList) { // compare with every creature is the new list.
                Creature otherCreature = (Creature) simOtherCreature; //make creature of a SimObject
                if (sim.point.x == otherCreature.point.x && sim.point.y == otherCreature.point.y) { // if creatures are on the same spot
                    if (sim.digestion != otherCreature.digestion) { //if otherCreature is a different species as sim.
                        if (sim.digestion == Digestion.Carnivore || sim.digestion == Digestion.Omnivore){ // if sim is a carnivore or omnivore
                            int simHunger = sim.getHunger();
                            int otherSimEnergy = otherCreature.getEnergy();
                            if (simHunger > otherSimEnergy) {
                                object = sim.eat(otherSimEnergy);
                                otherCreature.eaten(otherSimEnergy);
                            }
                            else {
                                object = sim.eat(simHunger);
                                otherCreature.eaten(simHunger);
                            }
                            didThing = true;
                        }
                    }
                    else  {
                        if (sim.getReproductionThreshold() < sim.getStrength() && otherCreature.getReproductionThreshold() < otherCreature.getStrength()) { // Same spicies => check is they want to mate
                            Creature child = sim.mate(otherCreature);
                            creatureList.add(child);
                            didThing = true;
                        }
                    }
                }
                else if (Math.abs(sim.point.x - otherCreature.point.x) <= 1 && Math.abs(sim.point.y - otherCreature.point.y) <= 1) {
                    if (sim.getReproductionThreshold() < sim.getStrength() && otherCreature.getReproductionThreshold() < otherCreature.getStrength()) { // Same spiecis => check is they want to mate
                        Creature child = sim.mate(otherCreature);
                        creatureList.add(child);
                    }
                }
                else {

                }
            }
            for (SimObject simPlant : newPlantList) {
                Plant plant = (Plant) simPlant;
                if (sim.point.x == plant.point.x && sim.point.y == plant.point.y) { // if plant is on the same spot as creature
                    if (sim.digestion == Digestion.Herbivore || sim.digestion == Digestion.Omnivore) // if creature is a herbivore or omnivore

                        if (!didThing && plant.isAlive() && sim.getHunger() != 0) {
                        if (sim.getHunger() > plant.getEnergy()){
                            plant.eaten(plant.getEnergy());
                            object = sim.eat(plant.getEnergy());
                        }
                        else {
                            plant.eaten(sim.getHunger());
                            object = sim.eat(sim.getHunger());
                        }


                        didThing = true;
                    }
                }
            }
            if (!didThing){
                object = sim.step();


            }
            newCreatureList.add(sim);
            newObjectList.add(object);

        }



        for (SimObject sim : plantList) {
            Plant plant = (Plant) sim;
            object = plant.step();
            newPlantList.add(plant);
            newObjectList.add(object);
        }

        // fill stepResult
        for (SimObject sim : newCreatureList) {
            Creature creature = (Creature) sim;
            Digestion digestion = creature.digestion;
            switch (digestion){
                case Carnivore: carnivores++;
                    energyCarnivores = energyCarnivores + creature.getEnergy();
                    break;
                case Herbivore: herbivores++;
                    energyHerbivores = energyHerbivores + creature.getEnergy();
                    break;
                case Omnivore: omnivores++;
                    energyOmnivores = energyOmnivores + creature.getEnergy();
                    break;
                case Nonivore: nonivores++;
                    energyOmnivores = energyNonivores + creature.getEnergy();
                    break;
            }
        }

        for (SimObject sim : newPlantList) {
            Plant plant = (Plant) sim;
            plants++;
            energyPlants = energyPlants + plant.getEnergy();
        }
        stepCount++;

        NewTarget target = new NewTarget(creatureList, plantList);
        StepResult stepResult = new StepResult(iGrid, nonivores, carnivores, herbivores, omnivores,plants, energyNonivores, energyCarnivores, energyHerbivores, energyOmnivores, energyPlants, stepCount);
        return stepResult;
    }

    public IGrid getGrid () {
        return iGrid;
    }

    /**
     * This method acts as a universal creature maker.
     *
     * @param gridWidth
     * @param gridHeight
     * @param energy
     * @param digestion
     * @param digestionBalance
     * @param stamina
     * @param legs
     * @param reproductionThreshold
     * @param reproductionCost
     * @param strength
     * @param swimThreshold
     * @param motionThreshold
     * @param howManyCreatures
     */
    private void createCreatures (int gridWidth, int gridHeight, int energy, Digestion digestion, int digestionBalance, int stamina, int legs, int reproductionThreshold, int reproductionCost, int strength, int swimThreshold, int motionThreshold, int howManyCreatures) {

        for (int i = 1; i == howManyCreatures;) {
            Random rnd = new Random();
            int x = rnd.nextInt(gridWidth);
            int y = rnd.nextInt(gridHeight);
            for (ArrayList<Point> livingArea : livingAreas) {
                for (Point gridPoint : livingArea) {
                    if (x == gridPoint.x && y == gridPoint.y) {
                        Point startPoint = new Point(x, y);
                        Point target = null;
                        switch (digestion) {
                            case Nonivore:
                                int balance = rnd.nextInt(100);
                                if (balance < 50 ) {
                                    target = findPlant(startPoint);
                                }
                                else {
                                    target = findCreature(startPoint);
                                }
                                break;
                            case Omnivore:
                                if (digestionBalance < 50 ) {
                                    target = findPlant(startPoint);
                                }
                                else {
                                    target = findCreature(startPoint);
                                }
                                break;
                            case Herbivore:
                                target = findPlant(startPoint);
                                break;
                            case Carnivore:
                                target = findCreature(startPoint);
                                break;
                        }
                        if (target == null) {
                            //go swimming
                        }
                        List<Point> path = null;
                        try {
                            path = movement.findPath(startPoint, target);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        creatureList.add(new Creature(startPoint, energy, digestion, digestionBalance, stamina, legs, reproductionThreshold, reproductionCost, strength, swimThreshold, motionThreshold, path));
                        i++;
                    }
                }
            }
        }

    }

    /**
     * It will find a Creature in the living Area of the current creature and set it as victim.
     *
     *
     * @param startPoint
     * @return targetPoint
     *
     */
    // find the living area of this creature
    private Point findCreature (Point startPoint) {
        List<Point> workingArea = null;
        Point targetPoint = null;
        for (List<Point> livingArea : livingAreas) {
            for (Point livingPoint : livingArea) {
                if (livingPoint.x == startPoint.x && livingPoint.y == startPoint.y) {
                    workingArea = livingArea;
                }
            }
        }

        /**
         * select a random creature from creatureList and compare the location.
         * if the location is in the same livingArea as the original creature, return location else select another random creature
         */


        /**
         * if there are no target creatures in this area, find a random creature in the whole world.
         */
        if (targetPoint == null) {
            int numberOfCreatures = creatureList.size();
            int selectedCreature = rnd.nextInt(numberOfCreatures);
            SimObject creature = creatureList.get(selectedCreature);
            for (List<Point> livingArea : livingAreas) {
                for (Point livingPoint : livingArea) {
                    if (creature.point.x == livingPoint.x && creature.point.y == livingPoint.y){
                        return creature.point;
                    }
                }
            }
        }

        return targetPoint;
    }

    private Point findPlant (Point startPoint) {
        List<Point> workingArea = null;
        Point targetPoint = null;

        boolean plantSelected = false;
        if (workingArea == null) {
            for (List<Point> livingArea : livingAreas) {
                for (Point livingPoint : livingArea) {
                    if (livingPoint.x == startPoint.x && livingPoint.y == startPoint.y) {
                        workingArea = livingArea;
                    }
                }
            }
        }
        for (int i = 1; i == 100; i++) {
            int numberOfPlants = plantList.size();
            int selectedCreature = rnd.nextInt(numberOfPlants);
            SimObject plant = creatureList.get(selectedCreature);
            for (Point point : workingArea) {
                if (plant.point.x == point.x && plant.point.y == point.y) {
                    return point;
                }
            }
        }
        return targetPoint;
    }


}
