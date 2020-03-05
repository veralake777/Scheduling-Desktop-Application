package MVC.controller;

import MVC.view.AppointmentVBox;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import utils.DateTime.DateTimeUtils;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Calendar;

public class OverviewController {
    // top level container
    @FXML
    public GridPane overview;

    // calendar view on the
    @FXML
    public BorderPane calendarPane;
    @FXML
    public Label monthAndYearLbl;
    @FXML
    public Button prevBtn;
    @FXML
    public Button nextBtn;

    @FXML
    public GridPane weeklyView;
    @FXML
    public TableColumn weekDayCol1;
    @FXML
    public TableColumn weekDayCol2;
    @FXML
    public TableColumn weekDayCol3;
    @FXML
    public TableColumn weekDayCol4;
    @FXML
    public TableColumn weekDayCol5;
    @FXML
    public TableColumn weekDayCol6;
    @FXML
    public TableColumn weekDayCol7;
    @FXML
    public BorderPane calendarMonthView;
    public ButtonBar prevNextBtnBar;

    @FXML
    public ColumnConstraints hours;
    @FXML
    public ColumnConstraints day1;
    @FXML
    public ColumnConstraints day2;
    @FXML
    public ColumnConstraints day3;
    @FXML
    public ColumnConstraints day4;
    @FXML
    public ColumnConstraints day5;
    @FXML
    public ColumnConstraints day6;
    @FXML
    public ColumnConstraints day7;



    public OverviewController() throws IOException {
    }

    @FXML
    public void initialize() throws Exception {
        setOverview();
    }

    CalendarMonthController calendarController = new CalendarMonthController();


    public GridPane getOverview() {
        return overview;
    }

    public void setOverview() throws IOException, ParseException, SQLException, ClassNotFoundException {
        calendarMonthView.setMaxWidth(200);
        calendarMonthView.prefHeightProperty().bind(calendarPane.heightProperty());
        calendarMonthView.prefWidthProperty().bind(calendarPane.widthProperty());

        /**
         * Appointment View
         */
        VBox appointmentBox = new AppointmentVBox().getAppointmentBox();
        appointmentBox.getChildren().add(appointmentBox.getChildren().size(), new Separator());
        appointmentBox.getChildren().add(appointmentBox.getChildren().size(), new Button("Add Appointment"));
        calendarMonthView.setBottom(appointmentBox);

        /**
         * Week View
         */
//        hours.setPercentWidth(5);
//        day1.setPercentWidth(13.5714286);
//        day2.setPercentWidth(13.5714286);
//        day3.setPercentWidth(13.5714286);
//        day4.setPercentWidth(13.5714286);
//        day5.setPercentWidth(13.5714286);
//        day6.setPercentWidth(13.5714286);
//        day7.setPercentWidth(13.5714286);

        hours.prefWidthProperty().bind(weeklyView.widthProperty().multiply(.07));
        day1.prefWidthProperty().bind(weeklyView.widthProperty().multiply(.135));
        day2.prefWidthProperty().bind(weeklyView.widthProperty().multiply(.135));
        day3.prefWidthProperty().bind(weeklyView.widthProperty().multiply(.135));
        day4.prefWidthProperty().bind(weeklyView.widthProperty().multiply(.135));
        day5.prefWidthProperty().bind(weeklyView.widthProperty().multiply(.135));
        day6.prefWidthProperty().bind(weeklyView.widthProperty().multiply(.135));
        day7.prefWidthProperty().bind(weeklyView.widthProperty().multiply(.135));

//        weeklyView.add(new Label("HOURS"), 0, 2);
        addCurrentHours();

        // create day labels
        // TODO set days dynamically based on calendar
        CalendarMonthController calendarController = new CalendarMonthController();
        int today = calendarController.c.getToday();
        String currentName;
//        DateFormatSymbols dfs = new DateFormatSymbols(Locale.getDefault());

        // set day names
        String[] dayNames = DateTimeUtils.getDaysOfWeek(today);


        for (int i = 1; i < 7; i++) {
            // date label
            Text dateText = new Text(String.valueOf(today + i));
            dateText.setFill(Color.WHITE);
            dateText.setFont(Font.font("Verdana", FontWeight.BOLD, 18));
            Circle dateCircle = new Circle(30);
            dateCircle.setFill(new Color(0.97931841398198070, 0.32340070014395870, 0.48078956516474713, .5));
            StackPane dateStack = new StackPane();
            dateStack.getChildren().addAll(dateCircle, dateText);
            dateStack.alignmentProperty().setValue(Pos.CENTER);
            dateStack.translateXProperty().setValue(-15);
            dateStack.paddingProperty().setValue(new Insets(20, 10, 20, 10));

            // weekday name label
            Label nameLbl = new Label(dayNames[i]);
            nameLbl.alignmentProperty().setValue(Pos.CENTER);
            nameLbl.translateXProperty().setValue(50);
            nameLbl.setFont(new Font(20));
            nameLbl.paddingProperty().setValue(new Insets(0, 0, 10, 0));

            // add both
            weeklyView.add(dateStack, i, 0);
            weeklyView.add(nameLbl, i, 1);

            weeklyView.add(addAppointment(), 5, 3);
        }

        // create separator
//        Separator horSep = new Separator();
//        horSep.setMaxWidth(day1.getPrefWidth() + 100);
//        horSep.setHalignment(HPos.CENTER);
//        horSep.setPadding(new Insets(10, 10, 10, 10));
//        weeklyView.add(horSep, 0, 2);
//
//        // Next Appointment
//        VBox apptBox2 = new VBox(5);
//        Label custTest = new Label(CustomerDao.getAllCustomers().get(1).getAddress());
//        weeklyView.add(custTest, 0, 2);
//        weeklyView.add(new AppointmentVBox().getAppointmentBox(), 1, 2);

    }

    private void addCurrentHours() {
        // get current time
        Calendar calendar = calendarController.c.getCalendar();
        int currentHour = calendar.get(Calendar.HOUR_OF_DAY);
        int rowIndex = 2;
        // go til 11:59PM
        while(currentHour < 24) {
            Label currentHourLbl = new Label(String.valueOf(currentHour));
            currentHourLbl.setPadding(new Insets(50, 10, 0, 50));
            weeklyView.add(currentHourLbl, 0, rowIndex);
            currentHour++;
            rowIndex++;
        }
    }

    private VBox addAppointment() throws ParseException, SQLException, ClassNotFoundException {
//        AppointmentDao.addAppointment(20, 1, 1, "Vbox Test", "test", "", "555-5555", "Video Call", "...", "NOW()", "NOW()", "test", "NOW()", "test");
        //        weeklyView.add(new AppointmentVBox().getAppointmentById(1), 1, 4);
        return new AppointmentVBox().getAppointmentById(1);
    }

    public void onActionGetPrevMonth(ActionEvent actionEvent) {
    }

    public void onActionGetNextMonth(ActionEvent actionEvent) {
    }
}
