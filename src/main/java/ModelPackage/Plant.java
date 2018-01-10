package ModelPackage;

import java.awt.*;

/**
 * Item in the simulation containing energy for herbivores and carnivores to eat. Also contains logic describing lifecycle for plants.
 */
public class Plant extends SimObject {

    private int deathCounter;
    private int stepsFromTenthTimeKilled;

    public Plant(Point point, int energy) {
        super(point, energy);
        status = new StatusObject(energy, Color.GREEN, true);
        deathCounter = 0;
        stepsFromTenthTimeKilled = 0;
    }

    public StatusObject step () {

        return new StatusObject(energy, Color.GREEN, true);
    }

}
