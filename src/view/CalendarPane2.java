package view;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.TextAlignment;
import utils.Internationalization;

import java.text.DateFormatSymbols;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

public class CalendarPane2 extends BorderPane {
    // Locale
    private Locale locale = Internationalization.getCurrentLocale();

    // CALENDAR PARTS
    // Calendar data getters
    private Calendar calendar;
    private int currentMonth;
    private int currentYear;
    private int totalDaysInCurrentMonth;
    private int today;

    // Month and Year Header
    private Label monthYearHeader = new Label();
    // Number for Days List
    private Label[] dayLabels = new Label[49];
    // List for Appointment Labels
    private Label[] appointments = new Label[1000];


    // Circle with 50px radius used to flag user of appointment on a given day
    private boolean isAppointment = false;
    private Circle isAppointmentCircle = new Circle(50);

    public Circle getIsAppointmentCircle() {
        return isAppointmentCircle;
    }

    public void setIsAppointmentCircle(Circle isAppointmentCircle, Color color) {
        if(isAppointment) {
            isAppointmentCircle.setFill(color);
            this.isAppointmentCircle = isAppointmentCircle;
        } else {
            this.isAppointmentCircle = null;
        }
    }

    // Constructor
    public CalendarPane2() {
        // Update Calendar with current data
        calendar = new GregorianCalendar();
        currentMonth = calendar.get(Calendar.MONTH);
        currentYear = calendar.get(Calendar.YEAR);
        today = calendar.get(Calendar.DAY_OF_MONTH);
        updateCalendar();

        // Create DayPane
        GridPane dayPane = new GridPane();
        dayPane.setAlignment(Pos.CENTER);
        dayPane.getStyleClass().addAll("days");
//        dayPane.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, CornerRadii.EMPTY, Insets.EMPTY)));
//        dayPane.setHgap(3);
//        dayPane.setVgap(3);


        // 7 columns
        ColumnConstraints column = new ColumnConstraints(130);
        column.setHgrow(Priority.ALWAYS);
        dayPane.getColumnConstraints().addAll(column, column, column, column, column, column, column);

        // 5 rows
        RowConstraints row1 = new RowConstraints(30);
        row1.setValignment(VPos.CENTER);
        RowConstraints row = new RowConstraints(column.getPrefWidth() -30);
        dayPane.getRowConstraints().addAll( row1, row, row, row, row, row, row);
        dayPane.setGridLinesVisible(true);

        // Create BorderPane
        // Place header and calendar body in the pane
        this.getStyleClass().add("borderPane");
        this.setTop(monthYearHeader);
        BorderPane.setAlignment(monthYearHeader, Pos.CENTER);
        this.setCenter(dayPane);

        // setTop to MONTH YEAR

        // setCenter to days & appointments

        // Set labels for Day Numbers
        for (int i = 0; i < 49; i++) {
            dayLabels[i] = new Label();
            dayLabels[i].setTextAlignment(TextAlignment.LEFT);
        }

        for (int i = 0; i < 49; i++) {
            dayPane.add(dayLabels[i], i % 7, i / 7);
        }

        showDaysOfWeek();
        showDays();

        // Add appointments to ListView

        // Add each ListView to corresponding day
    }

    private void showDaysOfWeek() {
        DateFormatSymbols dfs = new DateFormatSymbols(locale);
        String[] dayNames = dfs.getWeekdays();

        // jlblDay[0], jlblDay[1], ..., jlblDay[6] for day names
        for (int i = 0; i < 7; i++) {
//            dayLabels[i].setTextAlignment(TextAlignment.CENTER);
            dayLabels[i].setText(dayNames[i + 1]);

            // Center text horizontally and vertically
            GridPane.setConstraints(dayLabels[i],  i,  0,  1,  1, HPos.CENTER, VPos.CENTER);
        }
    }
    public void showDays() {
        int startingDayOfMonth = calendar.get(Calendar.DAY_OF_WEEK);

        // Display days of this month
        int daysInCurrentMonth = calendar.getActualMaximum(
                Calendar.DAY_OF_MONTH);
        for (int i = 1; i <= daysInCurrentMonth; i++) {
            // offset with starting day of month
            dayLabels[i + 2 + startingDayOfMonth + 7].setText(i + "");
            dayLabels[i + 2 + startingDayOfMonth + 7].setPadding(new Insets(-60, 0, 0, 10));
//            dayLabels[i + 2 + startingDayOfMonth + 7].setPadding(new Insets(1, 0, 0, 1));
        }
    }
    // CRUD helper methods
    public void updateCalendar() {
        calendar.set(Calendar.YEAR, currentYear);
        calendar.set(Calendar.MONTH, currentMonth);
        calendar.set(Calendar.DATE, 1);
        calendar.set(Calendar.DAY_OF_WEEK_IN_MONTH, today);
    }
}
