package ModelPackage;

import java.io.Serializable;

/**
 * Specific point in the grid, containing information on whether it is land or water and as which color the GUI should show it.
 */
public class GridPoint implements Cloneable, Serializable {

    public GridPoint() {
    }

    private int x;
    private int y;

    public GridPoint(int i, int j) {
        this.x = i;
        this.y = j;
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
}
