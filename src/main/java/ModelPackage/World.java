package ModelPackage;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Acts as a mediator between the outside of the model and the inside. Keeps track of the motionplanner and communicates it to the simobjects
 */
public class World implements Serializable, IWorld {

    private List<SimObject> plantList; //A List of all plants in this world
    private List<SimObject> creatureList; // A list of all creatures in this world
    private List<StatusObject> objectList; // A List of all step items in this world.
    private IGrid iGrid;
    private int stepCount;
    MovementPlanner movement;

    public World(int energyPlant, int howManyPlants, int energyCarnivore, Digestion digestionCarnivore,int digestionBalanceCarnivore,int staminaCarnivore, int legsCarnivore, int reproductionThresholdCarnivore, int reproductionCostCarnivore, int strengthCarnivore, int swimThresholdCarnivore, int motionThresholdCarnivore, int howManyCarnivore,
                 int energyHerbivore, Digestion digestionHerbivore, int digestionBalanceHerbivore, int staminaHerbivore, int legsHerbivore, int reproductionThresholdHerbivore, int reproductionCostHerbivore, int strengthHerbivore, int swimThresholdHerbivore, int motionThresholdHerbivore, int howManyHerbivore,
                 int energyNonivore, Digestion digestionNonivore, int digestionBalanceNonivore, int staminaNonivore, int legsNonivore, int reproductionThresholdNonivore, int reproductionCostNonivore, int strengthNonivore, int swimThresholdNonivore, int motionThresholdNonivore, int howManyNonivore,
                 int energyOmnivore, Digestion digestionOmnivore, int digestionBalanceOmnivore, int staminaOmnivore, int legsOmnivore, int reproductionThresholdOmnivore, int reproductionCostOmnivore, int strengthOmnivore, int swimThresholdOmnivore, int motionThresholdOmnivore, int howManyOmnivore){
        stepCount = 0;
        plantList = new ArrayList<>();
        creatureList = new ArrayList<>();
        objectList = new ArrayList<>();
        movement = new MovementPlanner();
        int gridWidth = iGrid.getWidth();
        int gridHeight = iGrid.getHeight();



        for (int i = 1; i == howManyPlants; i++) {
            Random rnd = new Random();
            int x = rnd.nextInt(gridWidth);
            int y = rnd.nextInt(gridHeight);
            movement.geLivingAreas();
        }


    }

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

        for(SimObject sim1 : creatureList) { // compare each creature.
            boolean didThing = false;
            Creature sim = (Creature) sim1;
            for (SimObject simOtherCreature : newCreatureList) { // with each creature.
                Creature otherCreature = (Creature) simOtherCreature;
                if (sim.point.x == otherCreature.point.x && sim.point.y == otherCreature.point.y) { // if creatures are on the same spot
                    if (sim.digestion != otherCreature.digestion) { //if simEach is a different species as sim.
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
                        if (sim.getReproductionThreshold() < sim.getStrength() && otherCreature.getReproductionThreshold() < otherCreature.getStrength()) { // Same spiecis => check is they want to mate
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


        StepResult stepResult = new StepResult(iGrid, nonivores, carnivores, herbivores, omnivores,plants, energyNonivores, energyCarnivores, energyHerbivores, energyOmnivores, energyPlants, stepCount);
        return stepResult;
    }

    public IGrid getGrid () {
        return iGrid;
    }
}
