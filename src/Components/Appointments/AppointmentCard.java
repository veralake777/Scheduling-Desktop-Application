package Components.Appointments;

import Components.Calendar.Week;
import Components.ComboBoxes;
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
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Stream;

public class AppointmentCard {
    // Calendar.Week
    private Week weekView = null;
    // Time slot
    private Week.TimeSlot timeSlot;
    // Appointment Table (parent)
    private AppointmentsTable appointmentsTable = null;
    // Appointment Id
    private Appointment appointment;
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
    public User user;

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
    private ComboBox<Integer> durationComboBox = new ComboBoxes().getDurationTimes();

    // For popups in Components.Calendar.Week, Components.Calendar.Month
    public AppointmentCard(User user) {
        this.stage = new Stage();
        this.user = user;
        System.out.println("constructor1: " + user.getId());
    }

    // for grid pane in dynamic views of Components.Appointments.AppointmentsTable, Components.Customer.CustomersTable
    public AppointmentCard(AppointmentsTable appointmentsTable, User user) {
        this.appointmentsTable = appointmentsTable;
        this.stage = new Stage();
        this.user = user;
        System.out.println("constructor2: " + user.getId());
    }

    // for week view, onDragRelease --> new appointment popup
    public AppointmentCard(User user, Week weekView, Week.TimeSlot timeSlot) {
        this.stage = new Stage();
        this.user = user;
        this.datePicker.setValue(timeSlot.getStart().toLocalDate());
        this.weekView = weekView;
        this.timeSlot = timeSlot;
        assert this.startTxt != null;
        this.startTxt.getSelectionModel().select(LocalTime.parse(timeSlot.getTime().format(DateTimeFormatter.ofPattern("hh:mm"))));
        this.durationComboBox.getSelectionModel().select(Integer.valueOf((int) timeSlot.getDuration().toMinutes()));
        System.out.println("constructor3: " + user.getId());
    }

    /**
     * @param appointmentId passed in from Calendar.CalendarView, Calendar.Month, Calendar.Week
     * @throws Exception Use with edit appointment buttons where you pass in a selected appointment
     */
    public AppointmentCard(int appointmentId) throws Exception {
        System.out.println("constructor5: " + user.getId());
        this.stage = new Stage();
        // access appointment in database - must use appointment id because the AppointmentsTable class uses a
        // special LocalAppointment class for building the table that includes customer names
        Optional<Appointment> appointment = new DbAppointmentDao(DBUtils.getMySQLDataSource()).getById(appointmentId);
        // set appointment
        if (appointment.isPresent()) {
            this.appointment = appointment.get();

            // get customer
            Optional<Customer> customer = new DbCustomerDao(DBUtils.getMySQLDataSource()).getById(this.appointment.getCustomerId());

            assert customerNameTxt != null;
            customerNameTxt.getSelectionModel().select(customer.get());
            titleTxt.setText(this.appointment.getTitle());
            descriptionTxt.setText(this.appointment.getDescription());
            locationTxt.setText(this.appointment.getLocation());
            contactTxt.setText(this.appointment.getContact());
            typeTxt.setText(this.appointment.getType());
            urlTxt.setText(this.appointment.getUrl());

            SimpleDateFormat sdf = new SimpleDateFormat("hh:mm");
            assert startTxt != null;
            startTxt.getSelectionModel().select(LocalTime.parse(sdf.format(this.appointment.getStart().getTime())));
            sdf = new SimpleDateFormat("mm");
            durationComboBox.getSelectionModel().select(Integer.valueOf(sdf.format(this.appointment.getEnd().getTime() - this.appointment.getStart().getTime())));
        } else {
            System.out.println("Appointment not found.");
        }
    }

    /**
     * @param appointmentId     passed in from Components.AppointmentsTable
     * @param appointmentsTable for access to updateRightSide() in Components.AppointmentsTable
     * @throws Exception Use with edit appointment buttons where you pass in a selected appointment
     */
    public AppointmentCard(int appointmentId, AppointmentsTable appointmentsTable) throws Exception {
        System.out.println("constructor6");
        this.appointmentsTable = appointmentsTable;
        this.stage = new Stage();
        // access appointment in database - must use appointment id because the AppointmentsTable class uses a
        // special LocalAppointment class for building the table that includes customer names
        Optional<Appointment> appointment = new DbAppointmentDao(DBUtils.getMySQLDataSource()).getById(appointmentId);
        // set appointment
        if (appointment.isPresent()) {
            this.appointment = appointment.get();

            // get customer
            Optional<Customer> customer = new DbCustomerDao(DBUtils.getMySQLDataSource()).getById(this.appointment.getCustomerId());

            assert customerNameTxt != null;
            customerNameTxt.getSelectionModel().select(customer.get());
            User user = new DbUserDao(DBUtils.getMySQLDataSource()).getById(this.appointment.getUserId()).get();
            this.user = user;

            // set text fields with data
            titleTxt.setText(this.appointment.getTitle());
            descriptionTxt.setText(this.appointment.getDescription());
            locationTxt.setText(this.appointment.getLocation());
            contactTxt.setText(this.appointment.getContact());
            typeTxt.setText(this.appointment.getType());
            urlTxt.setText(this.appointment.getUrl());
            datePicker.setValue(LocalDate.from(appointment.get().getStart().toLocalDateTime()));

            assert startTxt != null;
            startTxt.getSelectionModel().select(LocalTime.parse(this.appointment.getStart().toLocalDateTime().toLocalTime().format(DateTimeFormatter.ofPattern("hh:mm"))));
            durationComboBox.getSelectionModel().select(Integer.valueOf(this.appointment.getEnd().toLocalDateTime().toLocalTime().format(DateTimeFormatter.ofPattern("mm"))));
        } else {
            System.out.println("Appointment not found.");
        }
    }

    public AppointmentCard(int appointmentId, LocalDate date, Week weekView) throws Exception {
        System.out.println("constructor7: " + appointmentId);
        this.stage = new Stage();
        this.weekView = weekView;
        // access appointment in database - must use appointment id because the AppointmentsTable class uses a
        // special LocalAppointment class for building the table that includes customer names
        Optional<Appointment> appointment = new DbAppointmentDao(DBUtils.getMySQLDataSource()).getById(appointmentId);
        // set appointment
        if (appointment.isPresent()) {
            this.appointment = appointment.get();

            // get customer
            Optional<Customer> customer = new DbCustomerDao(DBUtils.getMySQLDataSource()).getById(this.appointment.getCustomerId());

            assert customerNameTxt != null;
            customerNameTxt.getSelectionModel().select(customer.get());
            User user = new DbUserDao(DBUtils.getMySQLDataSource()).getById(this.appointment.getUserId()).get();
            this.user = user;

            // set text fields with data
            titleTxt.setText(this.appointment.getTitle());
            descriptionTxt.setText(this.appointment.getDescription());
            locationTxt.setText(this.appointment.getLocation());
            contactTxt.setText(this.appointment.getContact());
            typeTxt.setText(this.appointment.getType());
            urlTxt.setText(this.appointment.getUrl());
            this.datePicker.setValue(date);
            assert startTxt != null;
            startTxt.getSelectionModel().select(LocalTime.parse(this.appointment.getStart().toLocalDateTime().toLocalTime().format(DateTimeFormatter.ofPattern("hh:mm"))));
            durationComboBox.getSelectionModel().select(Integer.valueOf(this.appointment.getStart().toLocalDateTime().toLocalTime().format(DateTimeFormatter.ofPattern("mm"))));
        } else {
            System.out.println("Appointment not found.");
        }
    }


    public AppointmentCard(LocalDate date, User user) {
        System.out.println("constructor8: " + user.getId());
        this.user = user;
        this.stage = new Stage();
        this.datePicker.setValue(date);
    }

    private void setAppointment(Appointment appointment) {
        // set all appointment values to with text field values
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
        LocalTime endTime = startTime.plusMinutes(durationComboBox.getSelectionModel().getSelectedItem());

        // F.   Write exception controls to prevent setting appointments outside of business hours
        if (datePicker.getValue() != null
                && datePicker.getValue().getDayOfWeek() != DayOfWeek.SATURDAY
                && datePicker.getValue().getDayOfWeek() != DayOfWeek.SUNDAY
                && startTime != null
                && endTime != null) {
            appointment.setStart(Timestamp.valueOf(dateFormatter.format(datePicker.getValue()) + " " + timeFormatter.format(startTime)));
            appointment.setEnd(Timestamp.valueOf(dateFormatter.format(datePicker.getValue()) + " " + timeFormatter.format(endTime)));
        } else {
            // F.   Write exception controls to prevent setting appointments outside of business hours
            Alert a = new Alert(Alert.AlertType.INFORMATION, "You must select a date of M-F, start time, and end time.");
            a.showAndWait();
        }
    }


    // NEW APPOINTMENT
    private ButtonBar newAppointmentButtonBar() throws Exception {
        ButtonBar buttonBar = new ButtonBar();
        Button okBtn = new Button("OK");
        Button cancelBtn = new Button("Cancel");
        // init button bar
        buttonBar.setButtonMinWidth(100);
        ButtonBar.setButtonData(okBtn, ButtonBar.ButtonData.OK_DONE);
        ButtonBar.setButtonData(cancelBtn, ButtonBar.ButtonData.CANCEL_CLOSE);
        buttonBar.getButtons().addAll(okBtn, cancelBtn);

        // G.  Write two or more lambda expressions to make your program more efficient, justifying the use of each lambda expression with an in-line comment.
        // this lambda is more efficient than the procedural version because the "e" allows the name of the EventType<> to be omitted
        okBtn.setOnAction(e -> {
            // exception handling
            if (customerNameTxt.getSelectionModel().isEmpty() || typeTxt.getText().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "You must select a customer from the list and enter a type.");
                alert.showAndWait();
            } else {
                // Date formatter for date picker and time combo boxes
                final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("hh:mm:ss");
                LocalTime startTime = startTxt.getSelectionModel().getSelectedItem();
                LocalTime endTime = startTime.plusMinutes(durationComboBox.getSelectionModel().getSelectedItem());
                // DAO.add()
                DbAppointmentDao dao = null;
                try {
                    dao = new DbAppointmentDao(DBUtils.getMySQLDataSource());
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                try {
                    assert dao != null;
                    Appointment appointmentToAdd = new Appointment(
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
                            user.getUserName(),
                            Timestamp.valueOf(LocalDateTime.now()),
                            "default");

                    // RUBRIC F:  exception control for scheduling overlapping appointments
                    if (!isAppointment(appointmentToAdd)) {
                        dao.add(appointmentToAdd);
                        Alert a = new Alert(Alert.AlertType.INFORMATION, "Success!");
                        a.showAndWait();
                        if (appointmentsTable != null) {
                            appointmentsTable.appointments.add(
                                    new AppointmentsTable.LocalAppointment(
                                            appointmentToAdd.getId(),
                                            appointmentToAdd.getType(),
                                            new DbCustomerDao(DBUtils.getMySQLDataSource()).getById(appointmentToAdd.getCustomerId()).get().getCustomerName()));
                        } else {
                            // else you are using calendar view and need to update the week
                            weekView.setAppointments();
                            stage.close();
                        }
                    } else {
                        // RUBRIC F:  exception control for scheduling overlapping appointments
                        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Appointment unavailable. Please try again.");
                        alert.showAndWait();
                    }
                } catch (Exception ex) {
                    Alert a = new Alert(Alert.AlertType.ERROR, "Something went wrong.");
                    a.showAndWait();
                    ex.printStackTrace();
                }
            }
        });

        // G.  Write two or more lambda expressions to make your program more efficient, justifying the use of each lambda expression with an in-line comment.
        // this lambda is more efficient than the procedural version because the "e" allows the name of the EventType<> to be omitted
        cancelBtn.setOnAction(e -> {stage.close();
            if(appointmentsTable != null) {
                appointmentsTable.updateRightSideView(new Label("Press Edit, Delete, or New Appointment to load a view."));
            }

            try {
                if(timeSlot != null) {
                    Week.TimeSlot endSlot = new Week.TimeSlot(timeSlot.getStart(), timeSlot.getDuration());
                    // G.  Write two or more lambda expressions to make your program more efficient,
                    //     justifying the use of each lambda expression with an in-line comment.
                    // this lambda is more efficient than the procedural version because the "slot" allows
                    // the name of the verbose version of fetching the slot to be omitted
                    weekView.getTimeSlots().forEach(slot -> {
                        if(weekView.isBetween(slot, timeSlot, endSlot)) {
                            timeSlot.setStart(timeSlot.getStart().plusMinutes(15));
                            slot.setSelected(false);
                        }
                    });
                }
                weekView.getView(weekView.monday);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        return buttonBar;
    }

    private GridPane newAppointmentGridPane() throws Exception {
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
        gridPane.add(datePickerLbl, 0, 7);
        gridPane.add(datePicker, 1, 7);
        gridPane.add(startLbl, 0, 8);
        gridPane.add(startTxt, 1, 8);
        gridPane.add(endLbl, 0, 9);
        gridPane.add(durationComboBox, 1, 9);
        gridPane.add(newAppointmentButtonBar(), 1, 10);

        return gridPane;
    }

    // Overloaded function that includes start and duration of the appointment - used to set values in the GridPane
    private void newAppointmentGridPane(LocalDateTime start, Duration duration) throws Exception {
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
        gridPane.add(datePickerLbl, 0, 7);
        datePicker.setValue(start.toLocalDate());
        gridPane.add(datePicker, 1, 7);
        gridPane.add(startLbl, 0, 8);
        startTxt.getSelectionModel().select(LocalTime.parse(start.toLocalTime().format(DateTimeFormatter.ofPattern("hh:mm"))));
        gridPane.add(startTxt, 1, 8);
        gridPane.add(endLbl, 0, 9);
        durationComboBox.setValue((int) duration.toMinutes());
        gridPane.add(durationComboBox, 1, 9);
        gridPane.add(newAppointmentButtonBar(), 1, 10);


        this.stage.setScene(new Scene(gridPane));
    }

    // EDIT APPOINTMENT
    private ButtonBar editAppointmentButtonBar() {
        ButtonBar buttonBar = new ButtonBar();
        Button okBtn = new Button("OK");
        Button deleteBtn = new Button("Delete");
        Button cancelBtn = new Button("Cancel");

        // init button bar
        ButtonBar.setButtonData(okBtn, ButtonBar.ButtonData.OK_DONE);
        ButtonBar.setButtonData(deleteBtn, ButtonBar.ButtonData.OTHER);
        ButtonBar.setButtonData(cancelBtn, ButtonBar.ButtonData.CANCEL_CLOSE);
        buttonBar.getButtons().addAll(okBtn, deleteBtn, cancelBtn);

        okBtn.setOnAction(e -> {
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
                // if appointmentsTable is not null then it is from the AppointmentsTable view
                if(appointmentsTable != null) {
                    // get customer name
                    String customerName = new DbCustomerDao(DBUtils.getMySQLDataSource()).getById(appointment.getCustomerId()).get().getCustomerName();
                    // find appointment in AppoinmentTable.appointments and match it with appointment --> update
                    for(int i=0; i<appointmentsTable.appointments.size(); i++) {
                        if(appointmentsTable.appointments.get(i).getAppointmentId() == appointment.getId()) {
                            appointmentsTable.appointments.get(i).setAppointmentType(appointment.getType());
                            appointmentsTable.appointments.get(i).setCustomerName(customerName);
                        }
                    }
                    appointmentsTable.updateRightSideView(new Label("Appointment was successfully updated!"));
                } else {
                    // show success message for calendar view
                    weekView.getView(weekView.monday);
                    stage.close();
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "Appointment updated.");
                    alert.showAndWait();
                }
            } catch (Exception ex) {
                Alert a = new Alert(Alert.AlertType.ERROR, "Something went wrong.");
                a.showAndWait();
                ex.printStackTrace();
            }
        });

        deleteBtn.setOnAction(e -> {
            try {
                DbAppointmentDao dao = new DbAppointmentDao(DBUtils.getMySQLDataSource());
                dao.delete(appointment);
                if(appointmentsTable != null) {
                    // get customer name
                    // find appointment in AppoinmentTable.appointments and match it with appointment --> update
                    for(int i=0; i<appointmentsTable.appointments.size(); i++) {
                        if(appointmentsTable.appointments.get(i).getAppointmentId() == appointment.getId()) {
                            appointmentsTable.appointments.remove(i);
                        }
                    }
                    appointmentsTable.updateRightSideView(new Label("Appointment was successfully deleted!"));
                } else {
                    weekView.getView(weekView.monday);
                    stage.close();
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "Appointment Deleted.");
                    alert.showAndWait();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        cancelBtn.setOnAction(e -> {
            stage.close();
            if(appointmentsTable != null) {
                appointmentsTable.updateRightSideView(new Label("Press Edit, Delete, or New Appointment to load a view."));
            }}
            );

        return buttonBar;
    }

    // Build Edit Appointment Stage
    private GridPane editAppointmentGridPane() throws Exception {
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
        customerNameTxt.getSelectionModel().select(new DbCustomerDao(DBUtils.getMySQLDataSource()).getById(appointment.getCustomerId()).get());
        gridPane.add(customerNameTxt, 1, 0);

        gridPane.add(titleLbl, 0, 1);
        titleTxt.setText(appointment.getTitle());
        gridPane.add(titleTxt, 1, 1);

        gridPane.add(descriptionLbl, 0, 2);
        descriptionTxt.setText(appointment.getDescription());
        gridPane.add(descriptionTxt, 1, 2);

        gridPane.add(locationLbl, 0, 3);
        locationTxt.setText(appointment.getLocation());
        gridPane.add(locationTxt, 1, 3);

        gridPane.add(contactLbl, 0, 4);
        contactTxt.setText(appointment.getContact());
        gridPane.add(contactTxt, 1, 4);

        gridPane.add(typeLbl, 0, 5);
        typeTxt.setText(appointment.getType());
        gridPane.add(typeTxt, 1, 5);

        gridPane.add(urlLbl, 0, 6);
        urlTxt.setText(appointment.getUrl());
        gridPane.add(urlTxt, 1, 6);

        // datepicker is set somewhere else
        gridPane.add(datePickerLbl, 0, 7);
        gridPane.add(datePicker, 1, 7);

        // start is set somewhere else
        gridPane.add(startLbl, 0, 8);
        gridPane.add(startTxt, 1, 8);

        // end is set somewhere else
        gridPane.add(endLbl, 0, 9);
        gridPane.add(durationComboBox, 1, 9);

        gridPane.add(editAppointmentButtonBar(), 1, 10);

        return gridPane;
    }

    private boolean isAppointment(Appointment appointment) throws Exception {
        // default to false - assume the appointment slot is available
        AtomicBoolean isAppointment = new AtomicBoolean(false);
        Stream<Appointment> appointmentStream = new DbAppointmentDao(DBUtils.getMySQLDataSource()).getAll();
        appointmentStream.forEach(a->{
            // if appointment times start or end
            if(a.getStart() == appointment.getStart() || a.getEnd() == appointment.getEnd()) {
                isAppointment.set(true);
            }
        });
        return isAppointment.get();
    }

    public Stage getNewAppointmentStage() throws Exception {
        stage.setScene(new Scene(newAppointmentGridPane()));
        stage.setResizable(false);
        stage.setMinWidth(500);
        stage.setMinHeight(500);

        stage.setTitle("New Appointment");
        return stage;
    }

    public Stage getEditAppointmentStage(Appointment appointment) throws Exception {
        setAppointment(appointment);
        stage.setScene(new Scene(editAppointmentGridPane()));
        stage.setResizable(false);
        stage.setMinWidth(500);
        stage.setMinHeight(500);

        stage.setTitle("Edit Appointment");
        return stage;
    }

    public Stage getNewAppointmentStage(LocalDateTime start, Duration duration) throws Exception {
        newAppointmentGridPane(start, duration);
        stage.setResizable(false);
        stage.setMinWidth(500);
        stage.setMinHeight(500);

        stage.setTitle("New Appointment");
        return stage;
    }

    public GridPane getNewAppointmentGridPane() throws Exception {
        return newAppointmentGridPane();
    }

    public GridPane getEditAppointmentGridPane() throws Exception {
        return editAppointmentGridPane();
    }
}
