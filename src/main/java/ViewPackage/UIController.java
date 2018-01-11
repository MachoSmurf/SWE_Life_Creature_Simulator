package ViewPackage;

import java.io.IOException;

//import DataMediatorPackage.DatabaseMediator;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import LifePackage.ILifeController;
import UserPackage.IUserController;
//import UserPackage.UserController;
import java.awt.event.ActionEvent;

/**
 * Responsible for controlling the switch between pages and storing some general UI functions and data
 *
 * @author Natascha Zorg-Wijnhoven
 */
public abstract class UIController{
    protected ILifeController simulation1;
    protected ILifeController simulation2;
    protected ILifeController simulation3;
    protected ILifeController simulation4;
    protected IUserController iUser1;
    //protected UserController user1;
    protected static Stage mainStage;

    public void setIlifeController1(ILifeController simulation1){
        this.simulation1 = simulation1;
    }

    public void setIlifeController2(ILifeController simulation2){
        this.simulation2 = simulation2;
    }

    public void setIlifeController3(ILifeController simulation3){
        this.simulation3 = simulation3;
    }

    public void setIlifeController4(ILifeController simulation4){
        this.simulation4 = simulation4;
    }

    public void setIUserController(IUserController iUser1){
        this.iUser1 = iUser1;
    }

    //public void setUserController(UserController user1){
        //this.user1 = user1;
    //}

    protected UIController changeScreen(String fxmlPath, ActionEvent event) throws IOException{

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
        controller.setIlifeController1(simulation1);
        controller.setIlifeController2(simulation2);
        controller.setIlifeController3(simulation3);
        controller.setIlifeController4(simulation4);
        controller.setIUserController(iUser1);
        //controller.setUserController(user1);

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
}