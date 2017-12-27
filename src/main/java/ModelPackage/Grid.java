package ModelPackage;

import java.awt.*;
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
                pointList.set(getListPosition(new Point(w, h)), new GridPoint(w, h));
            }
        }
    }

    /**
     * Returns the position in the list according to the formula
     * @param p Point representing the x and y
     * @return integer representing the position of the element in the list
     */
    private int getListPosition(Point p){
        return (width * ((int)p.getY()+1)) + ((int)p.getX()-width);
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
    public Color getColor(Point p) {
        int elementNumber = getListPosition(p);
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

    @Override
    public GridPointType getPointType(Point p) {
        return pointList.get(getListPosition(p)).getType();
    }

    public void setPointType(Point p, GridPointType gpt){
        pointList.get(getListPosition(p)).setType(gpt);
    }

    /**
     * Sets the color of a specific GridPoint
     * @param p Point representing the GridPoint
     * @param color Color that has to be assigned to the gridPoint
     */
    public void setColor(Point p, Color color){
        int elementNumber = getListPosition(p);
        pointList.get(elementNumber).setColor(color);
    }

    /**
     * resets gridPoint p Color to the default value associated to the GridPointType
     * @param p Point representing this GridPoint
     */
    public void resetColor(Point p){
        pointList.get(getListPosition(p)).resetColor();
    }
}
