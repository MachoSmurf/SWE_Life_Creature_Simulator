package ModelPackage;

import java.awt.*;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Collection of GridPoints combined with some "metadata" to describe the current situation of the world.
 */
public class Grid implements Cloneable, IGrid {

    int width;
    int height;
    ArrayList<GridPoint> pointList;

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

    private int getListPosition(int x, int y){
        return (width * (y+1)) + (x-width);
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
    public Color getColor(int x, int y) {
        return null;
    }

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
}
