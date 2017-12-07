package ModelPackage;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Clone of Grid. Used to be passed on to other instances outside of the model.
 */
public class GridClone implements Serializable, IGrid {

    public GridClone() {
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
