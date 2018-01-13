package ViewPackage;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import UserPackage.User;

/**
 * FXML Controller class responsible for all functionalities behind the UserManagement-screen
 *
 * @author Natascha Zorg-Wijnhoven
 */
public class FXMLUserManagementController extends UIController implements Initializable {

    @FXML private TextField userName;
    @FXML private PasswordField password;
    @FXML private PasswordField passwordRepeat;
    @FXML private RadioButton simUserYes;
    @FXML private ListView userOverview;
    private List<User> users;
    private ObservableList userList;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //userList = FXCollections.observableArrayList();
        //users = iUser1.getUsers();
        //if (!users.isEmpty()) {
            //users.forEach((user) -> {
                //userList.add(user.getUsername());
        //});
            //userOverview.setItems(userList);
        //}
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
        String userName2 = userName.getText();
        String password2 = password.getText();
        String passwordRepeat2 = passwordRepeat.getText();
        Boolean simUser = true;

        if ((userName2.equals("")) || (password2.equals("")) || (passwordRepeat2.equals(""))){
            showWarning("Inlogfout", "Niet alle vereiste velden zijn ingevuld.");
        }
        else
        {
            if (!simUserYes.isSelected())
            {
                simUser = false;
            }

            password2 = password2.trim();
            passwordRepeat2 = passwordRepeat2.trim();

            if (!password2.equals(passwordRepeat2))
            {
                showWarning("Fout", "Wachtwoorden zijn niet aan elkaar gelijk. Voer a.u.b. 2 gelijke wachtwoorden in.");
            }
            else
            {
                //Er moet iets aan de kant van Imke worden aangepast, zodat ik de methode saveUser van IDataMediator via IUserController kan gebruiken

                userOverview.getItems().clear();
                users.clear();
                userList.clear();

                userList = FXCollections.observableArrayList();
                users = iUser1.getUsers();
                if (!users.isEmpty()) {
                    users.forEach((user) -> {
                        userList.add(user.getUsername());
                });
                    userOverview.setItems(userList);
                }
            }
        }
    }
}
