package ModelPackage;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Acts as a mediator between the outside of the model and the inside. Keeps track of the motionplanner and communicates it to the simobjects
 */
public class World implements Serializable, IWorld {

    ArrayList<SimObject> plantList;
    ArrayList<SimObject> creatureList;
    ArrayList<SimObject> obstacleList;

    public World() {
    }

    @Override
    public StepResult doStep() {
        return null;
    }
}
