package EditorPackage;

import ModelPackage.Grid;
import ModelPackage.GridClone;

public interface IEditorController {

    /**
     * Loads a grid based on a given name
     *
     * @param gridName Name of the grid that has to be loaded
     */
    GridClone loadGrid(String gridName);

    /**
     * Saves a grid using a given name
     *
     * @param gridName Name of the grid that has to be saved
     */
    void saveGrid(GridClone grid, String gridName);

    /**
     * deletes a grid using a given name
     *
     * @param gridName Name of the grid that has to be deleted
     */
    void deleteGrid(String gridName);
}
