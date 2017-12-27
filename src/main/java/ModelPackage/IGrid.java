package ModelPackage;

import java.awt.*;
import java.util.ArrayList;

public interface IGrid {

    /**
     * Get the width of the grid
     * @return returns integer of grid width
     */
    int getWidth();

    /**
     * Get the height of the grid
     * @return returns integer of the grid height
     */
    int getHeight();

    /**
     * Get the color that needs to be drawn for a specific point
     * @param p Point of the grid
     * @return The color of a point in the grid
     */
    Color getColor(Point p);

    /**
     * Get a list of all points in the grid
     * @return Returns ArrayList containing GridPoints
     */
    ArrayList<GridPoint> getPointList();

    /**
     * Get the type of this specific GridPoint
     * @param p Point defining the GridPoint
     * @return GridPointType enum
     */
    GridPointType getPointType(Point p);

}
