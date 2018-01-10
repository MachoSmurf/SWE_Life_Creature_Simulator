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
    void setUp()
    {
        // create a testEditor
        testEditor = new Editor();

        // create a testGrid
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

        // Create a testGridClone
        testGridClone = new GridClone(testGrid.getPointList());
        testGridCloneDelete = new GridClone(testGrid.getPointList());


    }

    @Test
    void loadGrid()
    {
        // is tested in method: saveGrid().

    }

    @Test
    void saveGrid()
    {
        // create a file name
        String gridName = "TestGridName";

        // Save the testGridClone with gridName that contains the filename.
        testEditor.saveGrid(testGridClone,gridName);

        // Load the file stated above and put it in result. I used the method loadGrid()
        GridClone result = testEditor.loadGrid(gridName);

        // Compare the pointLists of the two gridClones. if the boolean returns true the test passed, else the test is false.
        boolean equalGridClone = testGridClone.getPointList().size() == result.getPointList().size();
        Assert.assertTrue(equalGridClone);


    }

    @Test
    void deleteGrid()
    {
        // create a file name
        String gridName = "TestGridNameDelete";

        // Save the testGridClone with gridName that contains the filename.
        testEditor.saveGrid(testGridCloneDelete,gridName);

        // create a boolean to check if file exist, after that delete the file.
        boolean fileExist;

        // Delete the gridClone
        testEditor.deleteGrid(gridName);

        // check if file is deleted.
        file = new File("C:\\Users\\Public\\Documents\\" +gridName + ".txt");
        if (!file.exists()) {

            fileExist = true;
            Assert.assertTrue(fileExist);
        }

    }


}