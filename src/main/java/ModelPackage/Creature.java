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
    List<ArrayList<Point>> livingAreas;
    MovementPlanner movement;
    World world;
    Color myColor;


    public Creature(Point point, int energy, Digestion digestion, int digestionBalance, int stamina, int legs, int reproductionThreshold, int reproductionCost, int strength, int swimThreshold, int motionThreshold, World world) {
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
        this.movement = movement;
        this.world = world;
        if (energy < strength) {
            weight = legs * 10;
        } else {
            weight = legs * 10 + (energy - strength);
        }
        int legSpeed;
        if (legs - 5 > 0) {
            legSpeed = legs - 5;
        } else if (legs - 5 < 0) {
            legSpeed = 5 - legs;
        } else {
            legSpeed = 1;
        }
        int minWeight = legs * 10;
        speed = ((weight - minWeight) / legSpeed) / 10;
        this.hunger = hunger;
        this.nextSteps = nextSteps;
        //status = new StatusObject(energy, getColor(), alive);

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

    public StatusObject step() {

        if (energy > motionThreshold) {

            if (nextSteps != null){
                //move to next step
                if (nextSteps.size() > 0){
                    try{
                        point = nextSteps.get(0);
                        if (nextSteps.size() > 1){
                            nextSteps = nextSteps.subList(1, nextSteps.size() - 1);
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
            } else {
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
            }
        }

        return new StatusObject(energy, myColor, alive);
    }

    public Digestion getDigestion() {
        return digestion;
    }
}