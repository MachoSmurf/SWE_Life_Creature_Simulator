package TestPackage;

import ModelPackage.Grid;
import ModelPackage.IWorld;
import ModelPackage.MovementPlanner;
import ModelPackage.World;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MovementPlannerTest {


    @org.junit.jupiter.api.BeforeEach
    void setUp() {
    }

    @org.junit.jupiter.api.AfterEach
    void tearDown() {
    }

    @org.junit.jupiter.api.Test
    void findPath() {
        assertEquals(1, 1);
    }

    @Test
    void testPathOne(){
        int testGridWidth = 10;
        int testGridHeight = 10;

        MovementPlanner planner = new MovementPlanner();
        planner.initializePlanner(null, null, null, new Grid(testGridWidth, testGridHeight));
        if (planner.getTotalMotionPoints() != (testGridHeight * testGridWidth)){
            fail("MotionPoint count does not match grid");
        }
        if (planner.getTotalAdjacentCount() != (testGridHeight * testGridWidth * 8)){
            fail("Not all adjacent points generated");
        }
    }

}