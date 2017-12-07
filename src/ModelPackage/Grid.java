package ModelPackage;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Collection of GridPoints combined with some "metadata" to describe the current situation of the world.
 */
public class Grid implements Cloneable, IGrid {

    public Grid() {
    }

    @Override
    public int getWidth() {
        return 0;
    }

    @Override
    public int getHeight() {
        return 0;
    }

    @Override
    public Color getColor(int x, int y) {
        return null;
    }

    @Override
    public ArrayList<GridPoint> getPointList() {
        return null;
    }
}
