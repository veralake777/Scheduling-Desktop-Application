package MVC.view;

import DAO.AppointmentDao;
import DAO.POJO.Appointment;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.VBox;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.Calendar;

public class AppointmentVBox {
    public VBox getAppointmentBox() throws ParseException, SQLException, ClassNotFoundException {
        VBox appointmentPane = new VBox();
        // VBox Title
        Separator horSep = new Separator();
        horSep.setHalignment(HPos.CENTER);
        horSep.setPadding(new Insets(20, 10, 20, 10 ));
        Label vboxTitle = new Label("Next Appointment");
        // appointment data
        Appointment appointment = AppointmentDao.getAppointment(1);
        String appointmentContact = appointment.getContact();
        String appointmentTitle = appointment.getTitle();

        // time
        int appointmentStartHour = appointment.getStart().get(Calendar.HOUR_OF_DAY);
        int appointmentStartMinutes = appointment.getStart().get(Calendar.MINUTE);
        int appointmentAM_PM = appointment.getStart().get(Calendar.AM_PM);
        String appointmentStartTime = appointmentStartHour + ":" + appointmentStartMinutes + appointmentAM_PM;

        // Labels
        Label contact = new Label(appointmentContact);
        Label title = new Label(appointmentTitle);
        Label startTime = new Label(appointmentStartTime);


        appointmentPane.getChildren().addAll(horSep, vboxTitle, contact, title, startTime);

        return appointmentPane;
    }
}
