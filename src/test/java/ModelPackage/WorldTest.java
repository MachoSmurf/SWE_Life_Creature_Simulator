package ModelPackage;

import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

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
        World world = new World(500,40,
                1500,Digestion.Carnivore,100,1500,4, 600, 500,900,400,300, 10,
                1400, Digestion.Herbivore, 100, 1400, 6, 700, 500, 750, 400, 300, 10,
                2000, Digestion.Nonivore, 0, 1750, 8, 1000, 800, 100, 300, 200, 10,
                1750, Digestion.Omnivore, 45, 2500, 2, 500, 400, 1500, 600, 300, 30,
                40, 40);
        //world.doStep();

    }
}
