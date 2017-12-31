package ModelPackage;

import java.awt.*;
import java.util.List;
import java.util.Random;

public class NewTarget {

    List<SimObject> creatureList;
    List<SimObject> plantList;
    Random rnd;

    public NewTarget (List<SimObject> creatureList, List<SimObject> plantList) {
        this.creatureList = creatureList;
        this.plantList = plantList;
        rnd = new Random();
    }

    public Point getNewTargetCreature () {
        int howManyCreatures = creatureList.size();
        int selected = rnd.nextInt(howManyCreatures);
        SimObject sim = creatureList.get(selected);
        return sim.point;
    }

    public Point getNewTargetPlant () {
        int howManyPlants = plantList.size();
        int selected = rnd.nextInt(howManyPlants);
        SimObject sim = plantList.get(selected);
        return sim.point;
    }


}
