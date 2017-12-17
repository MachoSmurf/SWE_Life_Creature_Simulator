package EditorPackage;

public interface IEditorController {

    /**
     * Loads a grid based on a given name
     * @param gridName Name of the grid that has to be loaded
     */
    public void loadGrid(String gridName);

    /**
     * Saves a grid using a given name
     * @param gridName Name of the grid that has to be saved
     */
    public void saveGrid(String gridName);

    /**
     * deletes a grid using a given name
     * @param gridName Name of the grid that has to be deleted
     */
    public void deleteGrid(String gridName);
}
