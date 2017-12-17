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

    public Grid(int width, int height) {
        pointList = new ArrayList<>();
        this.width = width;
        this.height = height;

        for (int w=0; w<width; w++){
            for (int h=0; h<height; h++){
                pointList.add(new GridPoint(w, h));
            }
        }
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
