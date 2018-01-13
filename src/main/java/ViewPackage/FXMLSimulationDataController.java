package ViewPackage;

import ModelPackage.World;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * FXML Controller class responsible for all functionalities behind the SimulationData-screen
 *
 * @author Natascha Zorg-Wijnhoven
 */
public class FXMLSimulationDataController extends UIController implements Initializable {

    @FXML private ComboBox simulations;
    private List<World> users;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //simulations.setItems(FXCollections.observableArrayList(ColorHair.values()));
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

























































