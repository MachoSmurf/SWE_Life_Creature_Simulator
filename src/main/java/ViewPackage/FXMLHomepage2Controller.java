package ViewPackage;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * FXML Controller class
 *
 * @author Natascha Zorg-Wijnhoven
 */
public class FXMLHomepage2Controller extends UIController implements Initializable {

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    public void onViewSimulationDataClick(ActionEvent actionEvent) {
        try{
            FXMLSimulationDataController sid = (FXMLSimulationDataController) changeScreen("/ViewPackage/FXMLSimulationData.fxml", null);
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




























