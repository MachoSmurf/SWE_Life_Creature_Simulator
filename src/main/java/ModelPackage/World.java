package ModelPackage;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Acts as a mediator between the outside of the model and the inside. Keeps track of the motionplanner and communicates it to the simobjects
 */
public class World implements Serializable, IWorld {

    private ArrayList<Plant> plantList;
    private ArrayList<Creature> creatureList;
    private ArrayList<SimObject> obstacleList;

    public World() {
    }

    @Override
    public StepResult doStep() {
        List<SimObject> newCreatureList = new ArrayList<>();
        List<SimObject> newPlantList = new ArrayList<>();

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
                                sim.eat(otherSimEnergy);
                                otherCreature.eaten(otherSimEnergy);
                            }
                            else {
                                sim.eat(simHunger);
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
                        plant.eaten(sim.getHunger());
                        didThing = true;
                    }
                }
            }
            if (!didThing){
                sim.step();

            }
            newCreatureList.add(sim);
        }



        for (Plant plant : plantList) {
            plant.step();
            newPlantList.add(plant);
        }

        // fill stepresult

        return null;
    }


}
