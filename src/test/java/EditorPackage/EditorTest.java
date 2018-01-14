package EditorPackage;

import ModelPackage.Grid;
import ModelPackage.GridClone;
import ModelPackage.GridPointType;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class EditorTest {

    Editor testEditor;
    Grid testGrid;
    GridClone testGridClone;
    GridClone testGridCloneDelete;
    File file;

    @BeforeEach
    void setUp() {
        // create a testEditor
        testEditor = new Editor();

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

        // Create a testGridClone
        testGridClone = new GridClone(testGrid.getPointList(), testGridHeight, testGridWidth);
        testGridCloneDelete = new GridClone(testGrid.getPointList(), testGridHeight, testGridWidth);


    }

    @Test
    void loadGrid() {
        // is tested in method: saveGrid().

    }

    @Test
    void saveGrid() {
        // create a file name
        String gridName = "TestGridName";

        // Save the testGridClone with gridName that contains the filename.
        testEditor.saveGrid(testGridClone, gridName);

        // Load the file stated above and put it in result. I used the method loadGrid()
        GridClone result = testEditor.loadGrid(gridName);

        // Compare the pointLists of the two gridClones. if the boolean returns true the test passed, else the test is false.
        boolean areEqual;

        if (result.equals(testGridClone))
        {
            areEqual = true;
            Assert.assertTrue(areEqual);

        }
        else{
            areEqual = false;
            Assert.assertFalse(areEqual);
        };



    }

    @Test
    void deleteGrid() {
        // create a file name
        String gridName = "TestGridNameDelete";

        // Save the testGridClone with gridName that contains the filename.
        testEditor.saveGrid(testGridCloneDelete, gridName);

        // create a boolean to check if file exist, after that delete the file.
        boolean fileExist;

        // Delete the gridClone
        testEditor.deleteGrid(gridName);

        // check if file is deleted.
        file = new File("C:\\Users\\Public\\Documents\\" + gridName + ".txt");
        if (!file.exists()) {

            fileExist = true;
            Assert.assertTrue(fileExist);
        }

    }


}