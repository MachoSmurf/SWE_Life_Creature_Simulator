package ModelPackage;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;

public class Grid implements Cloneable, IGrid {
    @Override
    public int getWidth() {
        return 0;
    }

    @Override
    public int getHeight() {
        return 0;
    }

    @Override
    public Color getColor(int x, int y) {
        return null;
    }

    @Override
    public ArrayList<GridPoint> getPointList() {
        return null;
    }
}
