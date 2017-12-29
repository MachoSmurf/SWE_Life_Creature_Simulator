package EditorPackage;

import ModelPackage.Grid;

public interface IEditorController {

    /**
     * Loads a grid based on a given name
     * @param gridName Name of the grid that has to be loaded
     */
    Grid loadGrid(String gridName);

    /**
     * Saves a grid using a given name
     * @param gridName Name of the grid that has to be saved
     */
    void saveGrid(String gridName);

    /**
     * deletes a grid using a given name
     * @param gridName Name of the grid that has to be deleted
     */
    void deleteGrid(String gridName);
}
