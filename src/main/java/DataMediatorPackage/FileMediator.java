package DataMediatorPackage;

import ModelPackage.*;
import UserPackage.User;

import java.io.*;

/**
 * Responsible for writing and reading data to and from flatfiles, if necessary using some form of encoding
 */
public class FileMediator implements IDataMediator {

    public FileMediator(){

    }

    @Override
    public User loadUser(String username, String password)
    {
        // DatabaseMediator
        return null;
    }

    @Override
    public void saveUser(User user)
    {
        // DatabaseMediator

    }

    @Override
    public Grid loadGrid(String gridName)
    {
        return this.<Grid>LoadFile(gridName);
    }

    @Override
    public void saveGrid(Grid grid, String gridName)
    {
        this.<Grid>SaveFile(grid, gridName);
    }

    @Override
    public StepResult loadSimulationResult(String simResultName)
    {
      return this.<StepResult>LoadFile(simResultName);
    }

    @Override
    public void saveSimulationResult(StepResult resultFrame, String simResultName)
    {
        this.<StepResult>SaveFile(resultFrame, simResultName);
    }

    @Override
    public World loadSimulation(String simulationName)
    {
        return this.<World>LoadFile(simulationName);
    }

    @Override
    public void saveSimulation(World simulation, String simulationName)
    {
        this.<World>SaveFile(simulation, simulationName);
    }

    public static <T> void SaveFile(T obj, String name)
    {
        File file;

        try
        {

            file = new File("C:\\Users\\Public\\Desktop" +name + ".txt");
            if (!file.exists()) {
                file.createNewFile();
            }
            FileOutputStream fos = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(obj);
            oos.close();
        }
        catch(Exception e)
        {
            System.out.println("Error while saving simulation result");
        }

    }

    public static <T> T LoadFile (String name)
    {
        String FileName = name + ".txt";
        FileInputStream fin;
        try
        {
            fin = new FileInputStream(FileName);
            ObjectInputStream ois;
            ois = new ObjectInputStream(fin);

            return (T) ois.readObject();

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return null;
    }

}
