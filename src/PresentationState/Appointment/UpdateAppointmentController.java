package PresentationState.Appointment;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class UpdateAppointmentController implements Initializable {
    // buttons
    @FXML
    public Button updateButton;
    @FXML
    public Button cancelButton;



    // user input
    @FXML
    public TextField idTxt; //disabled
    @FXML
    public TextField userIdTxt; //disabled
    @FXML
    public TextField customerTxt;//get customer name by Id
    @FXML
    public TextField titleTxt;
    @FXML
    public TextField descriptionTxt;
    @FXML
    public TextField locationTxt;
    @FXML
    public TextField contactTxt;
    @FXML
    public TextField typeTxt;
    @FXML
    public TextField urlTxt;
    @FXML
    public DatePicker startDatePicker; //get date from selected cell
    @FXML
    public TextField startTimeTxt;
    @FXML
    public DatePicker endDatePicker;
    @FXML
    public TextField endTimeTxt;


    public void onActionUpdate(ActionEvent actionEvent) {
        // See UpdateAppointmentActionHandlers

        // It was necessary to extract this away from the controller because I could not access the DAO data and the
        // FXML data within the same class.
    }

    public void onActionExit(ActionEvent actionEvent) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}