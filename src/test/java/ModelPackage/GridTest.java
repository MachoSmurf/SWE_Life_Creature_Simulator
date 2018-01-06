package ModelPackage;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

class GridTest {
    Grid testGrid;

    @BeforeEach
    void setUp() {
        testGrid = new Grid(20, 20);
        //populate with obstacles
        testGrid.setPointType(new Point(1,1), GridPointType.Obstacle);
        testGrid.setPointType(new Point(19,19), GridPointType.Obstacle);

        //populate with ground
        testGrid.setPointType(new Point(2, 2), GridPointType.Ground);
        testGrid.setPointType(new Point(18, 18), GridPointType.Ground);

        //try to override obstacle color (should fail)
        testGrid.setColor(new Point(1,1), Color.RED);
        //try to override ground color (should pass)
        testGrid.setColor(new Point(2,2), Color.GREEN);
        //try to override water color (should pass)
        testGrid.setColor(new Point(3,3), new Color(100, 100, 100));

    }

    @Test
    void getWidth() {
        assertEquals(20, testGrid.getWidth());
    }

    @Test
    void getHeight() {
        assertEquals(20, testGrid.getHeight());
    }

    @Test
    void getPointType() {
        assertEquals(GridPointType.Water, testGrid.getPointType(new Point(3,3)));
        assertEquals(GridPointType.Ground, testGrid.getPointType(new Point(2,2)));
        assertEquals(GridPointType.Obstacle, testGrid.getPointType(new Point(1,1)));
    }

    @Test
    void getColor() {
        //check the obstacle colors
        assertEquals(Color.BLACK, testGrid.getColor(new Point(1,1)), "Expected black for obstacle");
        assertEquals(Color.BLACK, testGrid.getColor(new Point(19,19)),"Expected black for obstacle");

        //check the ground colors
        assertEquals(Color.white, testGrid.getColor(new Point(18,18)), "Expected white for ground");
        assertEquals(Color.GREEN, testGrid.getColor(new Point(2,2)), "Expected green set on ground");

        //check the water colors
        assertEquals(Color.blue, testGrid.getColor(new Point(3,0)), "Expected blue for water");
        assertEquals(100, testGrid.getColor(new Point(3,3)).getRed(), "Expected 100,100,100 set on water");
        assertEquals(100, testGrid.getColor(new Point(3,3)).getGreen(), "Expected 100,100,100 set on water");
        assertEquals(100, testGrid.getColor(new Point(3,3)).getBlue(), "Expected 100,100,100 set on water");

        //reset colors
        testGrid.resetColor(new Point(3,3));
        testGrid.resetColor(new Point(2,2));

        //test original colors
        assertEquals(Color.blue, testGrid.getColor(new Point(3,3)), "Expected blue for water");
        assertEquals(Color.WHITE, testGrid.getColor(new Point(2,2)), "Expected white for ground");
    }
}