package ViewPackage;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.stage.*;

/**
 *
 * @author Natascha Zorg-Wijnhoven
 */
public class LifeCreatureSimulator extends Application{

    @Override
    public void start(Stage primaryStage) throws Exception {
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ViewPackage/FXMLLogin.fxml"));

            Parent root = (Parent)fxmlLoader.load();
            FXMLLoginController controllerLogin = (FXMLLoginController)fxmlLoader.getController();

            Scene scene = new Scene(root);

            primaryStage.setScene(scene);

            primaryStage.show();

            controllerLogin.setStage(primaryStage);
        }
        catch(IOException e){
            System.out.println(e);
        }
    }

    public static void main(String[] args) throws Exception {
        launch(args);
    }
}
