package Components.Appointments;

import Components.ComboBoxes;
import Components.Main;
import DbDao.DbAppointmentDao;
import DbDao.DbCustomerDao;
import DbDao.DbUserDao;
import POJO.Appointment;
import POJO.Customer;
import POJO.User;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import utils.DBUtils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class AppointmentCard {
    // Appointment Id
    Appointment appointment;
    // Customer Name **
    private Label customerNameLbl = new Label("Customer Name");
    private ComboBox<Customer> customerNameTxt;

    private Stage stage;

    {
        try {
            customerNameTxt = new ComboBoxes().getCustomers();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // User
    private User user;

    // Title
    private Label titleLbl = new Label("Title");
    private TextField titleTxt = new TextField();

    // Description
    private Label descriptionLbl = new Label("Description");
    private TextField descriptionTxt = new TextField();

    // Location
    private Label locationLbl = new Label("Location");
    private TextField locationTxt = new TextField();

    // Contact **
    private Label contactLbl = new Label("Contact");
    private TextField contactTxt = new TextField();

    // Type **
    private Label typeLbl = new Label("Type");
    private TextField typeTxt = new TextField();

    // URL
    private Label urlLbl = new Label("URL");
    private TextField urlTxt = new TextField();

    // Date picker - appointments are only available for up to 1 hour so only one datepicker is needed
    private Label datePickerLbl = new Label("Date");
    final DatePicker datePicker = new DatePicker();

    // Start Time
    private Label startLbl = new Label("Start");
    private ComboBox<LocalTime> startTxt;

    {
        try {
            startTxt = new ComboBoxes().getAppointmentTimes();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    // Duration (end time)
    private Label endLbl = new Label("Duration");
    private ComboBox<Integer> endTxt = new ComboBoxes().getDurationTimes();

    // FOR NEW APPOINTMENTS
    public AppointmentCard(Main main) {
        this.stage = new Stage();
        user = main.getUser();
    }

    /**
     * @param appointmentId passed in from Calendar.CalendarView, Calendar.Month, Calendar.Week
     * @throws Exception
     *
     * Use with edit appointment buttons where you pass in a selected appointment
     */
    public AppointmentCard(int appointmentId) throws Exception {
        this.stage = new Stage();
        // access appointment in database - must use appointment id because the AppointmentsTable class uses a
        // special LocalAppointment class for building the table that includes customer names
        Appointment appointment = new DbAppointmentDao(DBUtils.getMySQLDataSource()).getById(appointmentId).get();
        // set appointment
        this.appointment = appointment;

        // get customer
        Optional<Customer> customer = new DbCustomerDao(DBUtils.getMySQLDataSource()).getById(appointment.getCustomerId());

        assert customerNameTxt != null;
        customerNameTxt.getSelectionModel().select(customer.get());
        user = new DbUserDao(DBUtils.getMySQLDataSource()).getById(appointment.getUserId()).get();
        titleTxt.setText(appointment.getTitle());
        descriptionTxt.setText(appointment.getDescription());
        locationTxt.setText(appointment.getLocation());
        contactTxt.setText(appointment.getContact());
        typeTxt.setText(appointment.getType());
        urlTxt.setText(appointment.getUrl());

        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");
        assert startTxt != null;
        startTxt.getSelectionModel().select(LocalTime.parse(sdf.format(appointment.getStart().getTime())));
        sdf = new SimpleDateFormat("mm");
        endTxt.getSelectionModel().select(Integer.valueOf(sdf.format(appointment.getEnd().getTime())));
    }

    private void setAppointment(Appointment appointment) {
        // set all appointment values to input values
        appointment.setCustomerId(customerNameTxt.getSelectionModel().getSelectedItem().getId());
        appointment.setTitle(titleTxt.getText());
        appointment.setDescription(descriptionTxt.getText());
        appointment.setLocation(locationTxt.getText());
        appointment.setContact(contactTxt.getText());
        appointment.setType(typeTxt.getText());
        appointment.setUrl(urlTxt.getText());

        // format times
        // Date formatter for date picker and time combo boxes
        final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("hh:mm:ss");
        LocalTime startTime = startTxt.getSelectionModel().getSelectedItem();
        LocalTime endTime = startTime.plusMinutes(endTxt.getSelectionModel().getSelectedItem());

        appointment.setStart(Timestamp.valueOf(dateFormatter.format(datePicker.getValue()) + " " + timeFormatter.format(startTime)));
        appointment.setEnd(Timestamp.valueOf(dateFormatter.format(datePicker.getValue()) + " " + timeFormatter.format(endTime)));
    }


    // NEW APPOINTMENT
    private ButtonBar newAppointmentButtonBar() {
        ButtonBar buttonBar = new ButtonBar();
        Button okBtn = new Button("OK");
        Button cancelBtn = new Button("Cancel");
        // init button bar
        buttonBar.setButtonMinWidth(100);
        ButtonBar.setButtonData(okBtn, ButtonBar.ButtonData.OK_DONE);
        ButtonBar.setButtonData(cancelBtn, ButtonBar.ButtonData.CANCEL_CLOSE);
        buttonBar.getButtons().addAll(okBtn, cancelBtn);

        okBtn.setOnAction(e->{
            // Date formatter for date picker and time combo boxes
            final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("hh:mm:ss");
            LocalTime startTime = startTxt.getSelectionModel().getSelectedItem();
            LocalTime endTime = startTime.plusMinutes(endTxt.getSelectionModel().getSelectedItem());
            // DAO.add()
            DbAppointmentDao dao = null;
            try {
                dao = new DbAppointmentDao(DBUtils.getMySQLDataSource());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            try {
                assert dao != null;
                dao .add(new Appointment(
                        dao.getMaxId() + 1,
                        customerNameTxt.getValue().getId(),
                        user.getId(),
                        titleTxt.getText(),
                        descriptionTxt.getText(),
                        locationTxt.getText(),
                        contactTxt.getText(),
                        typeTxt.getText(),
                        urlTxt.getText(),
                        Timestamp.valueOf(dateFormatter.format(datePicker.getValue()) + " " + timeFormatter.format(startTime)),
                        Timestamp.valueOf(dateFormatter.format(datePicker.getValue()) + " " + timeFormatter.format(endTime)),
                        Timestamp.valueOf(LocalDateTime.now()),
                        "default",
                        Timestamp.valueOf(LocalDateTime.now()),
                        "default"
                ));
                stage.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        cancelBtn.setOnAction(e-> stage.close());

        return buttonBar;
    }

    private GridPane newAppointmentGridPane() {
        // style gridPane
        GridPane gridPane = new GridPane();
        gridPane.setHgap(20);
        gridPane.setVgap(15);
        gridPane.setAlignment(Pos.CENTER);

        // add nodes
        gridPane.add(customerNameLbl, 0, 0);
        gridPane.add(customerNameTxt, 1, 0);
        gridPane.add(titleLbl, 0, 1);
        gridPane.add(titleTxt, 1, 1);
        gridPane.add(descriptionLbl, 0, 2);
        gridPane.add(descriptionTxt, 1, 2);
        gridPane.add(locationLbl, 0, 3);
        gridPane.add(locationTxt, 1, 3);
        gridPane.add(contactLbl, 0, 4);
        gridPane.add(contactTxt, 1, 4);
        gridPane.add(typeLbl, 0, 5);
        gridPane.add(typeTxt, 1, 5);
        gridPane.add(urlLbl, 0, 6);
        gridPane.add(urlTxt, 1, 6);
        gridPane.add(datePickerLbl,0,7);
        gridPane.add(datePicker, 1, 7);
        gridPane.add(startLbl, 0, 8);
        gridPane.add(startTxt, 1, 8);
        gridPane.add(endLbl, 0, 9);
        gridPane.add(endTxt, 1, 9);
        gridPane.add(newAppointmentButtonBar(), 1, 10);

        return gridPane;
    }
    // Overloaded function that includes start and duration of the appointment - used to set values in the GridPane
    private void newAppointmentGridPane(LocalDateTime start, Duration duration) {
        // style gridPane
        GridPane gridPane = new GridPane();
        gridPane.setHgap(20);
        gridPane.setVgap(15);
        gridPane.setAlignment(Pos.CENTER);

        // add nodes
        gridPane.add(customerNameLbl, 0, 0);
        gridPane.add(customerNameTxt, 1, 0);
        gridPane.add(titleLbl, 0, 1);
        gridPane.add(titleTxt, 1, 1);
        gridPane.add(descriptionLbl, 0, 2);
        gridPane.add(descriptionTxt, 1, 2);
        gridPane.add(locationLbl, 0, 3);
        gridPane.add(locationTxt, 1, 3);
        gridPane.add(contactLbl, 0, 4);
        gridPane.add(contactTxt, 1, 4);
        gridPane.add(typeLbl, 0, 5);
        gridPane.add(typeTxt, 1, 5);
        gridPane.add(urlLbl, 0, 6);
        gridPane.add(urlTxt, 1, 6);
        gridPane.add(datePickerLbl,0,7);
        datePicker.setValue(start.toLocalDate());
        gridPane.add(datePicker, 1, 7);
        gridPane.add(startLbl, 0, 8);
        startTxt.getSelectionModel().select(LocalTime.from(start));
        gridPane.add(startTxt, 1, 8);
        gridPane.add(endLbl, 0, 9);
        endTxt.setValue((int) duration.toMinutes());
        gridPane.add(endTxt, 1, 9);
        gridPane.add(newAppointmentButtonBar(), 1, 10);


        this.stage.setScene(new Scene(gridPane));
    }

    // EDIT APPOINTMENT
    private ButtonBar editAppointmentButtonBar() {
        ButtonBar buttonBar = new ButtonBar();
        Button okBtn = new Button("OK");
        Button cancelBtn = new Button("Cancel");
        // init button bar
        ButtonBar.setButtonData(okBtn, ButtonBar.ButtonData.OK_DONE);
        ButtonBar.setButtonData(cancelBtn, ButtonBar.ButtonData.CANCEL_CLOSE);
        buttonBar.getButtons().addAll(okBtn, cancelBtn);

        okBtn.setOnAction(e->{
            // DAO.add()
            DbAppointmentDao dao = null;
            try {
                dao = new DbAppointmentDao(DBUtils.getMySQLDataSource());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            try {
                assert dao != null;
                setAppointment(appointment);
                dao.update(appointment);
                stage.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        cancelBtn.setOnAction(e-> stage.close());

        return buttonBar;
    }
    // Build Edit Appointment Stage
    private GridPane editAppointmentGridPane() {
        // style gridPane
        GridPane gridPane = new GridPane();
        gridPane.setHgap(20);
        gridPane.setVgap(15);
        gridPane.setAlignment(Pos.CENTER);


        /**
         * @constructor setText() for Txt fields are set in the constructor
         * @appointment is passed in from the Components.Appointments.AppointmentsTable class
         * add nodes to gridPane
         */
        gridPane.add(customerNameLbl, 0, 0);
        gridPane.add(customerNameTxt, 1, 0);
        gridPane.add(titleLbl, 0, 1);
        gridPane.add(titleTxt, 1, 1);
        gridPane.add(descriptionLbl, 0, 2);
        gridPane.add(descriptionTxt, 1, 2);
        gridPane.add(locationLbl, 0, 3);
        gridPane.add(locationTxt, 1, 3);
        gridPane.add(contactLbl, 0, 4);
        gridPane.add(contactTxt, 1, 4);
        gridPane.add(typeLbl, 0, 5);
        gridPane.add(typeTxt, 1, 5);
        gridPane.add(urlLbl, 0, 6);
        gridPane.add(urlTxt, 1, 6);
        gridPane.add(datePickerLbl, 0, 7);
        gridPane.add(datePicker, 1, 7);
        gridPane.add(startLbl, 0, 8);
        gridPane.add(startTxt, 1, 8);
        gridPane.add(endLbl, 0, 9);
        gridPane.add(endTxt, 1, 9);
        gridPane.add(editAppointmentButtonBar(), 1, 10);

        return gridPane;
    }

    public Stage getNewAppointmentStage() {
        stage.setScene(new Scene(newAppointmentGridPane()));
        stage.setResizable(false);
        stage.setMinWidth(500);
        stage.setMinHeight(500);

        stage.setTitle("New Appointment");
        return stage;
    }

    public Stage getEditAppointmentStage() {
        stage.setScene(new Scene(editAppointmentGridPane()));
        stage.setResizable(false);
        stage.setMinWidth(500);
        stage.setMinHeight(500);

        stage.setTitle("Edit Appointment");
        return stage;
    }

    public Stage getNewAppointmentStage(LocalDateTime start, Duration duration) {
        newAppointmentGridPane(start, duration);
        stage.setResizable(false);
        stage.setMinWidth(500);
        stage.setMinHeight(500);

        stage.setTitle("New Appointment");
        return stage;
    }

    public GridPane getNewAppointmentGridPane() {
        return newAppointmentGridPane();
    }
    public GridPane getEditAppointmentGridPane() { return editAppointmentGridPane();}
}
