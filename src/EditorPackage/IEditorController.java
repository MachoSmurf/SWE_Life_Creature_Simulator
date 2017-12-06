package EditorPackage;

public interface IEditorController {

    /**
     * Loads a grid based on a given name
     * @param gridName
     */
    public void loadGrid(String gridName);

    /**
     * Saves a grid using a given name
     * @param gridName
     */
    public void saveGrid(String gridName);

    /**
     * deletes a grid using a given name
     * @param gridName
     */
    public void deleteGrid(String gridName);
}
