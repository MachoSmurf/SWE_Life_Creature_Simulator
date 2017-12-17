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
     * @return
     */
    public int getX(){
        return x;
    }

    /**
     * Get the y coordinate of this GridPoint
     * @return
     */
    public int getY(){
        return y;
    }

    public Color getColor(){
        return this.color;
    }

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
