package ModelPackage;

import java.awt.*;

/**
 * Item in the simulation containing energy for herbivores and carnivores to eat. Also contains logic describing lifecycle for plants.
 */
public class Plant extends SimObject {

    private int deathCounter;
    private int stepsFromTenthTimeKilled;
    private boolean alive;

    public Plant(Point point, int energy) {
        super(point, energy);
        status = new StatusObject(energy, Color.GREEN, true);
        deathCounter = 0;
        stepsFromTenthTimeKilled = 0;
        alive = true;
    }

    public StatusObject step () {
        if (energy <= 0){
            alive = false;
            energy = 0;
        }

         if (!alive) {
             if (deathCounter >= 10) {
                 if (stepsFromTenthTimeKilled <= 100) {
                     stepsFromTenthTimeKilled ++;
                     alive = false;
                     energy++;
                 }
                 else {
                     deathCounter = 0;
                     energy++;
                 }
             }
             else{
                 deathCounter++;
                 energy++;
                 alive = true;
             }

        }

        else {
            energy++;
        }

        StatusObject status = new StatusObject(energy, Color.GREEN, alive);
        return status;
    }

}
