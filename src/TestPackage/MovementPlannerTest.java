package TestPackage;

import ModelPackage.*;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class MovementPlannerTest {


    @org.junit.jupiter.api.BeforeEach
    void setUp() {
    }

    @org.junit.jupiter.api.AfterEach
    void tearDown() {
    }

    @Test
    void testGrid(){
        int testGridWidth = 100;
        int testGridHeight = 100;

        MovementPlanner planner = new MovementPlanner();
        planner.initializePlanner(new ArrayList<>(), new Grid(testGridWidth, testGridHeight));
        if (planner.getTotalMotionPoints() != (testGridHeight * testGridWidth)){
            fail("MotionPoint count does not match grid");
        }
        if (planner.getTotalAdjacentCount() != (testGridHeight * testGridWidth * 8)){
            fail("Not all correct adjacent points generated. Found " + planner.getTotalAdjacentCount() + ", expected " + (testGridHeight * testGridWidth * 8));
        }
    }

    @Test
    void testObstacleGrid(){
        int testGridWidth = 10;
        int testGridHeight = 10;
        ArrayList<Obstacle> obstacleList = new ArrayList<>();
        obstacleList.add(new Obstacle(5,5,0));

        MovementPlanner planner = new MovementPlanner();
        planner.initializePlanner(obstacleList, new Grid(testGridWidth, testGridHeight));
        int expectedPointCount = ((testGridHeight * testGridWidth)-1);
        if (planner.getTotalMotionPoints() != expectedPointCount){
            fail("MotionPoint count does not match grid minus obstacles " + planner.getTotalMotionPoints() + " points found, expected: " + expectedPointCount);
        }
        expectedPointCount = (testGridHeight * testGridWidth * 8) - 8;
        if (planner.getTotalAdjacentCount() != expectedPointCount){
            fail("Not all correct adjacent points generated. Found " + planner.getTotalAdjacentCount() + ", expected: " + expectedPointCount);
        }
    }

    @Test
    void testPathNoObstacle(){
        int testGridWidth = 10;
        int testGridHeight = 10;
        ArrayList<Obstacle> obstacleList = new ArrayList<>();
        MovementPlanner planner = new MovementPlanner();
        planner.initializePlanner(obstacleList, new Grid(testGridWidth, testGridHeight));
        //TestPath1
        ArrayList<Point> resultList = planner.findPath(3, 5, 6, 5);
        if (resultList.size() != 3){
            fail("Path 1 failed. Expected 3 steps, found " + resultList.size());
        }
        //TestPath2
        resultList = planner.findPath(6, 5, 3, 5);
        if (resultList.size() != 3){
            fail("Path 2 failed. Expected 3 steps, found " + resultList.size());
        }
        //TestPath3
        resultList = planner.findPath(1, 0, 8, 0);
        if (resultList.size() != 3){
            fail("Path 3 failed. Expected 3 steps, found " + resultList.size());
        }
        //TestPath4
        resultList = planner.findPath(8, 0, 1, 0);
        if (resultList.size() != 3){
            fail("Path 4 failed. Expected 3 steps, found " + resultList.size());
        }
        //TestPath5
        resultList = planner.findPath(1, 9, 8, 9);
        if (resultList.size() != 3){
            fail("Path 5 failed. Expected 3 steps, found " + resultList.size());
        }
        //TestPath6
        resultList = planner.findPath(8, 9, 1, 9);
        if (resultList.size() != 3){
            fail("Path 6 failed. Expected 3 steps, found " + resultList.size());
        }
        //TestPath7
        resultList = planner.findPath(5, 1, 5, 8);
        if (resultList.size() != 3){
            fail("Path 7 failed. Expected 3 steps, found " + resultList.size());
        }
        //TestPath8
        resultList = planner.findPath(5, 8, 5, 1);
        if (resultList.size() != 3){
            fail("Path 8 failed. Expected 3 steps, found " + resultList.size());
        }
        //TestPath9
        resultList = planner.findPath(1, 8, 8, 1);
        if (resultList.size() != 3){
            fail("Path 9 failed. Expected 3 steps, found " + resultList.size());
        }
        //TestPath10
        resultList = planner.findPath(8, 1, 1, 8);
        if (resultList.size() != 3){
            fail("Path 10 failed. Expected 3 steps, found " + resultList.size());
        }
    }

    @Test
    void testPathObstacleCase11And12(){
        int testGridWidth = 10;
        int testGridHeight = 10;
        ArrayList<Obstacle> obstacleList = new ArrayList<>();
        //load edges (obstacle pattern for TP 11 & 12)
        for (int x=0; x<10; x++){
            for (int y=0; y<10; y++){
                if ((y==0) || (x==0) || (x==10) || (y==10)){
                    obstacleList.add(new Obstacle(x, y, 0));
                }
            }
        }
        MovementPlanner planner = new MovementPlanner();
        planner.initializePlanner(obstacleList, new Grid(testGridWidth, testGridHeight));
        //TestPath1
        ArrayList<Point> resultList = planner.findPath(3, 5, 6, 5);
        //path11
        if (resultList.size() != 3){
            fail("Path 11 failed. Expected 3 steps, found " + resultList.size());
        }
        //path12
        resultList = planner.findPath(6, 5, 3, 5);
        if (resultList.size() != 3){
            fail("Path 12 failed. Expected 3 steps, found " + resultList.size());
        }
    }

    @Test
    void testPathObstacleCase13And14(){
        int testGridWidth = 10;
        int testGridHeight = 10;
        ArrayList<Obstacle> obstacleList = new ArrayList<>();
        //load edges (obstacle pattern for TP 13 & 14)
        int x = 3;
        for (int y=0; y<10; y++){
            obstacleList.add(new Obstacle(x, y, 0));
        }
        MovementPlanner planner = new MovementPlanner();
        planner.initializePlanner(obstacleList, new Grid(testGridWidth, testGridHeight));
        //TestPath1
        ArrayList<Point> resultList = planner.findPath(1, 5, 6, 5);
        //path11
        if (resultList.size() != 5){
            fail("Path 13 failed. Expected 5 steps, found " + resultList.size());
        }
        //path12
        resultList = planner.findPath(6, 5, 1, 5);
        if (resultList.size() != 5){
            fail("Path 14 failed. Expected 5 steps, found " + resultList.size());
        }
    }

    @Test
    void testPathObstacleCase15And16(){
        int testGridWidth = 10;
        int testGridHeight = 10;
        ArrayList<Obstacle> obstacleList = new ArrayList<>();
        //load edges (obstacle pattern for TP 15 & 16)
        int x = 3;
        for (int y=0; y<10; y++){
            if (y != 6){
                obstacleList.add(new Obstacle(x, y, 0));
            }
        }
        MovementPlanner planner = new MovementPlanner();
        planner.initializePlanner(obstacleList, new Grid(testGridWidth, testGridHeight));
        //TestPath1
        ArrayList<Point> resultList = planner.findPath(1, 5, 5, 5);
        //path15
        if (resultList.size() != 4){
            fail("Path 15 failed. Expected 4 steps, found " + resultList.size());
        }
        //path16
        resultList = planner.findPath(5, 5, 1, 5);
        if (resultList.size() != 4){
            fail("Path 16 failed. Expected 4 steps, found " + resultList.size());
        }
    }
}