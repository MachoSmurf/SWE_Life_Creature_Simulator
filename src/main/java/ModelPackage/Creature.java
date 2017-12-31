package ModelPackage;


import javafx.scene.paint.Color;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Describes a creature living in the simulation.
 * Containing logic describing different sorts of behaviour depending on the digestive system of the instance.
 */
public class Creature extends SimObject {

    Digestion digestion;
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
    private List<Point> nextSteps;
    private int stepsTaken;
    MovementPlanner movement;
    List<ArrayList<Point>> livingAreas;


    public Creature(Point point, int energy, Digestion digestion, int digestionBalance, int stamina, int legs, int reproductionThreshold, int reproductionCost, int strength, int swimThreshold, int motionThreshold, List<Point> nextSteps) {
        super(point, energy);
        alive = true;
        stepsTaken = 0;
        this.digestion = digestion;
        this.digestionBalance = digestionBalance;
        this.stamina = stamina;
        this.legs = legs;
        this.reproductionThreshold = reproductionThreshold;
        this.reproductionCost = reproductionCost;
        this.strength = strength;
        this.swimThreshold = swimThreshold;
        this.motionThreshold = motionThreshold;
        movement = new MovementPlanner();
        if (energy < strength) {
            weight = legs * 10;
        }
        else {
            weight = legs * 10 + (energy - strength);
        }
        int legSpeed;
        if (legs - 5 >= 0) {
            legSpeed = legs - 5;
        }
        else {
            legSpeed = 5 - legs;
        }
        int minWeight = legs * 10;
        speed = (100 - (weight - minWeight) - legSpeed) /10;
        this.hunger = hunger;
        this.nextSteps = nextSteps;
        status = new StatusObject(energy, getColor(), alive);


        livingAreas = new ArrayList<ArrayList<Point>>();
        try {
            livingAreas = movement.getLivingAreas();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public StatusObject step() {
        Color gridColor = Color.ORANGE;
        switch (digestion){
            case Carnivore: gridColor = Color.RED;
                break;
            case Herbivore: gridColor = Color.BROWN;
                break;
            case Omnivore: gridColor = Color.YELLOW;
                break;
            case Nonivore: gridColor = Color.PURPLE;
                break;
        }
        StatusObject status = new StatusObject(energy, gridColor, alive);
        if (!alive) return (status);
        //weight



        if (energy < strength) {
            weight = legs * 10;
        }
        else {
            weight = legs * 10 + (energy - strength);
        }

        //move cost
        energy = energy - weight;


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
            Point nextGridPoint = null;
        int whichStep = speed + stepsTaken;
        if (whichStep > nextSteps.size()) {
            nextGridPoint = nextSteps.get(nextSteps.size());
            Point newTargetPoint = getNewTarget();
        }
        else {
            nextGridPoint = nextSteps.get(whichStep);
        }
            point.x = nextGridPoint.x;
            point.y = nextGridPoint.y;

        return status;
    }

    public Creature mate (Creature otherParent) {

        //energy
        int energyChild;
        energyChild = getReproductionCost() + otherParent.getReproductionCost();

        //Strength
        int diffStrength = Math.abs(strength - otherParent.strength) / 10;
        int minStrength = (strength + otherParent.strength) /2 - diffStrength;
        int maxStrength = (strength + otherParent.strength) /2 + diffStrength;
        int strengthChild = ThreadLocalRandom.current().nextInt(minStrength, maxStrength +1);


        //digestion balance
        int diffDigestionBalance = Math.abs(digestionBalance - otherParent.digestionBalance) / 10;
        int minDigestionBalance = (digestionBalance + otherParent.digestionBalance) /2 - diffDigestionBalance;
        int maxDigestionBalance = (digestionBalance + otherParent.digestionBalance) /2 + diffDigestionBalance;
        int digestionBalanceChild = ThreadLocalRandom.current().nextInt(minDigestionBalance, maxDigestionBalance + 1);

        //Stamina
        int diffStamina = Math.abs(stamina - otherParent.stamina) / 10;
        int minStamina = (stamina + otherParent.stamina) /2 - diffStamina;
        int maxStamina = (stamina + otherParent.stamina) /2 + diffStamina;
        int staminaChild = ThreadLocalRandom.current().nextInt(minStamina, maxStamina + 1);

        int diffReproductionThreshold = Math.abs(reproductionThreshold - otherParent.reproductionThreshold) /10;
        int minReproductionThreshold = reproductionThreshold + otherParent.reproductionThreshold - diffReproductionThreshold;
        int maxReproductionThreshold = reproductionThreshold + otherParent.reproductionThreshold + diffReproductionThreshold;
        int reproductionThresholdChild = ThreadLocalRandom.current().nextInt(minReproductionThreshold, maxReproductionThreshold +1);

        int diffReproductionCost = Math.abs(reproductionCost - otherParent.reproductionCost) / 10;
        int minReproductionCost = (reproductionCost + otherParent.reproductionCost) /2 - diffReproductionCost;
        int maxReproductionCost = (reproductionCost + otherParent.reproductionCost) /2 + diffReproductionCost;
        int reproductionCostChild = ThreadLocalRandom.current().nextInt(minReproductionCost, maxReproductionCost + 1);

        int diffSwimThreshold = Math.abs(swimThreshold - otherParent.swimThreshold) / 10;
        int minSwimThreshold = (swimThreshold + otherParent.swimThreshold) /2 - diffSwimThreshold;
        int maxSwimThreshold = (swimThreshold + otherParent.swimThreshold) /2 + diffSwimThreshold;
        int swimThresholdChild = ThreadLocalRandom.current().nextInt(minSwimThreshold, maxSwimThreshold + 1);

        int diffMotionThreshold = Math.abs(motionThreshold - otherParent.motionThreshold) / 10;
        int minMotionThreshold = (motionThreshold + otherParent.motionThreshold) /2 - diffMotionThreshold;
        int maxMotionThreshold = (motionThreshold + otherParent.motionThreshold) /2 + diffMotionThreshold;
        int motionThresholdChild = ThreadLocalRandom.current().nextInt(minMotionThreshold, maxMotionThreshold + 1);



        return new Creature(point, energyChild, digestion, digestionBalanceChild, staminaChild, legs, reproductionThresholdChild, reproductionCostChild, strengthChild, swimThresholdChild, motionThresholdChild, nextSteps);
    }

    private int getReproductionCost () {
        int costReproduction = stamina / 100 * reproductionCost;
        energy = energy - costReproduction;
        return  costReproduction;
    }

    public int getReproductionThreshold() {
        return reproductionThreshold;
    }

    public int getStrength() {
        return strength;
    }

    public int getHunger() {
        return hunger = stamina - energy;
    }

    public StatusObject eaten (int energyEaten) {
        energy = energy - energyEaten;
        if (energy == 0) {
            alive = false;
        }
        status = new StatusObject(energy, getColor(), alive);
        return status;
    }

    public StatusObject eat (int energyAdded) {
        energy = energy + energyAdded;

        status = new StatusObject(energy, getColor(), alive);
        return status;
    }

    private Color getColor() {
        Color point = Color.ORANGE;
        switch (digestion){
            case Carnivore: point = Color.RED;
                break;
            case Herbivore: point = Color.BROWN;
                break;
            case Omnivore: point = Color.YELLOW;
                break;
            case Nonivore: point = Color.PURPLE;
                break;
        }
        return point;
    }

    /**
     * Gets a new targetPoint.
     *
     * @return Point
     */
    private Point getNewTarget () {

        List<Point> workingArea = null;
        Point targetPoint = null;
        for (List<Point> livingArea : livingAreas) {
            for (Point livingPoint : livingArea) {
                if (livingPoint.x == point.x && livingPoint.y == point.y) {
                    workingArea = livingArea;
                }
            }
        }

        for (int i = 0; i == 100; i++) {
            for (Point point : workingArea) {
                Point victimPoint = getNewTarget();
                if (victimPoint.x == point.x && victimPoint.y == point.y) {
                    return point;
                }
            }
        }

        if (targetPoint == null) {
            for (List<Point> livingArea : livingAreas) {
                for (Point livingPoint : livingArea) {
                    Point victimPoint = getNewTarget();
                    if (victimPoint.x == livingPoint.x && victimPoint.y == livingPoint.y){
                        return victimPoint;
                    }
                }
            }
        }
        return targetPoint;
    }
}
