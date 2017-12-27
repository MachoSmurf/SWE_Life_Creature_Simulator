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
    private GridPointType type;

    public GridPoint(int x, int y) {
        this(x, y, GridPointType.Water);
    }

    public GridPoint(int x, int y, GridPointType gridPointType){
        this.x = x;
        this.y = y;
        type = gridPointType;
        switch(type){
            case Obstacle:
                this.setColor(Color.black);
                break;
            case Ground:
                this.setColor(Color.white);
                break;
            case Water:
                this.setColor(Color.blue);
                break;
        }
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
        //cant reset the color on obstacle
        if(this.getType() != GridPointType.Obstacle){
            this.color = color;
        }
        else{
            this.color = Color.black;
        }
    }

    public GridPointType getType() {
        return type;
    }

    public void setType(GridPointType t){
        this.type = t;
        resetColor();
    }

    public void resetColor(){
        switch (type){
            case Water:
                setColor(Color.blue);
                break;
            case Ground:
                setColor(Color.white);
                break;
            case Obstacle:
                setColor(Color.black);
                break;
        }
    }
}
