package ModelPackage;

import org.junit.jupiter.api.Assertions;
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
    void testPathNoObstacle0() {
        int testGridWidth = 10;
        int testGridHeight = 10;
        ArrayList<Obstacle> obstacleList = new ArrayList<>();
        MovementPlanner planner = new MovementPlanner();
        planner.initializePlanner(obstacleList, new Grid(testGridWidth, testGridHeight));
        //TestPath1
        ArrayList<Point> resultList = null;
        try {
            resultList = planner.findPath(new Point(5, 5), new Point( 6, 5));
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("TP0 completed");
        if (resultList.size() != 2) {
            fail("Path 0 failed. Expected 2 steps, found " + resultList.size());
        }
    }

    @Test
    void testPathNoObstacle1() {
        int testGridWidth = 10;
        int testGridHeight = 10;
        ArrayList<Obstacle> obstacleList = new ArrayList<>();
        MovementPlanner planner = new MovementPlanner();
        planner.initializePlanner(obstacleList, new Grid(testGridWidth, testGridHeight));
        //TestPath1
        ArrayList<Point> resultList = null;
        try {
            resultList = planner.findPath(new Point(3, 5), new Point( 6, 5));
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("TP1 completed");
        if (resultList.size() != 4) {
            fail("Path 1 failed. Expected 4 steps, found " + resultList.size());
        }
    }

    @Test
    void testPathNoObstacle2() {
        int testGridWidth = 10;
        int testGridHeight = 10;
        ArrayList<Obstacle> obstacleList = new ArrayList<>();
        MovementPlanner planner = new MovementPlanner();
        planner.initializePlanner(obstacleList, new Grid(testGridWidth, testGridHeight));
        //TestPath1
        ArrayList<Point>
        //TestPath2
        resultList = null;
        try {
            resultList = planner.findPath(new Point(6, 5), new Point( 3, 5));
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("TP2 completed");
        if (resultList.size() != 4){
            fail("Path 2 failed. Expected 4 steps, found " + resultList.size());
        }
    }

    @Test
    void testPathNoObstacle3() {
        int testGridWidth = 10;
        int testGridHeight = 10;
        ArrayList<Obstacle> obstacleList = new ArrayList<>();
        MovementPlanner planner = new MovementPlanner();
        planner.initializePlanner(obstacleList, new Grid(testGridWidth, testGridHeight));
        //TestPath1
        ArrayList<Point>
        //TestPath3
        resultList = null;
        try {
            resultList = planner.findPath(new Point(1, 0), new Point( 8, 0));
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("TP3 completed");
        if (resultList.size() != 4){
            fail("Path 3 failed. Expected 4 steps, found " + resultList.size());
        }
    }

    @Test
    void testPathNoObstacle4() {
        int testGridWidth = 10;
        int testGridHeight = 10;
        ArrayList<Obstacle> obstacleList = new ArrayList<>();
        MovementPlanner planner = new MovementPlanner();
        planner.initializePlanner(obstacleList, new Grid(testGridWidth, testGridHeight));
        //TestPath1
        ArrayList<Point>
        //TestPath4
        resultList = null;
        try {
            resultList = planner.findPath(new Point(8, 0), new Point(1, 0));
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("TP4 completed");
        if (resultList.size() != 4) {
            fail("Path 4 failed. Expected 4 steps, found " + resultList.size());
        }
    }

    @Test
    void testPathNoObstacle5() {
        int testGridWidth = 10;
        int testGridHeight = 10;
        ArrayList<Obstacle> obstacleList = new ArrayList<>();
        MovementPlanner planner = new MovementPlanner();
        planner.initializePlanner(obstacleList, new Grid(testGridWidth, testGridHeight));
        //TestPath5
        ArrayList<Point> resultList = null;
        try {
            resultList = planner.findPath(new Point(1, 9), new Point(8, 9));
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("TP5 completed");
        if (resultList.size() != 4) {
            fail("Path 5 failed. Expected 4 steps, found " + resultList.size());
        }
    }

    @Test
    void testPathNoObstacle6() {
        int testGridWidth = 10;
        int testGridHeight = 10;
        ArrayList<Obstacle> obstacleList = new ArrayList<>();
        MovementPlanner planner = new MovementPlanner();
        planner.initializePlanner(obstacleList, new Grid(testGridWidth, testGridHeight));
        //TestPath6
        ArrayList<Point> resultList = null;
        try {
            resultList = planner.findPath(new Point(8, 9), new Point(1, 9));
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("TP6 completed");
        if (resultList.size() != 4) {
            fail("Path 6 failed. Expected 4 steps, found " + resultList.size());
        }
    }

    @Test
    void testPathNoObstacle7() {
        int testGridWidth = 10;
        int testGridHeight = 10;
        ArrayList<Obstacle> obstacleList = new ArrayList<>();
        MovementPlanner planner = new MovementPlanner();
        planner.initializePlanner(obstacleList, new Grid(testGridWidth, testGridHeight));
        //TestPath7
        ArrayList<Point> resultList = null;
        try {
            resultList = planner.findPath(new Point(5, 1), new Point(5, 8));
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("TP7 completed");
        if (resultList.size() != 4) {
            fail("Path 7 failed. Expected 4 steps, found " + resultList.size());
        }
    }

    @Test
    void testPathNoObstacle8() {
        int testGridWidth = 10;
        int testGridHeight = 10;
        ArrayList<Obstacle> obstacleList = new ArrayList<>();
        MovementPlanner planner = new MovementPlanner();
        planner.initializePlanner(obstacleList, new Grid(testGridWidth, testGridHeight));
        //TestPath8
        ArrayList<Point> resultList = null;
        try {
            resultList = planner.findPath(new Point(5, 8), new Point(5, 1));
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("TP8 completed");
        if (resultList.size() != 4) {
            fail("Path 8 failed. Expected 4 steps, found " + resultList.size());
        }
    }

    @Test
    void testPathNoObstacle9() {
        int testGridWidth = 10;
        int testGridHeight = 10;
        ArrayList<Obstacle> obstacleList = new ArrayList<>();
        MovementPlanner planner = new MovementPlanner();
        planner.initializePlanner(obstacleList, new Grid(testGridWidth, testGridHeight));
        //TestPath9
        ArrayList<Point> resultList = null;
        try {
            resultList = planner.findPath(new Point(1, 8), new Point(8, 1));
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("TP9 completed");
        if (resultList.size() != 4) {
            fail("Path 9 failed. Expected 4 steps, found " + resultList.size());
        }
    }

    @Test
    void testPathNoObstacle10() {
        int testGridWidth = 10;
        int testGridHeight = 10;
        ArrayList<Obstacle> obstacleList = new ArrayList<>();
        MovementPlanner planner = new MovementPlanner();
        planner.initializePlanner(obstacleList, new Grid(testGridWidth, testGridHeight));
        //TestPath10
        ArrayList<Point> resultList = null;
        try {
            resultList = planner.findPath(new Point(8, 1), new Point(1, 8));
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("TP10 completed");
        if (resultList.size() != 4){
            fail("Path 10 failed. Expected 4 steps, found " + resultList.size());
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
                if ((y==0) || (x==0) || (x==9) || (y==9)){
                    obstacleList.add(new Obstacle(x, y, 0));
                }
            }
        }
        MovementPlanner planner = new MovementPlanner();
        planner.initializePlanner(obstacleList, new Grid(testGridWidth, testGridHeight));
        //TestPath1
        ArrayList<Point> resultList = null;
        try{
            resultList = planner.findPath(new Point(3, 5), new Point(6, 5));
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("TP11 completed");
        //path11
        if (resultList.size() != 4){
            fail("Path 11 failed. Expected 4 steps, found " + resultList.size());
        }

        //path12
        try {
            resultList = planner.findPath(new Point(6, 5), new Point(3, 5));
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("TP12 completed");
        if (resultList.size() != 4){
            fail("Path 12 failed. Expected 4 steps, found " + resultList.size());
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
        ArrayList<Point> resultList = null;
        try {
            resultList = planner.findPath(new Point(1, 5), new Point(6, 5));
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("TP13 completed");
        //path11
        if (resultList.size() != 6){
            fail("Path 13 failed. Expected 6 steps, found " + resultList.size());
        }
        //path12
        try {
            resultList = planner.findPath(new Point(6, 5), new Point(1, 5));
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("TP14 completed");
        if (resultList.size() != 6){
            fail("Path 14 failed. Expected 6 steps, found " + resultList.size());
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
        ArrayList<Point> resultList = null;
        try {
            resultList = planner.findPath(new Point(1, 5), new Point(5, 5));
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("TP15 completed");
        //path15
        if (resultList.size() != 5){
            fail("Path 15 failed. Expected 5 steps, found " + resultList.size());
        }
        //path16
        try {
            resultList = planner.findPath(new Point(5, 5), new Point(1, 5));
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("TP16 completed");
        if (resultList.size() != 5){
            fail("Path 16 failed. Expected 5 steps, found " + resultList.size());
        }
    }

    @Test
    public void testUnplanable(){
        //TestPath 17
        int testGridWidth = 10;
        int testGridHeight = 10;
        ArrayList<Obstacle> obstacleList = new ArrayList<>();
        //load edges (obstacle pattern like TP12)
        for (int x=0; x<10; x++){
            for (int y=0; y<10; y++){
                if ((y==0) || (x==0) || (x==9) || (y==9)){
                    obstacleList.add(new Obstacle(x, y, 0));
                }
            }
        }
        //create line like in TP 13
        int x = 3;
        for (int y=1; y<9; y++){
            obstacleList.add(new Obstacle(x, y, 0));
        }
        MovementPlanner planner = new MovementPlanner();
        planner.initializePlanner(obstacleList, new Grid(testGridWidth, testGridHeight));
        //TestPath1
        ArrayList<Point> resultList = null;
        try {
            resultList = planner.findPath(new Point(2, 5), new Point(6, 5));
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("TP17 completed");
        //path11
        if (resultList != null){
            fail("Path 17 failed. Expected unplannable");
        }
    }

    @Test
    public void testStartIsEnd(){
        //Testpath 18
        int testGridWidth = 10;
        int testGridHeight = 10;
        ArrayList<Obstacle> obstacleList = new ArrayList<>();
        MovementPlanner planner = new MovementPlanner();
        planner.initializePlanner(obstacleList, new Grid(testGridWidth, testGridHeight));
        //TestPath1
        ArrayList<Point> resultList = null;
        try {
            resultList = planner.findPath(new Point(2, 2), new Point(2, 2));
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("TP18 completed");
        //path11
        if (resultList != null){
            fail("Path 18 failed. Expected unplannable");
        }
    }

    @Test
    public void subGridTest(){
        int testGridWidth = 20;
        int testGridHeight = 20;

        Grid grid = new Grid(testGridWidth, testGridHeight);

        //island 1
        grid.setWater(2,2, false);
        grid.setWater(2,3, false);
        grid.setWater(2,4, false);
        grid.setWater(2,5, false);
        grid.setWater(3,2, false);
        grid.setWater(3,3, false);
        grid.setWater(3,4, false);
        grid.setWater(3,5, false);
        grid.setWater(4,2, false);
        grid.setWater(4,3, false);
        grid.setWater(4,4, false);
        grid.setWater(4,5, false);
        grid.setWater(5,2, false);
        grid.setWater(5,3, false);
        grid.setWater(5,4, false);
        grid.setWater(5,5, false);

        //island 2
        grid.setWater(10,2, false);
        grid.setWater(10,3, false);
        grid.setWater(10,4, false);
        grid.setWater(10,5, false);
        grid.setWater(11,2, false);
        grid.setWater(11,3, false);
        grid.setWater(11,4, false);
        grid.setWater(11,5, false);
        grid.setWater(12,2, false);
        grid.setWater(12,3, false);
        grid.setWater(12,4, false);
        grid.setWater(12,5, false);
        grid.setWater(13,2, false);
        grid.setWater(13,3, false);
        grid.setWater(13,4, false);
        grid.setWater(13,5, false);

        //island 3
        grid.setWater(10,10, false);
        grid.setWater(10,11, false);
        grid.setWater(10,12, false);
        grid.setWater(10,13, false);
        grid.setWater(11,10, false);
        grid.setWater(11,11, false);
        grid.setWater(11,12, false);
        grid.setWater(11,13, false);
        grid.setWater(12,10, false);
        grid.setWater(12,11, false);
        grid.setWater(12,12, false);
        grid.setWater(12,13, false);
        grid.setWater(13,10, false);
        grid.setWater(13,11, false);
        grid.setWater(13,12, false);
        grid.setWater(13,13, false);

        MovementPlanner planner = new MovementPlanner();
        planner.initializePlanner(new ArrayList<>(), grid);

        ArrayList<Point> result = null;
        try {
            result = planner.findPath(new Point(2,2), new Point(10,11));
        } catch (Exception e) {
            e.printStackTrace();
        }

        assertEquals(10, result.size(), "Failed to find correct number of steps");
        int livingAreaCount = 0;
        try{
            livingAreaCount = planner.getLivingAreas().size();
        }
        catch(Exception e){
            System.out.print("Failed to find livingareas");
        }
        assertEquals(4, livingAreaCount);
    }

    @Test
    void testDemoGrid() {
        int testGridWidth = 50;
        int testGridHeight = 50;
        ArrayList<Obstacle> obstacleList = new ArrayList<>();

        obstacleList.add(new Obstacle(15, 25, 0));
        obstacleList.add(new Obstacle(16, 22, 0));

        obstacleList.add(new Obstacle(19, 25, 0));
        obstacleList.add(new Obstacle(20, 25, 0));
        obstacleList.add(new Obstacle(21, 25, 0));
        obstacleList.add(new Obstacle(22, 25, 0));
        obstacleList.add(new Obstacle(23, 25, 0));
        obstacleList.add(new Obstacle(24, 25, 0));
        obstacleList.add(new Obstacle(25, 25, 0));
        obstacleList.add(new Obstacle(26, 25, 0));
        obstacleList.add(new Obstacle(27, 25, 0));
        obstacleList.add(new Obstacle(28, 25, 0));
        obstacleList.add(new Obstacle(30, 25, 0));
        obstacleList.add(new Obstacle(31, 25, 0));
        obstacleList.add(new Obstacle(32, 25, 0));
        obstacleList.add(new Obstacle(33, 25, 0));

        MovementPlanner planner = new MovementPlanner();
        planner.initializePlanner(obstacleList, new Grid(testGridWidth, testGridHeight));
        //TestPath1
        ArrayList<Point> resultList = null;
        try {
            resultList = planner.findPath(new Point(20, 20), new Point(30, 30));
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("TP11 completed");
    }
}