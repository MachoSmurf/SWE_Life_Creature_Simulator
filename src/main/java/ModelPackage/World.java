package ModelPackage;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Acts as a mediator between the outside of the model and the inside. Keeps track of the motionplanner and communicates it to the simobjects
 */
public class World implements Serializable, IWorld {

    private ArrayList<SimObject> plantList;
    private ArrayList<SimObject> creatureList;
    private ArrayList<SimObject> obstacleList;
    private IGrid iGrid;
    private int stepCount;

    public World(){
        stepCount = 0;

    }

    @Override
    public StepResult doStep() {
        List<SimObject> newCreatureList = new ArrayList<>();
        List<SimObject> newPlantList = new ArrayList<>();
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

        for(SimObject sim1 : creatureList) { // compare each creature.
            boolean didThing = false;
            Creature sim = (Creature) sim1;
            for (SimObject simOtherCreature : newCreatureList) { // with each creature.
                Creature otherCreature = (Creature) simOtherCreature;
                if (sim.x == otherCreature.x && sim.y == otherCreature.y) { // if creatures are on the same spot
                    if (sim.digestion != otherCreature.digestion) { //if simEach is a different species as sim.
                        if (sim.digestion == Digestion.Carnivore || sim.digestion == Digestion.Omnivore){ // if sim is a carnivore or omnivore
                            int simHunger = sim.getHunger();
                            int otherSimEnergy = otherCreature.getEnergy();
                            if (simHunger > otherSimEnergy) {
                                StatusObject object = sim.eat(otherSimEnergy);
                                otherCreature.eaten(otherSimEnergy);
                            }
                            else {
                                StatusObject object = sim.eat(simHunger);
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
                else if (Math.abs(sim.x - otherCreature.x) <= 1 && Math.abs(sim.y - otherCreature.y) <= 1) {
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
                if (sim.x == plant.x && sim.y == plant.y) { // if plant is on the same spot as creature
                    if (sim.digestion == Digestion.Herbivore || sim.digestion == Digestion.Omnivore) // if creature is a herbivore or omnivore

                        if (!didThing && plant.isAlive() && sim.getHunger() != 0) {
                        if (sim.getHunger() > plant.getEnergy()){
                            plant.eaten(plant.getEnergy());
                            StatusObject object = sim.eat(plant.getEnergy());
                        }
                        else {
                            plant.eaten(sim.getHunger());
                            StatusObject object = sim.eat(sim.getHunger());
                        }


                        didThing = true;
                    }
                }
            }
            if (!didThing){
                StatusObject object = sim.step();


            }
            newCreatureList.add(sim);
        }



        for (SimObject sim : plantList) {
            Plant plant = (Plant) sim;
            plant.step();
            newPlantList.add(plant);
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
