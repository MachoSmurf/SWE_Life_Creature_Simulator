package ModelPackage;

import java.awt.*;
import java.util.ArrayList;

public interface IGrid {

    /**
     * Get the width of the grid
     * @return returns integer of grid width
     */
    public int getWidth();

    /**
     * Get the height of the grid
     * @return returns integer of the grid height
     */
    public int getHeight();

    /**
     * Get the color that needs to be drawn for a specific point
     * @param x the X coordinate of the desired gridPoint
     * @param y the Y coordinate of the desired gridPoint
     * @return The color of a point in the grid
     */
    public Color getColor(int x, int y);

    /**
     * Get a list of all points in the grid
     * @return Returns ArrayList containing GridPoints
     */
    public ArrayList<GridPoint> getPointList();
}
