package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginScreenController {
    Stage stage;
    Parent scene;
    @FXML
    private PasswordField passwordTxt;
    @FXML
    private TextField emailTxt;

    public void goToMainScreen(ActionEvent actionEvent) throws IOException {
        Stage stage;
        Parent scene;
        // return to main menu
        // build stage
        stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();

        // load add part view
        stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();

        // load add part view
        scene = FXMLLoader.load(getClass().getResource("/view/mainScreen.fxml"));

        // add scene to stage
        stage.setScene(new Scene(scene));

        //show stage
        stage.show();
    }
    @FXML
    private void onActionLogin(ActionEvent actionEvent) throws IOException {
        // validate email and password

        // switch to main screen
        goToMainScreen(actionEvent);
    }

    @FXML
    private void onActionExit(ActionEvent actionEvent) {
        System.exit(0);
    }


//    Font.loadFont(getClass().getResourceAsStream([fontpath...]), [Some font-size, for example 12]);
}
