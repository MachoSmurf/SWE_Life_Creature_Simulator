package ViewPackage;

import EditorPackage.IEditorController;
import LifePackage.ILifeController;
import ModelPackage.StepResult;
import UserPackage.IUserController;
import UserPackage.User;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Responsible for controlling the switch between pages and storing some general UI functions and data
 */
public abstract class UIController implements ILifeResult{
    protected ILifeController simulation1;
    protected ILifeController simulation2;
    protected ILifeController simulation3;
    protected ILifeController simulation4;
    protected IUserController user;
    protected IEditorController editor;
    protected List<User> users;
    protected static Stage mainStage;

    protected UIController changeScreen(String fxmlPath, ActionEvent event) throws IOException{

        this.users = new ArrayList<>();

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlPath));

        Parent root = (Parent)fxmlLoader.load();

        Scene current_scene = new Scene(root);
        Stage app_stage;

        if (event == null)
        {
            app_stage = mainStage;
        }
        else
        {
            app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        }

        UIController controller = (UIController)fxmlLoader.getController();

        app_stage.hide();
        app_stage.setScene(current_scene);
        app_stage.show();

        return controller;
    }

    public void setStage(Stage s){
        mainStage = s;
    }

    protected void showWarning(String title, String text){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(text);
        alert.show();
    }

    protected void showSucces(String title, String text){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(text);
        alert.show();
    }

    @Override
    public void updateSimulationResults(StepResult simStatus) {
    }
}