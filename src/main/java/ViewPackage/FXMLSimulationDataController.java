package ViewPackage;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * FXML Controller class responsible for all functionalities behind the SimulationData-screen
 *
 * @author Natascha Zorg-Wijnhoven
 */
public class FXMLSimulationDataController extends UIController implements Initializable {

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    public void onShowSimulationDataClick(ActionEvent actionEvent) {
    }

    public void onCancelClick(ActionEvent actionEvent) {
        try{
            FXMLHomepage2Controller h2 = (FXMLHomepage2Controller) changeScreen("/ViewPackage/FXMLHomepage2.fxml", null);
        }
        catch(IOException ioe){
            System.out.println(ioe);
            showWarning("Fout", "Het scherm kon niet worden geladen.");
        }
    }
}

























































