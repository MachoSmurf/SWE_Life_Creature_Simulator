package ModelPackage;

public interface IWorld {

    /**
     * Executes a step in the simulation
     * @return Returns all the information necessary to show a frame on the GUI
     */
    public StepResult doStep();

    public void resetExtinction();

    public void disableExtinction();

    public void activateExtinctionNow();
}
