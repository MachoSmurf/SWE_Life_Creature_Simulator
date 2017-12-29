package EditorPackage;

import ModelPackage.Grid;

import java.io.*;
import java.nio.file.Files;

/**
 * Controls the in and output of a editor instance. Also responsible for passing data to and from an IDataMediator instance
 */
public class Editor implements IEditorController{

    public Editor(){

    }

    @Override
    public Grid loadGrid(String gridName)
    {
        String FileName = gridName + ".txt";
        FileInputStream fin;
        try
        {
            fin = new FileInputStream(FileName);
            ObjectInputStream ois;
            ois = new ObjectInputStream(fin);

            return (Grid) ois.readObject();

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
        /*catch (IOException e)
        {
            e.printStackTrace();
        }
        catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }*/
    }

    @Override
    public void saveGrid(String gridName)
    {
        try
        {
            String FileName = gridName + ".txt";
            FileOutputStream fos = new FileOutputStream(FileName);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(gridName);
            oos.close();
        }
        catch (Exception e)
        {
            System.out.println("Error while saving grid");

        }
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
