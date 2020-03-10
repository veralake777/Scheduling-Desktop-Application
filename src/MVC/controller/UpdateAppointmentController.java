package MVC.controller;

import DAO.POJO.Appointment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ResourceBundle;

public class UpdateAppointmentController implements Initializable {
    public Button updateButton;
    private ObservableList<Appendable> allAppointments = FXCollections.observableArrayList();
    private Appointment appointment;
    private VBox layout;
    Stage stage;
    Scene scene;

    public void openUpdateAppointment(MouseEvent actionEvent, Appointment a) throws IOException, ParseException, SQLException, ClassNotFoundException {
        System.out.println("OPEN");
        System.out.println(appointment);
        setAppointment(a);
        Stage stage;
        Parent scene;
        // return to main menu
        // build stage
        stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage = (Stage) ((VBox) actionEvent.getSource()).getScene().getWindow();

        // load add part MVC.view
        scene = FXMLLoader.load(getClass().getResource("/MVC/view/updateAppointment.fxml"));

        // add scene to stage
        stage.setScene(new Scene(scene));

        //show stage
        stage.show();
    }

    public void setAppointment(Appointment appointment) {
        System.out.println("SET APPT");
        this.appointment = appointment;
        System.out.println(appointment);
        getAppointment();
    }

    //    public void receiveAppointment(Appointment a) throws ParseException, SQLException, ClassNotFoundException {
//        appointment = a;
//
//          idTxt.setText(String.valueOf(a.getAppointmentId()));
//        customerTxt.setText(String.valueOf(appointment.getCustomerId()));
//        userIdTxt.setText(String.valueOf(appointment.getUserId()));
//        descriptionTxt.setText(String.valueOf(appointment.getDescription()));
//        locationTxt.setText(String.valueOf(appointment.getLocation()));
//        contactTxt.setText(String.valueOf(appointment.getContact()));
//        typeTxt.setText(String.valueOf(appointment.getType()));
//        urlTxt.setText(String.valueOf(appointment.getUrl()));
//        startDateTxt.setText(String.valueOf(appointment.getStart().getTime()));
//        endDateTxt.setText(String.valueOf(appointment.getEnd().getTime()));
//    }
//
//    public Scene getScene() {
//        return scene;
//    }
//    private void setScene(Scene scene){
//        this.scene = scene;
//    }
//
    public Appointment getAppointment() {
        System.out.println(this.idTxt.getCharacters());
        return appointment;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        idTxt.setText(this.getAppointment().getContact());
    }


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
    public TextField startDateTxt; //get date from selected cell

    public void setUserIdTxt(TextField userIdTxt) {
        this.userIdTxt = userIdTxt;
    }

    public void setCustomerTxt(TextField customerTxt) {
        this.customerTxt = customerTxt;
    }

    public void setTitleTxt(TextField titleTxt) {
        this.titleTxt = titleTxt;
    }

    public void setDescriptionTxt(TextField descriptionTxt) {
        this.descriptionTxt = descriptionTxt;
    }

    public void setLocationTxt(TextField locationTxt) {
        this.locationTxt = locationTxt;
    }

    public void setContactTxt(TextField contactTxt) {
        this.contactTxt = contactTxt;
    }

    public void setTypeTxt(TextField typeTxt) {
        this.typeTxt = typeTxt;
    }

    public void setUrlTxt(TextField urlTxt) {
        this.urlTxt = urlTxt;
    }

    public void setStartDateTxt(TextField startDateTxt) {
        this.startDateTxt = startDateTxt;
    }

    public void setEndDateTxt(TextField endDateTxt) {
        this.endDateTxt = endDateTxt;
    }

    @FXML
    public TextField endDateTxt;


    public void onActionUpdate(ActionEvent actionEvent) {

    }

    public void onActionExit(ActionEvent actionEvent) {
        stage.close();
    }
}