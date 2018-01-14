package ModelPackage;


import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Describes a creature living in the simulation.
 * Containing logic describing different sorts of behaviour depending on the digestive system of the instance.
 */
public class Creature extends SimObject {

    private Digestion digestion;
    private int digestionBalance;
    private int stamina;
    private int legs;
    private int reproductionThreshold;
    private int reproductionCost;
    private int strength;
    private int swimThreshold;
    private int motionThreshold;
    private int weight;
    private int hunger;
    private boolean alive;
    private List<Point> nextSteps;
    private World world;
    private Color myColor;

    /**
     * Create a Creature with the parameters we get from World.
     *
     * @param point => location of the Creature in the Grid
     * @param energy => the energy of the Creature
     * @param digestion => The spiecies of the Creature
     * @param digestionBalance => thus it prefer meat or Plant?
     * @param stamina => the max energy of the creature
     * @param legs => how many legs does the creature have
     * @param reproductionThreshold => at which energy does the Creature wants to mate in %
     * @param reproductionCost => how much energy does re reproduction cost.
     * @param strength => how strong is the creature
     * @param swimThreshold => under which threshold does a creature want to swim
     * @param motionThreshold => under which threshold does a creature stop moving.
     * @param world an instance of this world.
     */

    public Creature(Point point, int energy, Digestion digestion, int digestionBalance, int stamina, int legs, int reproductionThreshold, int reproductionCost, int strength, int swimThreshold, int motionThreshold, World world) {
        super(point, energy);
        alive = true;

        this.digestion = digestion;
        this.digestionBalance = digestionBalance;
        this.stamina = stamina;
        this.legs = legs;
        this.reproductionThreshold = reproductionThreshold;
        this.reproductionCost = reproductionCost;
        this.strength = strength;
        this.swimThreshold = swimThreshold;
        this.motionThreshold = motionThreshold;
        this.world = world;
        if (energy < strength) {
            weight = legs * 10;
        } else {
            weight = legs * 10 + (energy - strength);
        }
        this.hunger = stamina - energy;

        switch (digestion) {
            case Carnivore:
                myColor = Color.RED;
                break;
            case Herbivore:
                myColor = new Color(145, 121, 88);
                break;
            case Omnivore:
                myColor = Color.YELLOW;
                break;
            case Nonivore:
                myColor = Color.MAGENTA;
                break;
        }
    }

    /**
     * Take a step.
     * check if the creature wants to eat?
     * if not does it want to mate?,
     * if not does it want to move?
     *
     *
     * @return StatusObject.
     */
    public StatusObject step() {
        boolean didThing = false;

        // does it want toe eat?
        if (hunger > 0) {
            switch (digestion) {
                case Nonivore:
                    break;
                case Carnivore:
                    didThing = eatMeat();
                    break;
                case Herbivore:
                    didThing = eatPlant();
                    break;
                case Omnivore:
                    Random rnd = new Random();
                    int whatToEat = rnd.nextInt(100);
                    if (digestionBalance < whatToEat) {
                        didThing = eatMeat();
                    }
                    else {
                        didThing = eatPlant();
                    }
                    break;
            }
        }
        // Does it want to mate?
        if ((stamina / 100 * reproductionThreshold)< energy && !didThing){

            didThing = mate();
        }

        // does it want to move?
        if (energy > motionThreshold && !didThing) {
            MovementCost();
            //System.out.println("Take a step");
            if (nextSteps == null){

                //fetch new target list or stand still
                boolean wantToSwim = false;
                if (energy <= this.swimThreshold) {
                    wantToSwim = true;
                }
                Digestion digestionToUse = digestion;
                if (digestion.equals(Digestion.Omnivore)){
                    //we need to decide what the creature wants to eat
                    Random rnd = new Random();
                    int dice = rnd.nextInt(100);
                    if (dice >= digestionBalance){
                        //we want to eat meat!
                        digestionToUse = Digestion.Carnivore;
                    }
                    else{
                        digestionToUse = Digestion.Herbivore;
                    }
                }
                nextSteps = world.findSimObjectTarget(this.getPoint(), digestionToUse, wantToSwim);

            } else if (nextSteps.size() > getSpeed()) {
                point = nextSteps.get(1);
                nextSteps = null;
            } else {
                //move to next step
                if (nextSteps.size() > 0){
                    try{
                        point = nextSteps.get(nextSteps.size()-1);
                        if (nextSteps.size() > 1){
                            nextSteps = nextSteps.subList(0, nextSteps.size() - 1);
                        }
                        else{
                            nextSteps = null;
                        }
                    }
                    catch (Exception e){
                        nextSteps = null;
                    }
                }
                else
                {
                    nextSteps = null;
                }
            }
        }

        return new StatusObject(energy, myColor, alive);
    }

    public Digestion getDigestion() {
        return digestion;
    }

    private int getHunger() {

        hunger = stamina - energy;
        return hunger;
    }

    private int getSpeed() {
        //5 legs is ideal
        int legSpeed;
        if (legs - 5 > 0) {
            legSpeed = legs - 5;
        } else if (legs - 5 < 0) {
            legSpeed = 5 - legs;
        } else {
            legSpeed = 1;
        }
        legSpeed = legSpeed + 1;

        int minWeight = legs * 10;
        return (5 * (minWeight / weight) / legSpeed); // max 2,5 gridpoints per step. if is too heavy does not walk at all.

    }

    /**
     * If we move it costs energy.
     * How much energy depends on:
     * the legs of the creature, the closer to 5 the faster we go.
     * we get more slow if we weigh more.
     *
     */
    private void MovementCost(){
        Color color = world.getColor(point);
        if (color == Color.blue){
            energy = energy - legs;
        }
        else {
            if (getEnergy() - strength < 0) {
                weight = legs * 10;
            }
            else {
                weight = legs * 10 + (energy - strength);
            }
            energy = energy - weight;
        }

    }

    /**
     * if the creature is not in water,
     * do for each simObject in the list of objects that already did a step:
     *  if the other creature is on the same gridpoint
     *  if it is another species
     *  if the strength of the creature is more then the strength of the other creature.
     * eat Creature
     *
     * @return the answer to the question did it eat?
     */
    private boolean eatMeat () {
        if (world.getColor(point) != Color.blue) {
            List<SimObject> ThingToSelect = world.newSimObjectList;
            for (SimObject sim : ThingToSelect) {
                if (sim instanceof Creature) {
                    if (sim.point.x == point.x && sim.point.y == point.y){
                        if (((Creature) sim).getDigestion() != digestion){
                            if (strength > ((Creature) sim).strength){
                                int eaten = strength - ((Creature) sim).strength;
                                if (sim.energy >= eaten && getHunger() >= eaten) {
                                    energy = energy + eaten;
                                    sim.energy = sim.energy - eaten;
                                    //System.out.println("Did eat meat nr 0");
                                    return true;
                                }
                                else if (getHunger() < eaten && sim.energy >= eaten) {
                                    energy = energy + getHunger();
                                    sim.energy = sim.energy - getHunger();
                                    //System.out.println("Did eat meat nr 1");
                                    return true;
                                }
                                else if (sim.energy >= eaten && getHunger() < eaten) {
                                    energy = energy + sim.energy;
                                    sim.energy = 0;
                                    //System.out.println("Did eat meat nr 2");
                                    return true;
                                }
                                else if (sim.energy < getHunger()) {
                                    energy = energy + sim.energy;
                                    sim.energy = 0;
                                    //System.out.println("Did eat meat nr 3");
                                    return true;
                                }
                                else {
                                    energy = energy + getHunger();
                                    sim.energy = sim.energy - getHunger();
                                    //System.out.println("Did eat meat nr 4");
                                    return true;
                                }
                            }
                        }
                    }

                }
            }
        }

        return false;
    }

    /**
     * if not in water
     * do for each simObject in the list of objects that already did a step:
     * if the plant is on the same gridpoint
     * eat Plant
     * @return the answer to the question did it eat?
     */
    private boolean eatPlant() {
        if (world.getColor(point) != Color.blue) {
            List<SimObject> ThingToSelect = world.newSimObjectList;
            for (SimObject sim : ThingToSelect) {
                if (sim instanceof Plant) {
                    if (sim.point.x == point.x && sim.point.y == point.y){
                        if (getHunger() > sim.getEnergy()){
                            energy = energy + sim.getEnergy();
                            sim.energy = 0;
                            //System.out.println("ate a whole plant");
                            return true;
                        }
                        else {
                            energy = energy + getHunger();
                            sim.energy = sim.energy - getHunger();
                            //System.out.println("ate a plant til no hungry anymore");
                            return true;
                        }
                    }
                }
            }
        }

        return false;

    }

    /**
     * if the creature is not in water,
     * do for each simObject in the list of objects that already did a step:
     *  if the other creature is on the same gridPoint or max 1 gridPoint away
     *  if it is the same species
     *  if the reproductionThreshold of the other creature is met.
     * Mate.
     *
     * @return the answer to the question: "did it mate?"
     */
    private boolean mate () {

        List<SimObject> ThingToSelect = world.newSimObjectList;
        for (SimObject otherParent : ThingToSelect) {
            if (otherParent instanceof Creature) {
                if (Math.abs(point.x - otherParent.point.x) <= 1 && Math.abs(point.y - otherParent.point.y) <= 1) {
                    if (((Creature) otherParent).getDigestion() == digestion) {
                        if (((Creature) otherParent).reproductionThreshold < otherParent.energy) {
                            int energyChild;
                            energyChild = reproductionCost + ((Creature) otherParent).reproductionCost;
                            energy = energy - reproductionCost;

                            //Strength
                            int diffStrength = Math.abs(strength - ((Creature) otherParent).strength) / 10;
                            int minStrength = (strength + ((Creature) otherParent).strength) / 2 - diffStrength;
                            int maxStrength = (strength + ((Creature) otherParent).strength) / 2 + diffStrength;
                            int strengthChild = ThreadLocalRandom.current().nextInt(minStrength, maxStrength + 1);


                            //digestion balance
                            int diffDigestionBalance = Math.abs(digestionBalance - ((Creature) otherParent).digestionBalance) / 10;
                            int minDigestionBalance = (digestionBalance + ((Creature) otherParent).digestionBalance) / 2 - diffDigestionBalance;
                            int maxDigestionBalance = (digestionBalance + ((Creature) otherParent).digestionBalance) / 2 + diffDigestionBalance;
                            int digestionBalanceChild = ThreadLocalRandom.current().nextInt(minDigestionBalance, maxDigestionBalance + 1);

                            //Stamina
                            int diffStamina = Math.abs(stamina - ((Creature) otherParent).stamina) / 10;
                            int minStamina = (stamina + ((Creature) otherParent).stamina) / 2 - diffStamina;
                            int maxStamina = (stamina + ((Creature) otherParent).stamina) / 2 + diffStamina;
                            int staminaChild = ThreadLocalRandom.current().nextInt(minStamina, maxStamina + 1);

                            int diffReproductionThreshold = Math.abs(reproductionThreshold - ((Creature) otherParent).reproductionThreshold) / 10;
                            int minReproductionThreshold = reproductionThreshold + ((Creature) otherParent).reproductionThreshold - diffReproductionThreshold;
                            int maxReproductionThreshold = reproductionThreshold + ((Creature) otherParent).reproductionThreshold + diffReproductionThreshold;
                            int reproductionThresholdChild = ThreadLocalRandom.current().nextInt(minReproductionThreshold, maxReproductionThreshold + 1);

                            int diffReproductionCost = Math.abs(reproductionCost - ((Creature) otherParent).reproductionCost) / 10;
                            int minReproductionCost = (reproductionCost + ((Creature) otherParent).reproductionCost) / 2 - diffReproductionCost;
                            int maxReproductionCost = (reproductionCost + ((Creature) otherParent).reproductionCost) / 2 + diffReproductionCost;
                            int reproductionCostChild = ThreadLocalRandom.current().nextInt(minReproductionCost, maxReproductionCost + 1);

                            int diffSwimThreshold = Math.abs(swimThreshold - ((Creature) otherParent).swimThreshold) / 10;
                            int minSwimThreshold = (swimThreshold + ((Creature) otherParent).swimThreshold) / 2 - diffSwimThreshold;
                            int maxSwimThreshold = (swimThreshold + ((Creature) otherParent).swimThreshold) / 2 + diffSwimThreshold;
                            int swimThresholdChild = ThreadLocalRandom.current().nextInt(minSwimThreshold, maxSwimThreshold + 1);

                            int diffMotionThreshold = Math.abs(motionThreshold - ((Creature) otherParent).motionThreshold) / 10;
                            int minMotionThreshold = (motionThreshold + ((Creature) otherParent).motionThreshold) / 2 - diffMotionThreshold;
                            int maxMotionThreshold = (motionThreshold + ((Creature) otherParent).motionThreshold) / 2 + diffMotionThreshold;
                            int motionThresholdChild = ThreadLocalRandom.current().nextInt(minMotionThreshold, maxMotionThreshold + 1);

                            Creature child = new Creature(point, energyChild, digestion, digestionBalanceChild, staminaChild, legs, reproductionThresholdChild, reproductionCostChild, strengthChild, swimThresholdChild, motionThresholdChild, world);
                            world.newSimObjectList.add(child);
                            System.out.println("Mated!!!!!");
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    public int getDigestionBalance() {
        return digestionBalance;
    }
}