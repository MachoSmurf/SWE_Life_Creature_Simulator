package LifePackage;

import DataMediatorPackage.DatabaseMediator;
import ModelPackage.*;
import ViewPackage.FXMLSimulatorController;
import com.sun.org.apache.xerces.internal.impl.xpath.XPath;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

class SimulationTest {

    Simulation testSimulation;
    Grid testGrid;
    FXMLSimulatorController testSimController;
    DatabaseMediator testDataMediator;
    World testWorld;
    StepResult testStepResult;


    @BeforeEach
    void setUp() {
        // create a testDataMediator
        testDataMediator = new DatabaseMediator();

        // create a testGrid
        int testGridWidth = 20;
        int testGridHeight = 20;

        testGrid = new Grid(testGridWidth, testGridHeight);

        //island 1
        testGrid.setPointType(new Point(2, 2), GridPointType.Ground);
        testGrid.setPointType(new Point(2, 3), GridPointType.Ground);
        testGrid.setPointType(new Point(2, 4), GridPointType.Ground);
        testGrid.setPointType(new Point(2, 5), GridPointType.Ground);
        testGrid.setPointType(new Point(3, 2), GridPointType.Ground);
        testGrid.setPointType(new Point(3, 3), GridPointType.Ground);
        testGrid.setPointType(new Point(3, 4), GridPointType.Ground);
        testGrid.setPointType(new Point(3, 5), GridPointType.Ground);
        testGrid.setPointType(new Point(4, 2), GridPointType.Ground);
        testGrid.setPointType(new Point(4, 3), GridPointType.Ground);
        testGrid.setPointType(new Point(4, 4), GridPointType.Ground);
        testGrid.setPointType(new Point(4, 5), GridPointType.Ground);
        testGrid.setPointType(new Point(5, 2), GridPointType.Ground);
        testGrid.setPointType(new Point(5, 3), GridPointType.Ground);
        testGrid.setPointType(new Point(5, 4), GridPointType.Ground);
        testGrid.setPointType(new Point(5, 5), GridPointType.Ground);

        testSimulation = new Simulation(500, 25,
                1500, 1500, 4, 600, 500, 900, 400, 300, 5,
                1400, 1400, 6, 700, 500, 750, 400, 300, 5,
                2000, 1750, 8, 1000, 800, 500, 300, 200, 5,
                1750, 45, 2500, 2, 500, 400, 1500, 600, 300, 5,
                testGrid, testSimController, 1);

        testWorld = new World(500, 25,
                1500, 1500, 4, 600, 500, 900, 400, 300, 5,
                1400, 1400, 6, 700, 500, 750, 400, 300, 5,
                2000, 1750, 8, 1000, 800, 500, 300, 200, 5,
                1750, 45, 2500, 2, 500, 400, 1500, 600, 300, 5, testGrid);
    }

    @Test
    void setSimulationSpeed() {

    }

    @Test
    void startSimulation() {
    }

    @Test
    void stopSimulation() {
    }

    @Test
    void saveSimulation() {
        // create a file name
        String simulationName = "TestSimulationName";

        // Save the testSimulation with simulationName that contains the filename.
        testSimulation.saveSimulation(simulationName);

        // Load the file stated above and put it in result. I used the method loadSimulation()
        World result = testDataMediator.loadSimulation(simulationName);
        World expResult = testWorld;

        boolean areEqual;

        // Check of result and expResult are equal
        if (result.equals(expResult)) {
            areEqual = true;
            Assert.assertTrue(areEqual);
        } else {
            areEqual = false;
            Assert.assertFalse(areEqual);
        }

    }

    @Test
    void loadSimulation() {
        // method test in method: saveSimulation().
    }

    @Test
    void saveStepResult() {
        // Create a filename
        String stepResult = "TestStepResultName";

        // Save the testSimulation with simulationName that contains the filename.
        testSimulation.saveSimulation(stepResult);

        // load the file stated above and put it in result. I used the method loadStepResult()
        StepResult result = testSimulation.loadStepResult(stepResult);
        StepResult expResult = testWorld.doStep();

        boolean areEqual;
        // Check of result and expResult are equal
        if (result.equals(expResult)) {
            areEqual = true;
            Assert.assertTrue(areEqual);
        } else {
            areEqual = false;
            Assert.assertFalse(areEqual);
        }

    }

    @Test
    void loadStepResult() {
        // method test in method: saveStepResult().
    }
}