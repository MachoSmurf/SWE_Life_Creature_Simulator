package EditorPackage;

import DataMediatorPackage.FileMediator;
import ModelPackage.Grid;


import java.io.*;
import java.nio.file.Files;

/**
 * Controls the in and output of a editor instance. Also responsible for passing data to and from an IDataMediator instance
 */
public class Editor extends FileMediator implements IEditorController{

    FileMediator fileMediator = new FileMediator();

    public Editor()
    {

    }

    @Override
    public Grid loadGrid(String gridName)
    {
        return fileMediator.loadGrid(gridName);
    }

    @Override
    public void saveGrid(Grid grid, String gridName)
    {

        fileMediator.saveGrid(grid, gridName);
    }

    @Override
    public void deleteGrid(String gridName)
    {
        String gridFile = gridName + ".txt";
        File file = new File(gridFile);

        try
        {
            Files.deleteIfExists(file.toPath());
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }


    }


}
