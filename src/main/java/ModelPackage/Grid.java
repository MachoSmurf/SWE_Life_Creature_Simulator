package ModelPackage;

import java.awt.*;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Collection of GridPoints combined with some "metadata" to describe the current situation of the world.
 */
public class Grid implements Cloneable, IGrid {

    //TODO: Write tests for classes Grid and GridPoint

    private int width;
    private int height;
    private ArrayList<GridPoint> pointList;

    /**
     * Generates a grid compliant with formula: (width * (y+1)) + (x - width). Compatible with MovementPlanner's
     * grid formulation:
     *
     * 6 7 8
     * 3 4 5
     * 0 1 2
     *
     * @param width width of the grid
     * @param height height of the grid
     */
    public Grid(int width, int height) {
        pointList = new ArrayList<>();
        this.width = width;
        this.height = height;

        //create correct size for list
        for (int i=0; i<(width*height); i++){
            pointList.add(null);
        }

        for (int w=0; w<width; w++){
            for (int h=0; h<height; h++){
                pointList.set(getListPosition(w, h), new GridPoint(w, h));
            }
        }
    }

    /**
     * Returns the position in the list according to the formula
     * @param x x part of coordinate
     * @param y y part of coordinate
     * @return integer representing the position of the element in the list
     */
    private int getListPosition(int x, int y){
        return (width * (y+1)) + (x-width);
    }

    /**
     * return the width of the grid
     * @return integer of the grid-width
     */
    @Override
    public int getWidth() {
        return width;
    }

    /**
     * return the height of the grid
     * @return integer of the height-width
     */
    @Override
    public int getHeight() {
        return height;
    }

    /**
     * Gets the color of a gridpoint represented by x and y
     * @return Color
     */
    @Override
    public Color getColor(int x, int y) {
        int elementNumber = getListPosition(x, y);
        return pointList.get(elementNumber).getColor();
    }

    /**
     * Gets all the points in the grid, ordered according to the formula
     * @return List of all GridPoints in the Grid
     */
    @Override
    public ArrayList<GridPoint> getPointList() {
        return pointList;
    }

    public void setWater(int x, int y, boolean water){
        for (GridPoint gp : pointList){
            if ((gp.getX() == x) && (gp.getY() == y)){
                gp.setWater(water);
            }
        }
    }

    public boolean getWater(int x, int y){
        for (GridPoint gp : pointList){
            if ((gp.getX() == x) && (gp.getY() == y)){
                return gp.getWater();
            }
        }
        return false;
    }

    /**
     * Sets the color of a specific GridPoint
     * @param x x part of coordinate defining the GridPoint
     * @param y y part of coordinate defining the GridPoint
     * @param color Color that has to be assigned to the gridPoint
     */
    public void setColor(int x, int y, Color color){
        int elementNumber = getListPosition(x, y);
        pointList.get(elementNumber).setColor(color);
    }
}
