package ViewPackage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import UserPackage.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

/**
 * FXML Controller class responsible for all functionalities behind the Login-screen
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
            boolean loginResult = iUser1.Login(userName2, password2);

            if (loginResult == true)
            {
                if (iUser1.getRights() == true)
                {
                    try{
                        FXMLHomepageController h1 = (FXMLHomepageController) changeScreen("/ViewPackage/FXMLHomepage.fxml", null);
                    }
                    catch(IOException ioe) {
                        System.out.println(ioe);
                        showWarning("Fout", "Het scherm kon niet worden geladen.");
                    }
                }
                else
                {
                    try{
                        FXMLHomepage2Controller h2 = (FXMLHomepage2Controller) changeScreen("/ViewPackage/FXMLHomepage2.fxml", null);
                    }
                    catch(IOException ioe) {
                        System.out.println(ioe);
                        showWarning("Fout", "Het scherm kon niet worden geladen.");
                    }
                }
            }
            else
            {
                showWarning("Inlogfout", "De inloggegevens zijn niet juist. Probeer het opnieuw.");
            }
        }
    }
}
