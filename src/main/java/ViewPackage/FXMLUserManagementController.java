package ViewPackage;

import DataMediatorPackage.IDataMediator;
import UserPackage.User;
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
public class FXMLUserManagementController extends UIController implements Initializable {

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    public void onCancelClick(ActionEvent actionEvent) {
        try{
            FXMLHomepageController h1 = (FXMLHomepageController) changeScreen("/ViewPackage/FXMLHomepage.fxml", null);
        }
        catch(IOException ioe){
            System.out.println(ioe);
            showWarning("Fout", "Het scherm kon niet worden geladen.");
        }
    }

    public void onSaveUserClick(ActionEvent actionEvent) {
        //Nog uitwerken
    }
}
