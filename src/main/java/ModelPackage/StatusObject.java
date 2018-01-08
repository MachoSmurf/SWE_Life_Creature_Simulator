package ModelPackage;



import java.awt.*;

public class StatusObject {

    private int energy;
    private Color color;
    private boolean alive;
    private Point location;

    public StatusObject(Point location, int energy, Color color, boolean alive) {

        this.energy = energy;
        this.color = color;
        this.alive = alive;
        this.location = location;

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

    public Point getLocation() {
        return location;
    }
}
