package ModelPackage;

import java.util.List;
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
    private int speed;
    private int hunger;
    private boolean alive;
    private List<TargetCoordinate> nextSteps;

    public Creature(int x, int y, int energy, Digestion digestion, int digestionBalans, int stamina, int legs, int reproductionThreshold, int reproductionCost, int strength, int swimThreshold, int motionThreshold, int speed, int hunger, List<TargetCoordinate> nextSteps) {
        super(x, y, energy);
        this.digestion = digestion;
        this.digestionBalance = digestionBalans;
        this.stamina = stamina;
        this.legs = legs;
        this.reproductionThreshold = reproductionThreshold;
        this.reproductionCost = reproductionCost;
        this.strength = strength;
        this.swimThreshold = swimThreshold;
        this.motionThreshold = motionThreshold;
        if (energy < strength) {
            weight = legs * 10;
        }
        else {
            weight = legs * 10 + (energy - strength);
        }
        this.speed = speed;
        this.hunger = hunger;
        this.nextSteps = nextSteps;
    }

    public void step() {
        //weight
        if (energy < strength) {
            weight = legs * 10;
        }
        else {
            weight = legs * 10 + (energy - strength);
        }

        //speed
        int legSpeed;
        if (legs - 5 >= 0) {
            legSpeed = legs - 5;
        }
        else {
            legSpeed = 5 - legs;
        }
        int minWeight = legs * 10;
        speed = (100 - (weight - minWeight) - legSpeed) /10;

        //hunger
        hunger = stamina - energy;

        if (energy <= 0 ) {
            alive = false;
        }

        // next grid location.
        TargetCoordinate nextGridPoint = nextSteps.get(0);
        x = nextGridPoint.getX();
        y = nextGridPoint.getY();

    }

    public Creature mate (Creature otherParent) {

        //energy
        int energyChild = 50;  // moet uitzoeken hoe dit gezet wordt de eerste keer.

        //Strength
        int diffStrength = Math.abs(strength - otherParent.strength) / 10;
        int minStrength = strength + otherParent.strength - diffStrength;
        int maxStrength = strength + otherParent.strength + diffStrength;
        int strengthChild = ThreadLocalRandom.current().nextInt(minStrength, maxStrength +1);


        //digestion balans
        int diffDigestionBalance = Math.abs(digestionBalance - digestionBalance) / 10;
        int minDigestionBalance = (digestionBalance + otherParent.digestionBalance) /2 - diffDigestionBalance;
        int maxDigestionBalance = (digestionBalance + otherParent.digestionBalance) /2 + diffDigestionBalance;
        int digestionBalanceChild = ThreadLocalRandom.current().nextInt(minDigestionBalance, maxDigestionBalance + 1);

        int diffReproductionThreshold = Math.abs(reproductionThreshold - otherParent.reproductionThreshold) /10;
        int minReproductionThreshold = reproductionThreshold + otherParent.reproductionThreshold - diffReproductionThreshold;
        int maxReproductionThreshold = reproductionThreshold + otherParent.reproductionThreshold + diffReproductionThreshold;
        int reproductionThresholdChild = ThreadLocalRandom.current().nextInt(minReproductionThreshold, maxReproductionThreshold +1);

        Creature child = new Creature(x, y, energyChild, digestion, digestionBalanceChild, stamina, legs, reproductionThresholdChild, reproductionCost, strengthChild, swimThreshold, motionThreshold, speed, hunger, nextSteps);
        return child;
    }

    public int getDigestionBalance() {
        return digestionBalance;
    }

    public void setDigestionBalance(int digestionBalance) {
        this.digestionBalance = digestionBalance;
    }

    public int getStamina() {
        return stamina;
    }

    public void setStamina(int stamina) {
        this.stamina = stamina;
    }

    public int getLegs() {
        return legs;
    }

    public void setLegs(int legs) {
        this.legs = legs;
    }

    public int getReproductionThreshold() {
        return reproductionThreshold;
    }

    public void setReproductionThreshold(int reproductionThreshold) {
        this.reproductionThreshold = reproductionThreshold;
    }

    public int getReproductionCost() {
        return reproductionCost;
    }

    public void setReproductionCost(int reproductionCost) {
        this.reproductionCost = reproductionCost;
    }

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public int getSwimThreshold() {
        return swimThreshold;
    }

    public void setSwimThreshold(int swimThreshold) {
        this.swimThreshold = swimThreshold;
    }

    public int getMotionThreshold() {
        return motionThreshold;
    }

    public void setMotionThreshold(int motionThreshold) {
        this.motionThreshold = motionThreshold;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getHunger() {
        return hunger;
    }

    public void setHunger(int hunger) {
        this.hunger = hunger;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

}
