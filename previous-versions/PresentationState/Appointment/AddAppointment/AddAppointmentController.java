package PresentationState.Appointment.AddAppointment;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class AddAppointmentController implements Initializable {
    // buttons
    @FXML
    public Button addButton;
    @FXML
    public Button cancelButton;

    // user input
    @FXML
    public TextField idTxt; //disabled
    @FXML
    public TextField userIdTxt; //disabled
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

    public ComboBox<String> customerComboBox;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}