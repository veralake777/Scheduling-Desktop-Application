package MVC.view;

import DAO.AppointmentDao;
import DAO.POJO.Appointment;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.Calendar;

public class AppointmentVBox extends VBox {
    VBox vbox;
    int vboxID;
    Separator horSep;
    Label titleLbl;
    Label contactLbl;
    Appointment appointment;
    Text contactTxt;
    Text typeTxt;
    int startHour;
    int startMinutes;
    Label startTimeLbl;
    int endHour;
    int endMinutes;
    int meridian;

    public AppointmentVBox(VBox vbox, int vboxID, Separator horSep, Label titleLbl, Label contactLbl, Appointment appointment, Text contactTxt, Text typeTxt, int startHour, int startMinutes, Label startTimeLbl, int endHour, int endMinutes, int meridian) {
        this.vbox = vbox;
        this.vboxID = vboxID;
        this.horSep = horSep;
        this.titleLbl = titleLbl;
        this.contactLbl = contactLbl;
        this.appointment = appointment;
        this.contactTxt = contactTxt;
        this.typeTxt = typeTxt;
        this.startHour = startHour;
        this.startMinutes = startMinutes;
        this.startTimeLbl = startTimeLbl;
        this.endHour = endHour;
        this.endMinutes = endMinutes;
        this.meridian = meridian;
    }

    public AppointmentVBox() {
    }

    public VBox getAppointmentById(int id) throws ParseException, SQLException, ClassNotFoundException {
        // appointment data
        Appointment appointment = AppointmentDao.getAppointment(id);
        if(appointment != null) {
            // Vbox
//        vbox = new VBox();
            // separator
            horSep = new Separator();
            horSep.setHalignment(HPos.CENTER);
            horSep.setPadding(new Insets(20, 10, 20, 10));

            // title
            titleLbl = new Label("Next Appointment");
            titleLbl.setFont(Font.font("Veranda", FontWeight.EXTRA_BOLD, FontPosture.REGULAR, 25));

            // contact
            assert appointment != null;
            contactTxt = new Text(appointment.getContact());
            typeTxt = new Text(appointment.getType());

            // time
            startHour = appointment.getStart().get(Calendar.HOUR_OF_DAY);
            startMinutes = appointment.getStart().get(Calendar.MINUTE);
            int startAM_PM = appointment.getStart().get(Calendar.AM_PM);
            String appointmentStartTime = startHour + ":" + startMinutes + startAM_PM;

            endHour = appointment.getEnd().get(Calendar.HOUR_OF_DAY);
            endMinutes = appointment.getEnd().get(Calendar.MINUTE);
            int endAM_PM = appointment.getEnd().get(Calendar.AM_PM);
            String appointmentEndTime = endHour + ":" + endMinutes + endAM_PM;

            // Labels

            titleLbl = new Label(typeTxt.getText());
            titleLbl.setFont(Font.font("Veranda", FontWeight.BOLD, FontPosture.REGULAR, 22));

            contactLbl = new Label(contactTxt.getText());
            contactLbl.setFont(Font.font("Veranda", FontWeight.BOLD, FontPosture.REGULAR, 18));

            startTimeLbl = new Label(appointmentStartTime);


            this.getChildren().addAll(horSep, titleLbl, contactLbl, startTimeLbl);
        } else {
            this.getChildren().add(new Label("NULL"));
        }

        return this;
    }

    public VBox getAppointmentByDate(int dayOfMonth, int startHour) throws ParseException, SQLException, ClassNotFoundException {
        // get all appointments
        ObservableList<Appointment> allAppointments = new AppointmentDao().getAllAppointments();
        for(Appointment a : allAppointments) {
            if(a.getStart().get(Calendar.HOUR_OF_DAY) == startHour && a.getStart().get(Calendar.DAY_OF_MONTH) == dayOfMonth) {
                vboxID = a.getAppointmentId();
                titleLbl = new Label(a.getType());
                contactLbl = new Label(a.getContact());
                appointment = a;
                startHour = a.getStart().get(Calendar.HOUR_OF_DAY);
                startMinutes = a.getStart().get(Calendar.MINUTE);
                startTimeLbl = new Label(startHour + ":" + startMinutes);
                endHour = a.getEnd().get(Calendar.HOUR_OF_DAY);
                endMinutes = a.getEnd().get(Calendar.MINUTE);
                meridian = a.getStart().get(Calendar.AM_PM);
                vbox = new VBox();
                vbox.getChildren().addAll(titleLbl, contactLbl, startTimeLbl);
            } else {
                vbox = new VBox();
                vbox.getChildren().add(new Label("CANNOT FIND MATCH"));
            }
        }

        return vbox;
    }


    public VBox getAppointmentBox() throws ParseException, SQLException, ClassNotFoundException {
        VBox apptVBox = new VBox();
        // VBox Title
        Separator horSep = new Separator();
        horSep.setHalignment(HPos.CENTER);
        horSep.setPadding(new Insets(20, 10, 20, 10 ));
        Label vboxTitle = new Label("Next Appointment");
        vboxTitle.setFont(Font.font("Veranda", FontWeight.EXTRA_BOLD, FontPosture.REGULAR, 25));
        // appointment data
        Appointment appointment = AppointmentDao.getAppointment(1);

        // contact
        Text apptContact = new Text( appointment.getContact());
        Text apptType = new Text(appointment.getType());


        // time
        int appointmentStartHour = appointment.getStart().get(Calendar.HOUR_OF_DAY);
        int appointmentStartMinutes = appointment.getStart().get(Calendar.MINUTE);
        int appointmentAM_PM = appointment.getStart().get(Calendar.AM_PM);
        String appointmentStartTime = appointmentStartHour + ":" + appointmentStartMinutes + appointmentAM_PM;

        // Labels

        Label title = new Label(apptType.getText());
        title.setFont(Font.font("Veranda", FontWeight.BOLD, FontPosture.REGULAR, 22));

        Label contact = new Label(apptContact.getText());
        contact.setFont(Font.font("Veranda", FontWeight.BOLD, FontPosture.REGULAR, 18));

        Label startTime = new Label(appointmentStartTime);


        apptVBox.getChildren().addAll(horSep, vboxTitle, title, contact, startTime);

        return apptVBox;
    }


}
