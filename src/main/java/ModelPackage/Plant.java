package ModelPackage;

import javafx.scene.paint.Color;

/**
 * Item in the simulation containing energy for herbivores and carnivores to eat. Also contains logic describing lifecycle for plants.
 */
public class Plant extends SimObject {

    private int deathCounter;
    private int stepsFromTenthTimeKilled;

    public Plant(int x, int y, int energy) {
        super(x, y, energy);
        status = new StatusObject(energy, Color.GREEN, true);
        deathCounter = 0;
        stepsFromTenthTimeKilled = 0;
    }

    public StatusObject step () {

        Boolean alive = true;
        if (deathCounter >= 10) {
            if (stepsFromTenthTimeKilled < 100) {
                stepsFromTenthTimeKilled ++;
                alive = false;
            }
            else {
                deathCounter = 0;
                energy++;
            }
        }
        else {
            energy++;
        }

        StatusObject status = new StatusObject(energy, Color.GREEN, alive);
        return status;
    }

    public int eaten (int hunger) {
        int resultHunger = 0;
        if ((energy - hunger) <= 0) {
            // plant dies
            resultHunger = hunger - energy;
            energy = 0;
            deathCounter++;
        }
        else {
            // Hunger is gone
            energy = energy - hunger;
        }
        return resultHunger;
    }

    public Boolean isAlive () {
        if (energy == 0) {
            return false;
        }
        else {
            return true;
        }
    }
}
