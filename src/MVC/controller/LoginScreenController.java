package MVC.controller;

import DAO.POJO.User;
import PresentationState.Customer.JavaFxApplications;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Locale;
import java.util.ResourceBundle;

import static utils.Database.QueryUtils.createQuery;
import static utils.Database.QueryUtils.getResult;

public class LoginScreenController {
    Stage stage;
    Parent scene;
    @FXML
    private PasswordField passwordTxt;
    @FXML
    private TextField userNameTxt;

    public void goToMainScreen(ActionEvent actionEvent) throws IOException {
        Stage stage;
        Parent scene;
        // return to main menu
        // build stage
        stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();

        // load add part MVC.view
        scene = FXMLLoader.load(getClass().getResource("/MVC/view/overview.fxml"));

        // add scene to stage
        stage.setScene(new Scene(scene));

        //show stage
        stage.show();
    }

    // validate user input for login screen
    public static boolean validateInput(String selectRow, String fromTable, String whereCol, String isValue) throws SQLException, ClassNotFoundException {

        String query = "SELECT " + selectRow + " FROM " + fromTable;
        createQuery(query);
        try (ResultSet rs = getResult()) {
            while (rs.next()) {
                if (rs.getString(whereCol).equals(isValue)) {
                    return true;
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getErrorCode());
        }
        return false;
    }

    private static void alert(String alertType, String alertTitle, String message) {
        if(Locale.getDefault().getLanguage().equals("fr")) {
            ResourceBundle fr = JavaFxApplications.resources("resources/bundles/app_fr");
            message = fr.getString(alertTitle);
        }
        Alert alert = new Alert(Alert.AlertType.valueOf(alertType));
        alert.setTitle(alertTitle);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void onActionLogin(ActionEvent actionEvent) throws IOException, SQLException, ClassNotFoundException, ParseException {
        // validate email and password
        User credentials;
        String userName;
        String password;
        // check for values
        if(userNameTxt.getText().equals("") || passwordTxt.getText().equals("")
            || userNameTxt.getText() == null || passwordTxt.getText() == null) {
            /**
             * @link 'resources/bundles/app_fr'
             *
             * Title Key:Values
             * err_username_password_required=Nom d'utilisateur requis
             * err_password=Mot de passe requis
             * err_unauth=L'accès non autorisé
             * err_badCredentials=L'accès non autorisé
             * err_sessionExpired=Votre session a expiré
             * err_logoutError=Sorry, Error Logging Out
             * err_logoutSucc=Vous vous êtes déconnecté avec succès
             *
             **/
            alert("WARNING", "err_username_password_required", "Username and Password are required.");
        } else {
            System.out.println(userNameTxt.getText());
            System.out.println(passwordTxt.getText());
            credentials = new User(userNameTxt.getText(), passwordTxt.getText());
            userName = credentials.getUserName();
            password = credentials.getPassword();
            // validate userName
            boolean validUserName = validateInput("*", "user", "userName", userName);
            // validate password
            boolean validPassword = validateInput("*", "user", "password", password);

            // if userName is true and password is true then switch to main screen
            if (validUserName && validPassword) {
                // switch to main screen
                goToMainScreen(actionEvent);
            } else {
                alert("ERROR", "err_unauth", "Invalid Username or Password. Please try again.");
            }
        }
        // set userName and password


        //get username and password

        // test add
//        AppointmentDao.addAppointment(10, 1, 1, "testAddAppt", "test description", "location", "test", "newtest", "test.com", "NOW()", "NOW()", "test", "NOW()", "test");

        // test update
//        AppointmentDao.updateAppointmentWithString("title", "testUpdateAppt", 10);



        // else notify user invalid credentials
    }


    @FXML
    private void onActionExit(ActionEvent actionEvent) {
        System.exit(0);
    }

    @FXML
    public void initialize() {

    }
}
