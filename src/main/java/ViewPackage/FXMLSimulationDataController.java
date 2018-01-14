package ViewPackage;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.scene.control.TextArea;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * FXML Controller class responsible for all functionalities behind the SimulationData-screen
 *
 * @author Natascha Zorg-Wijnhoven
 */
public class FXMLSimulationDataController extends UIController implements Initializable {

    @FXML private AnchorPane component;
    @FXML private TextArea textArea;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
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

    public void onUploadenClick(ActionEvent actionEvent) {
        //String filepath = "";
        //List<String> filesentences = new ArrayList<>();

        //Create a new instance of FileChooser class
        FileChooser fileChooser = new FileChooser();

        //Set dialog title
        fileChooser.setTitle("Open Simulation");

        //Show up the dialog
        File file = fileChooser.showOpenDialog(component.getScene().getWindow());

        //Add extension filters
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("TXT", "*.txt")
        );

        //Show file in TextArea
        BufferedReader buff = null;
        try {
            buff = new BufferedReader(new FileReader(file));
        } catch (IOException e) {
        } finally {
            try { buff.close(); } catch (Exception ex) { }
        }
    }
}

























































