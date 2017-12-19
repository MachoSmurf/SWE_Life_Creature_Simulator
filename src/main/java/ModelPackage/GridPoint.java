package ModelPackage;

import java.awt.*;
import java.io.Serializable;

/**
 * Specific point in the grid, containing information on whether it is land or water and as which color the GUI should show it.
 */
public class GridPoint implements Cloneable, Serializable {

    private int x;
    private int y;
    private boolean water;
    private Color color;

    public GridPoint(int x, int y) {
        /*this.x = x;
        this.y = y;
        water = true;*/
        this(x, y, true);
    }

    public GridPoint(int x, int y, boolean water){
        this.x = x;
        this.y = y;
        this.water = water;
    }

    /**
     * Get the X coordinate of this Gridpoint
     * @return x part of coordinate for this GridPoint
     */
    public int getX(){
        return x;
    }

    /**
     * Get the Y coordinate of this GridPoint
     * @return Y part of coordinate for this GridPoint
     */
    public int getY(){
        return y;
    }

    /**
     * Gets the color of this GridPoint
     * @return Color of the specified GridPoint
     */
    public Color getColor(){
        return this.color;
    }

    /**
     * Sets the color of this GridPoint
     * @param color that has to be set
     */
    public void setColor(Color color){
        this.color = color;
    }

    public boolean isWater(){
        return water;
    }

    public void setWater(boolean water){
        this.water = water;
    }

    public boolean getWater(){
        return water;
    }
}
