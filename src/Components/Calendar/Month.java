package Components.Calendar;

import Components.Appointments.AppointmentCard;
import DbDao.DbAppointmentDao;
import DbDao.DbCustomerDao;
import POJO.Appointment;
import POJO.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventType;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import utils.DBUtils;

import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * CalendarMonthModel contains the CalendarMonth data.
 */
public class Month {
    private User user;
    private Week week;
    private ArrayList<AnchorPaneNode> allCalendarDays = new ArrayList<>(35);
    private VBox view;
    private Text calendarTitle;
    private YearMonth currentYearMonth;
    private ObservableList<Appointment> appointmentsThisMonth = FXCollections.observableArrayList();
    private Stream<Appointment> appointmentStream = new DbAppointmentDao(DBUtils.getMySQLDataSource()).getAll();

    // new appointment button
    Button newAppointmentBtn = new Button("New Appointment");

    public Month(User user, Week week) throws Exception {
        this.user = user;
        this.week = week;
        buildAppointmentsThisMonth();
    }

    /**
     * Create a calendar view
     *
     * @param yearMonth year month to create the calendar of
     */
    public void build(YearMonth yearMonth) throws Exception {
        currentYearMonth = yearMonth;
        // Create the calendar grid pane
        GridPane calendar = new GridPane();
        calendar.getStylesheets().add("CSS/calendarPane.css");
        calendar.getStyleClass().add("calendar");
        calendar.setPrefSize(600, 400);
        calendar.setGridLinesVisible(true);
        // Create rows and columns with anchor panes for the calendar
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 7; j++) {
                AnchorPaneNode ap = new AnchorPaneNode();
                ap.setPrefSize(200, 200);
                calendar.add(ap, j, i);
                allCalendarDays.add(ap);
            }
        }
        // Days of the week labels
        Text[] dayNames = new Text[]{new Text("SUN"), new Text("MON"), new Text("TUE"),
                new Text("WED"), new Text("THU"), new Text("FRI"),
                new Text("SAT")};
        GridPane dayLabels = new GridPane();
        dayLabels.getStylesheets().add("CSS/calendarPane.css");
        dayLabels.getStyleClass().add("day-labels");
        dayLabels.setPrefWidth(500);
        Integer col = 0;
        for (Text txt : dayNames) {
            AnchorPane ap = new AnchorPane();
            ap.getStyleClass().add("days-small");
            ap.setMinSize(dayLabels.getPrefWidth() / 6, 10);
            AnchorPane.setBottomAnchor(txt, 5.0);
            AnchorPane.setLeftAnchor(txt, 8.0);
            ap.getChildren().add(txt);
            dayLabels.add(ap, col++, 0);
        }
        // Create calendarTitle and buttons to change current month
        calendarTitle = new Text();
        Button previousMonth = new Button("<<");
        previousMonth.setOnAction(e -> {
            try {
                previousMonth();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        Button nextMonth = new Button(">>");
        nextMonth.setOnAction(e -> {
            try {
                nextMonth();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        // style
        calendarTitle.setStyle("-fx-padding: 10 5;" +
                "-fx-font-family: 'Roboto Light';" +
                "-fx-font-weight: BOLD;" +
                "-fx-font-size: 25;");
        previousMonth.setStyle("-fx-padding: 0 5;" +
                "-fx-font-family: 'Roboto Bold';" +
                "-fx-font-weight: BOLD;" +
                "-fx-font-size: 18;" +
                "-fx-background-color: #38909b;");
        nextMonth.setStyle("-fx-padding: 0 5;" +
                "-fx-font-family: 'Roboto Bold';" +
                "-fx-font-weight: BOLD;" +
                "-fx-font-size: 18;" +
                "-fx-background-color: #38909b;");
        HBox titleBar = new HBox(previousMonth, calendarTitle, nextMonth);
        titleBar.setStyle("-fx-background-color: WHITE;" +
                "-fx-padding: 10;" +
                "-fx-border-radius: .5;");
        titleBar.setSpacing(25);
        titleBar.setAlignment(Pos.BASELINE_CENTER);
        // Populate calendar with the appropriate day numbers
        populateCalendar(yearMonth);
        // Create the calendar view
        view = new VBox(titleBar, dayLabels, calendar);
        view.setStyle("-fx-border-color: GREY;" +
                "-fx-border-width: 2;");
    }

    /**
     * Set the days of the calendar to correspond to the appropriate date
     *
     * @param yearMonth year and month of month to render
     */
    public void populateCalendar(YearMonth yearMonth) throws Exception {
        // Get the date we want to start with on the calendar
        LocalDate calendarDate = LocalDate.of(yearMonth.getYear(), yearMonth.getMonthValue(), 1);
        // Dial back the day until it is SUNDAY (unless the month starts on a sunday)
        while (!calendarDate.getDayOfWeek().toString().equals("SUNDAY")) {
            calendarDate = calendarDate.minusDays(1);
        }
        // Populate the calendar with day numbers
        for (AnchorPaneNode ap : allCalendarDays) {
            ap.setStyle("-fx-background-color: WHITE;");
            if (ap.getChildren().size() != 0) {
                ap.getChildren().remove(0);
                ap.onMouseClickedProperty().set(null);
            }
            Text txt = new Text(String.valueOf(calendarDate.getDayOfMonth()));
            txt.setStyle("-fx-font-family: 'Roboto Bold';" +
                    "-fx-font-size: 16;" +
                    "-fx-font-weight: BOLD;");
            ap.setDate(calendarDate);
            AnchorPane.setTopAnchor(txt, 5.0);
            AnchorPane.setLeftAnchor(txt, 5.0);
            ap.getChildren().add(txt);

            ap.removeEventHandler(EventType.ROOT, Event::consume);
            LocalDate finalCalendarDate = calendarDate;
            ap.setOnMouseClicked(e -> {
                System.out.println(week);
                try {
                    updateWeek(ap.getDate());
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            });

            if (isAppointment(ap.getDate(), ap)) {
                Label appointmentCount = new Label(String.valueOf(ap.appointments.size()));
                appointmentCount.setFont(Font.font("Roboto Bold", FontWeight.BOLD, 40));
                appointmentCount.setStyle("-fx-text-fill: white;");
                AnchorPane.setTopAnchor(appointmentCount, 15.0);
                AnchorPane.setRightAnchor(appointmentCount, 30.0);
                ap.getChildren().add(appointmentCount);
                ap.setStyle("-fx-background-color: rgba(210, 145, 188, .5)");
            }
            calendarDate = calendarDate.plusDays(1);
        }
        // Change the title of the calendar
        calendarTitle.setText(yearMonth.getMonth().toString() + " " + yearMonth.getYear());
    }

    /**
     * Find if appointment exists on current calendar day
     */

    private void buildAppointmentsThisMonth() {
        appointmentStream.forEach(a -> {
            appointmentsThisMonth.add(a);
        });
    }

    private boolean isAppointment(LocalDate calendarDate, AnchorPaneNode ap) {
        boolean match = false;
        for (Appointment a : appointmentsThisMonth) {
            String startDate = new SimpleDateFormat("yyyy-MM-dd").format(a.getStart());
            if (startDate.equals(String.valueOf(calendarDate)) && a.getUserId() == user.getId()) {
                ap.appointments.add(a);
                match = true;
            }
        }
        return match;
    }

    /**
     * Move the month back by one. Repopulate the calendar with the correct dates.
     */
    private void previousMonth() throws Exception {
        currentYearMonth = currentYearMonth.minusMonths(1);
        populateCalendar(currentYearMonth);
    }

    /**
     * Move the month forward by one. Repopulate the calendar with the correct dates.
     */
    private void nextMonth() throws Exception {
        currentYearMonth = currentYearMonth.plusMonths(1);
        populateCalendar(currentYearMonth);
    }

    public VBox getView() {
        return view;
    }

    public ArrayList<AnchorPaneNode> getAllCalendarDays() {
        return allCalendarDays;
    }

    public void setAllCalendarDays(ArrayList<AnchorPaneNode> allCalendarDays) {
        this.allCalendarDays = allCalendarDays;
    }

    public void updateWeek(LocalDate localDate) throws Exception {
        week.getView(localDate.with(DayOfWeek.MONDAY));
    }
    /**
     * Create an anchor pane that can store additional data.
     */
    private class AnchorPaneNode extends AnchorPane {

        // Date associated with this pane
        private LocalDate date;

        // Appointment IDs associated with this pane
        private ObservableList<Appointment> appointments = FXCollections.observableArrayList();

        /**
         * Create a anchor pane node. Date is not assigned in the constructor.
         *
         * @param children children of the anchor pane
         */
        public AnchorPaneNode(Node... children) {
            super(children);
            this.removeEventHandler(EventType.ROOT, Event::consume);
            // Add action handler for mouse clicked
            this.setOnMouseClicked(e -> {
                Stage appointmentStage;

                try {
                    if (appointments.size() > 0) {
                        appointmentStage = thisDaysAppointmentsStage(appointments);
                    } else {
                        appointmentStage = new AppointmentCard(user).getNewAppointmentStage();
                    }
                    appointmentStage.showAndWait();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

            });
        }

        public LocalDate getDate() {
            return date;
        }

        public void setDate(LocalDate date) {
            this.date = date;
        }

        // use if you want a popup that lists all the current appointments for an anchor pane

        // add this after line 181
        //Stage appointmentStage;
        //                try {
//                    if (ap.appointments.size() > 0) {
//                        appointmentStage = ap.thisDaysAppointmentsStage(ap.appointments);
//                    } else {
//                        appointmentStage = new AppointmentCard(finalCalendarDate, user.getId()).getNewAppointmentStage();
//                    }
//                    appointmentStage.showAndWait();
//                } catch (Exception ex) {
//                    ex.printStackTrace();
//                }
        private Stage thisDaysAppointmentsStage(ObservableList<Appointment> appointments) throws Exception {
            Stage stage = new Stage();
            VBox appointmentVBox = new VBox(15);
            appointmentVBox.setPrefSize(400, 400);
            appointmentVBox.setPadding(new Insets(25));
            appointmentVBox.setStyle("-fx-font-family: 'Roboto Bold';" +
                    "-fx-font-size: 12;" +
                    "-fx-font-weight: BOLD;");
            for (Appointment appointment : appointments) {
                String appointmentType = appointment.getType();
                String customerName = String.valueOf(new DbCustomerDao(DBUtils.getMySQLDataSource()).getById(appointment.getCustomerId()).get().getCustomerName());
                Optional<Appointment> appointmentToEdit = new DbAppointmentDao(DBUtils.getMySQLDataSource()).getById(appointment.getId());
                Button updateAppointmentBtn = new Button("Update");
                updateAppointmentBtn.setOnAction(e -> {
                    try {
                        stage.setMinWidth(500);
                        stage.setScene(new Scene(new AppointmentCard(appointmentToEdit.get().getId()).getNewAppointmentGridPane()));
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                });
                Button deleteAppointmentBtn = new Button("Delete");
                deleteAppointmentBtn.setOnAction(e -> {
                    try {
                        DbAppointmentDao dao = new DbAppointmentDao(DBUtils.getMySQLDataSource());
                        Alert a = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this appointment?");

                        a.showAndWait()
                                .filter(response -> response == ButtonType.OK)
                                .ifPresent(response -> {
                                    try {
                                        dao.delete(appointmentToEdit.get());
                                        Alert a2 = new Alert(Alert.AlertType.INFORMATION, "Success!");
                                        a2.showAndWait();
                                    } catch (Exception ex) {
                                        Alert a3 = new Alert(Alert.AlertType.INFORMATION, "Something went wrong.");
                                        a3.showAndWait();
                                        ex.printStackTrace();
                                    }
                                });
                    } catch (Exception ex) {
                        Alert a = new Alert(Alert.AlertType.INFORMATION, "Something went wrong.");
                        a.showAndWait();
                        ex.printStackTrace();
                    }
                });

                Separator separator = new Separator();
                HBox appt = new HBox(20);
                appt.getChildren().addAll(new Label(appointmentType), new Label(customerName), updateAppointmentBtn, deleteAppointmentBtn);
                appointmentVBox.getChildren().addAll(appt, separator);
                newAppointmentBtn.setOnAction(e -> {
                    try {
                        stage.setScene(new Scene(new AppointmentCard(date, user.getId()).getNewAppointmentGridPane()));
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                });
            }

            appointmentVBox.getChildren().add(newAppointmentBtn);
            stage.setScene(new Scene(appointmentVBox));
            return stage;
        }
    }
}
