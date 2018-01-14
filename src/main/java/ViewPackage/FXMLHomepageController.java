package ViewPackage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;

/**
 * FXML Controller class responsible for all functionalities behind the Homepage-screen
 *
 * @author Natascha Zorg-Wijnhoven
 */
public class FXMLHomepageController extends UIController implements Initializable {

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    public void onSimulationClick(ActionEvent actionEvent) {
        try{
            FXMLSimulatorController si = (FXMLSimulatorController) changeScreen("/ViewPackage/FXMLSimulator.fxml", null);
        }
        catch(IOException ioe){
            System.out.println(ioe);
            showWarning("Fout", "Het scherm kon niet worden geladen.");
        }
    }

    public void onUserManagementClick(ActionEvent actionEvent) {
        try{
            FXMLUserManagementController u = (FXMLUserManagementController) changeScreen("/ViewPackage/FXMLUserManagement.fxml", null);
        }
        catch(IOException ioe){
            System.out.println(ioe);
            showWarning("Fout", "Het scherm kon niet worden geladen.");
        }
    }

    public void onLogoutClick(ActionEvent actionEvent) {
        try{
            FXMLLoginController l = (FXMLLoginController) changeScreen("/ViewPackage/FXMLLogin.fxml", null);
        }
        catch(IOException ioe){
            System.out.println(ioe);
            showWarning("Fout", "Het scherm kon niet worden geladen.");
        }
    }
}
