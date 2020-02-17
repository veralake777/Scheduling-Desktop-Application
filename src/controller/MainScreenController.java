package controller;

import DAO.AppointmentDao;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import model.Appointment;

public class MainScreenController {

    @FXML
    private Label userId;
    @FXML
    private Label userName;

    public MainScreenController() throws Exception {
    }

    @FXML
    public void initialize() throws Exception {
        Appointment testappt = AppointmentDao.getAppointment(1);
        userId.setText(String.valueOf(testappt.getCustomerId()));
        userName.setText(String.valueOf(testappt.getUrl()));
    }
}
