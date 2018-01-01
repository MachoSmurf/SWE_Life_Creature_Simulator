package ViewPackage;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * FXML Controller class
 *
 * @author Natascha Zorg-Wijnhoven
 */
public class FXMLLoginController extends UIController implements Initializable {

    @FXML private TextField userName;
    @FXML private PasswordField password;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    public void onLoginClick(ActionEvent actionEvent) {
        String userName2 = userName.getText();
        String password2 = password.getText();

        if ((userName2.equals("")) || (password2.equals(""))){
            showWarning("Inlogfout", "Niet alle vereiste velden zijn ingevuld.");
        }
        else
        {
            boolean loginResult = user.Login(userName.getText(), password.getText());
            if (loginResult)
            {
                try{
                    FXMLHomepageController h = (FXMLHomepageController) changeScreen("/ViewPackage/FXMLHomepage.fxml", null);
                }
                catch(IOException ioe){
                    System.out.println(ioe);
                    showWarning("Fout", "Het scherm kon niet worden geladen.");
                }
            }
            else
            {
                showWarning("Inlogfout", "De inloggegevens zijn niet juist. Probeer het opnieuw.");
            }
        }
    }
}
