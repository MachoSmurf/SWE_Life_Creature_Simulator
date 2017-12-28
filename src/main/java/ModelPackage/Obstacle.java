package ModelPackage;

import java.awt.*;

/**
 * Obstacle obstructing motions in a living area
 */
public class Obstacle extends SimObject{

    public Obstacle(Point point, int energy) {
        super(point, energy);
    }

    @Override
    public StatusObject step() {
        return null;
    }
}
