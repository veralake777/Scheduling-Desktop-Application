package view;

import DAO.AppointmentDao;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import model.Appointment;

import java.sql.SQLException;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

public class CalendarPane extends BorderPane {
    // Locale
    private Locale locale = Locale.getDefault();

    // CALENDAR PARTS
    // Calendar data getters
    private Calendar calendar;
    private int currentMonth;
    private int currentYear;
    private int totalDaysInCurrentMonth;
    private int today;
    private String currentMonthString;

    // Month and Year Header
    private Label monthYearHeader = new Label();
    // Number for Days List
    private Label[] dayLabels = new Label[49];
    // List for Appointment Labels
    private Label[] appointments = new Label[1000];


    // Circle with 10px radius used to flag user of appointment on a given day
    private boolean isAppointment = false;
    private Circle isAppointmentCircle = new Circle(10);

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
    public CalendarPane() throws ParseException, SQLException, ClassNotFoundException {
        // Update Calendar with current data
        calendar = new GregorianCalendar();
        currentMonth = calendar.get(Calendar.MONTH);
        currentMonthString =  calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault());
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
        RowConstraints row = new RowConstraints(column.getPrefWidth() -40);
        dayPane.getRowConstraints().addAll( row1, row, row, row, row, row, row);
        dayPane.setGridLinesVisible(true);

        // Create BorderPane
        // Place header and calendar body in the pane
        this.getStyleClass().add("borderPane");

        // header
        this.setTop(monthYearHeader);
        BorderPane.setAlignment(monthYearHeader, Pos.CENTER);

        // calendar
        this.setCenter(dayPane);

        // previous month and next month buttons

        // create button bar instance
        ButtonBar prevNextBtnBar = new ButtonBar();

        // create the buttons to go into ButtonBar
        Button nextMonthBtn = new Button("NEXT MONTH");
        ButtonBar.setButtonData(nextMonthBtn, ButtonBar.ButtonData.NEXT_FORWARD);
        nextMonthBtn.setAlignment(Pos.CENTER);
        nextMonthBtn.setFont(new Font(18));
        nextMonthBtn.setPrefSize(250, 10);

        Button prevMonthBtn = new Button("LAST MONTH");
        ButtonBar.setButtonData(prevMonthBtn, ButtonBar.ButtonData.BACK_PREVIOUS);
        prevMonthBtn.setAlignment(Pos.CENTER);
        prevMonthBtn.setFont(new Font(18));
        prevMonthBtn.setPrefSize(250, 10);

        // add buttons to button bar
        prevNextBtnBar.getButtons().addAll(nextMonthBtn, prevMonthBtn);
        prevNextBtnBar.setTranslateX(-460);


        this.setAlignment(prevMonthBtn, Pos.CENTER);
        this.setBottom(prevNextBtnBar);



        // setTop to MONTH YEAR

        // setCenter to days & appointments

        // Set labels for Day Numbers
        for (int i = 0; i < 49; i++) {
            dayLabels[i] = new Label();
            dayLabels[i].setTextAlignment(TextAlignment.LEFT);

            appointments[i] = new Label();
            appointments[i].setTextAlignment(TextAlignment.RIGHT);

        }

        for (int i = 0; i < 49; i++) {
            dayPane.add(dayLabels[i], i % 7, i / 7);
            dayPane.add(appointments[i], i % 7, i / 7);
        }

        showHeader();
        showDaysOfWeek();
        showDays();

        // Add appointments to ListView

        // Add each ListView to corresponding day
    }

    private void showHeader() {
        monthYearHeader.setText(this.currentMonthString + " " + String.valueOf(currentYear));
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
    public void showDays() throws ParseException, SQLException, ClassNotFoundException {
        int startingDayOfMonth = calendar.get(Calendar.DAY_OF_WEEK);
        ObservableList<Appointment> appts = null;

        // get appointments for current month
        try {
            appts = AppointmentDao.getAppointmentsWithinMonth(currentMonth);
        } catch (ClassNotFoundException | SQLException | ParseException e) {
            e.printStackTrace();
        }
        int appointmentDate;

        // Display days of this month
        int daysInCurrentMonth = calendar.getActualMaximum(
                Calendar.DAY_OF_MONTH);
        for (int i = 1; i <= daysInCurrentMonth; i++) {
            int index = i + 1 + startingDayOfMonth + 7;
            // offset with starting day of month
            dayLabels[index].setText(i + "");
            dayLabels[index].setPadding(new Insets(-60, 0, 0, 10));

            // map appointments to correct dayNumber
            for(Appointment a : appts){
                int startDate = a.getStart().get(Calendar.DAY_OF_MONTH);
//                appointments[index].setText(a.getTitle());
                if(startDate == i) {
                    isAppointment = true;

                    // TODO color differences based on type
                    setIsAppointmentCircle(isAppointmentCircle, Color.LIGHTCORAL);
                    appointments[index].setGraphic(getIsAppointmentCircle());
                    appointments[index].setPadding(new Insets( -60, 0, 0, 25));
                    isAppointment = false;
                }
            }
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
