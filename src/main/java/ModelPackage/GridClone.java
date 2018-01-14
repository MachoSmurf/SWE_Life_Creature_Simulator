package ModelPackage;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Clone of Grid. Used to be passed on to other instances outside of the model.
 */
public class GridClone implements Serializable, IGrid {

    ArrayList<GridPoint> pointList;
    int height;
    int width;

    public GridClone(ArrayList<GridPoint> pointList, int height, int width) {
        this.pointList = pointList;
        this.height = height;
        this.width = width;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
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