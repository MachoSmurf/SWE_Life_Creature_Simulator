package ModelPackage;

import java.awt.*;
import java.io.Serializable;

/**
 * Abstract class containing the blueprint for items in the world.
 */
public abstract class SimObject implements Serializable {

    protected Point point;
    protected int energy;
    protected StatusObject status;


    public SimObject(Point point, int energy) {
        this.point = point;
        this.energy = energy;

    }

    public Point getPoint() {
        return point;
    }

    public int getEnergy() {
        return energy;
    }

    public void setEnergy(int energy) {
        this.energy = energy;
    }

    public abstract StatusObject step();

    public StatusObject getStatus() {
        return status;
    }
}
