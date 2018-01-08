package ModelPackage;


import java.awt.*;

public class StatusObject {

    private int energy;
    private Color color;
    private boolean alive;

    public StatusObject(int energy, Color color, boolean alive) {
        this.energy = energy;
        this.color = color;
        this.alive = alive;

    }

    public int getEnergy () {
        return energy;
    }

    public boolean getAlive () {
        return alive;
    }

    public Color getColor () {
        return color;
    }
}
