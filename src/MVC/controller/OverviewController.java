package MVC.controller;

import DAO.CustomerDao;
import MVC.view.AppointmentVBox;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.io.IOException;
import java.sql.SQLException;
import java.text.DateFormatSymbols;
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
        day1.prefWidthProperty().bind(weeklyView.widthProperty().multiply(.142857));
        day2.prefWidthProperty().bind(weeklyView.widthProperty().multiply(.142857));
        day3.prefWidthProperty().bind(weeklyView.widthProperty().multiply(.142857));
        day4.prefWidthProperty().bind(weeklyView.widthProperty().multiply(.142857));
        day5.prefWidthProperty().bind(weeklyView.widthProperty().multiply(.142857));
        day6.prefWidthProperty().bind(weeklyView.widthProperty().multiply(.142857));
        day7.prefWidthProperty().bind(weeklyView.widthProperty().multiply(.142857));

        // create day labels
        // TODO set days dynamically based on calendar
        CalendarMonthController calendarController = new CalendarMonthController();
        int today = calendarController.c.getToday();
        String currentName;
//        DateFormatSymbols dfs = new DateFormatSymbols(Locale.getDefault());

        // set day names
        for (int i = 0; i < 7; i++) {

            Calendar cal = calendarController.c.getCalendar();
            DateFormatSymbols dfs = new DateFormatSymbols(calendarController.c.getLocale());
            String[] dayNames = dfs.getWeekdays();

            String firstLetter = String.valueOf(String.valueOf(dayNames[i + 1]).charAt(0));
            String secondLetter = String.valueOf(String.valueOf(dayNames[i + 1]).charAt(1));
            String thirdLetter = String.valueOf(String.valueOf(dayNames[i + 1]).charAt(2));

            // date label
            Text dateText = new Text(String.valueOf(today + i));
            Circle dateCircle = new Circle(30);
            dateCircle.setFill(new Color(Math.random(), Math.random(), Math.random(), 1));
            StackPane dateStack = new StackPane();
            dateStack.getChildren().addAll(dateCircle, dateText);
            dateStack.alignmentProperty().setValue(Pos.CENTER);
            dateStack.translateXProperty().setValue(-15);
            dateStack.paddingProperty().setValue(new Insets(20, 10 , 20, 10));

            // weekday name label
            Label nameLbl = new Label(firstLetter + secondLetter + thirdLetter);
            nameLbl.alignmentProperty().setValue(Pos.CENTER);
            nameLbl.translateXProperty().setValue(50);
            nameLbl.setFont(new Font(20));
            nameLbl.paddingProperty().setValue(new Insets(20, 0, 10, 0));

            // add both
            weeklyView.add(dateStack, i, 0);
            weeklyView.add(nameLbl, i, 1);
        }
//        Label test = new Label(String.valueOf(today));
//        test.setAlignment(Pos.CENTER);
//        test.setFont(new Font(20));
//        Label test2 = new Label("DAY2");
//        test2.setAlignment(Pos.CENTER);
//        test2.setFont(new Font(20));

        // add labels
//        weeklyView.add(test, 0, 0);
//        day2.prefWidthProperty().bind(weeklyView.widthProperty().multiply(.14));
//        weeklyView.add(test2, 1,0);

        // create separator
        Separator horSep = new Separator();
        horSep.setMaxWidth(day1.getPrefWidth() + 100);
        horSep.setHalignment(HPos.CENTER);
        horSep.setPadding(new Insets(10, 10, 10, 10));
        weeklyView.add(horSep, 0, 2);
        VBox apptBox2 = new VBox(5);
//        apptBox2.setStyle("-fx-border-style: none none solid none; -fx-border-width: 0 0 1 0; -fx-border-color: LIGHTCORAL; -fx-padding: 10; -fx-start-margin: 10; -fx-end-margin: 10;");
//        apptBox2.getChildren().addAll(
//                new Label(appt.getContact()),
//                new Label(String.valueOf(appt.getStart().get(Calendar.HOUR) + ":" + appt.getStart().get(Calendar.MINUTE) + appt.getStart().get(Calendar.AM_PM))),
//                new Label(String.valueOf(appt.getEnd().get(Calendar.HOUR_OF_DAY) + ":" + appt.getStart().get(Calendar.MINUTE) + appt.getStart().get(Calendar.AM_PM)))
//        );
        Label custTest = new Label(CustomerDao.getAllCustomers().get(1).getAddress());
        weeklyView.add(custTest, 0, 2);
        weeklyView.add(new AppointmentVBox().getAppointmentBox(), 1, 2);

    }

    public void onActionGetPrevMonth(ActionEvent actionEvent) {
    }

    public void onActionGetNextMonth(ActionEvent actionEvent) {
    }
}
