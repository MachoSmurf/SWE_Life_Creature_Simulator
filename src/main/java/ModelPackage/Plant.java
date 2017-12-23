package ModelPackage;

/**
 * Item in the simulation containing energy for herbivores and carnivores to eat. Also contains logic describing lifecycle for plants.
 */
public class Plant extends SimObject {

    private int timesKilled;
    private int stepsFromTenthTimeKilled;




    public Plant(int x, int y, int energy) {
        super(x, y, energy);
    }

    public void step () {

        if (timesKilled >= 10) {
            if (stepsFromTenthTimeKilled < 100) {
                stepsFromTenthTimeKilled ++;
            }
            else {
                timesKilled = 0;
                energy++;
            }
        }
        else {
            energy++;
        }

    }

    public int eaten (int hunger) {
        int resultHunger = 0;
        if ((energy - hunger) <= 0) {
            // plant dies
            resultHunger = hunger - energy;
            energy = 0;
            timesKilled++;
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
