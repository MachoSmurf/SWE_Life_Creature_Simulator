package ModelPackage;

import java.io.Serializable;

/**
 * Abstract class containing the blueprint for items in the world.
 */
public abstract class SimObject implements Serializable {

    protected int x;
    protected int y;
    protected int energy;

    public SimObject(int x, int y, int energy) {
        this.x = x;
        this.y = y;
        this.energy = energy;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getEnergy() {
        return energy;
    }

    public void setEnergy(int energy) {
        this.energy = energy;
    }
}
