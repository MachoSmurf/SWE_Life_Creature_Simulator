package ModelPackage;

import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import java.awt.*;

public class WorldTest {
    MovementPlanner movement;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    public void createWorld () throws Exception {
        /**
        movement = new MovementPlanner();
        Point puntA = new Point(10,10);
        Point puntB = new Point(20,20);
        List<Point> next = movement.findPath(puntA, puntB);
        Point puntC = new Point(10,10);
        Point puntD = new Point(20,20);
        List<Point> next1 = movement.findPath(puntC, puntD);
        Point puntE = new Point(10,10);
        Point puntF = new Point(20,20);
        List<Point> next2 = movement.findPath(puntE, puntF);
        Point puntG = new Point(10,10);
        Point puntH = new Point(20,20);
        List<Point> next3 = movement.findPath(puntG, puntH);
         */
        int testGridWidth = 20;
        int testGridHeight = 20;

        Grid grid = new Grid(testGridWidth, testGridHeight);

        //island 1
        grid.setPointType(new Point(2,2), GridPointType.Ground);
        grid.setPointType(new Point(2,3), GridPointType.Ground);
        grid.setPointType(new Point(2,4), GridPointType.Ground);
        grid.setPointType(new Point(2,5), GridPointType.Ground);
        grid.setPointType(new Point(3,2), GridPointType.Ground);
        grid.setPointType(new Point(3,3), GridPointType.Ground);
        grid.setPointType(new Point(3,4), GridPointType.Ground);
        grid.setPointType(new Point(3,5), GridPointType.Ground);
        grid.setPointType(new Point(4,2), GridPointType.Ground);
        grid.setPointType(new Point(4,3), GridPointType.Ground);
        grid.setPointType(new Point(4,4), GridPointType.Ground);
        grid.setPointType(new Point(4,5), GridPointType.Ground);
        grid.setPointType(new Point(5,2), GridPointType.Ground);
        grid.setPointType(new Point(5,3), GridPointType.Ground);
        grid.setPointType(new Point(5,4), GridPointType.Ground);
        grid.setPointType(new Point(5,5), GridPointType.Ground);

        //island 2
        grid.setPointType(new Point(10,2), GridPointType.Ground);
        grid.setPointType(new Point(10,3), GridPointType.Ground);
        grid.setPointType(new Point(10,4), GridPointType.Ground);
        grid.setPointType(new Point(10,5), GridPointType.Ground);
        grid.setPointType(new Point(11,2), GridPointType.Ground);
        grid.setPointType(new Point(11,3), GridPointType.Ground);
        grid.setPointType(new Point(11,4), GridPointType.Ground);
        grid.setPointType(new Point(11,5), GridPointType.Obstacle);
        grid.setPointType(new Point(12,2), GridPointType.Obstacle);
        grid.setPointType(new Point(12,3), GridPointType.Ground);
        grid.setPointType(new Point(12,4), GridPointType.Ground);
        grid.setPointType(new Point(12,5), GridPointType.Ground);
        grid.setPointType(new Point(13,2), GridPointType.Ground);
        grid.setPointType(new Point(13,3), GridPointType.Ground);
        grid.setPointType(new Point(13,4), GridPointType.Ground);
        grid.setPointType(new Point(13,5), GridPointType.Ground);

        //island 3
        grid.setPointType(new Point(10,10), GridPointType.Ground);
        grid.setPointType(new Point(10,11), GridPointType.Ground);
        grid.setPointType(new Point(10,12), GridPointType.Ground);
        grid.setPointType(new Point(10,13), GridPointType.Ground);
        grid.setPointType(new Point(11,10), GridPointType.Ground);
        grid.setPointType(new Point(11,11), GridPointType.Ground);
        grid.setPointType(new Point(11,12), GridPointType.Ground);
        grid.setPointType(new Point(11,13), GridPointType.Ground);
        grid.setPointType(new Point(12,10), GridPointType.Ground);
        grid.setPointType(new Point(12,11), GridPointType.Ground);
        grid.setPointType(new Point(12,12), GridPointType.Ground);
        grid.setPointType(new Point(12,13), GridPointType.Ground);
        grid.setPointType(new Point(13,10), GridPointType.Ground);
        grid.setPointType(new Point(13,11), GridPointType.Ground);
        grid.setPointType(new Point(13,12), GridPointType.Ground);
        grid.setPointType(new Point(13,13), GridPointType.Ground);

        World world = new World(500,40,
                1500,Digestion.Carnivore,100,1500,4, 600, 500,900,400,300, 10,
                1400, Digestion.Herbivore, 100, 1400, 6, 700, 500, 750, 400, 300, 10,
                2000, Digestion.Nonivore, 0, 1750, 8, 1000, 800, 100, 300, 200, 10,
                1750, Digestion.Omnivore, 45, 2500, 2, 500, 400, 1500, 600, 300, 30,
                grid);
        world.doStep();
    }
}
