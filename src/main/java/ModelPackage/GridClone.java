package ModelPackage;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Clone of Grid. Used to be passed on to other instances outside of the model.
 */
public class GridClone implements Serializable, IGrid {

    ArrayList<GridPoint> pointList;

    public GridClone(ArrayList<GridPoint> pointList) {
        this.pointList = pointList;
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
    public Color getColor(Point p) {
        return null;
    }

    @Override
    public ArrayList<GridPoint> getPointList() {
        return this.pointList;
    }

    @Override
    public GridPointType getPointType(Point p) {
        return null;
    }
}