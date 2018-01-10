package EditorPackage;

import DataMediatorPackage.FileMediator;
import ModelPackage.Grid;
import ModelPackage.GridClone;


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
    public GridClone loadGrid(String gridName)
    {
        return fileMediator.loadGrid(gridName);
    }

    @Override
    public void saveGrid(GridClone grid, String gridName)
    {

        fileMediator.saveGrid(grid, gridName);
    }

    @Override
    public void deleteGrid(String gridName)
    {
        String gridFile = gridName + ".txt";
        File file = new File("C:\\Users\\Public\\Documents\\" +gridFile );

        try
        {
            if(file.exists())
            {
                file.delete();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }


    }


}
