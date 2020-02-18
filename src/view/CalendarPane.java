package view;

import DAO.AppointmentDao;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import model.Appointment;

import java.sql.SQLException;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

public class CalendarPane extends BorderPane {
    // The header label
    private Label lblHeader = new Label();

    // Maximum number of labels to display day names and days
    private Label[] lblDay = new Label[49];

    // Appointment labels
    private Label[] lblAppointment = new Label[300];

    private Calendar calendar;
    private int month;  // The specified month
    private int year;  // The specified year
    private int today; // The specified current day
    // TODO user Internationalization util class
    private Locale locale = Locale.getDefault();

    public CalendarPane() throws ParseException, SQLException, ClassNotFoundException {
        // Create labels for displaying days
        for (int i = 0; i < 49; i++) {
            lblDay[i] = new Label();
            lblDay[i].setTextAlignment(TextAlignment.RIGHT);
        }

        showDayNames(); // Display day names for the locale

        GridPane dayPane = new GridPane();
        dayPane.setAlignment(Pos.CENTER);
        dayPane.getStyleClass().addAll("days");

        // 7 columns
        ColumnConstraints column = new ColumnConstraints();
        column.setPercentWidth(14);
        dayPane.getColumnConstraints().add(column);
        dayPane.getColumnConstraints().add(column);
        dayPane.getColumnConstraints().add(column);
        dayPane.getColumnConstraints().add(column);
        dayPane.getColumnConstraints().add(column);
        dayPane.getColumnConstraints().add(column);
        dayPane.getColumnConstraints().add(column);

        // 6 rows
        RowConstraints row = new RowConstraints();
        row.setPercentHeight(12);
        dayPane.getRowConstraints().add(row);
        dayPane.getRowConstraints().add(row);
        dayPane.getRowConstraints().add(row);
        dayPane.getRowConstraints().add(row);
        dayPane.getRowConstraints().add(row);
        dayPane.getRowConstraints().add(row);

        // add day numbers to dayPane
         for (int i = 0; i < 49; i++) {
                dayPane.add(lblDay[i], i % 7, i / 7);
        }

//        // add appointments to dayPane
//        for(int i = 0; i < 300; i ++) {
//            dayPane.add(lblAppointment[i], 1, 1);
//        }


        // Place header and calendar body in the pane
        this.getStyleClass().add("borderPane");
        this.setTop(lblHeader);
        BorderPane.setAlignment(lblHeader, Pos.CENTER);
        this.setCenter(dayPane);

        // Set current month and year and day
        calendar = new GregorianCalendar();
        month = calendar.get(Calendar.MONTH);
        year = calendar.get(Calendar.YEAR);
        today = calendar.get(Calendar.DAY_OF_MONTH);
        updateCalendar();

        // Show calendar
        showHeader();
        showDays();
    }

    /** Update the day names based on locale */
    private void showDayNames() {
        DateFormatSymbols dfs = new DateFormatSymbols(locale);
        String[] dayNames = dfs.getWeekdays();

        // jlblDay[0], jlblDay[1], ..., jlblDay[6] for day names
        for (int i = 0; i < 7; i++) {
            lblDay[i].setText(dayNames[i + 1]);
        }
    }

    /** Update the header based on locale */
    private void showHeader() {
        SimpleDateFormat sdf = new SimpleDateFormat("MMMM yyyy", locale);
        String header = sdf.format(calendar.getTime());
        lblHeader.setText(header);
    }

    public void showDays() throws ParseException, SQLException, ClassNotFoundException {
        // Get the day of the first day in a month
        int startingDayOfMonth = calendar.get(Calendar.DAY_OF_WEEK);

        // Fill the calendar with the days before this month
        Calendar cloneCalendar = (Calendar) calendar.clone();
        cloneCalendar.add(Calendar.DATE, -1); // Becomes preceding month
        int daysInPrecedingMonth = cloneCalendar.getActualMaximum(
                Calendar.DAY_OF_MONTH);
//
//        for (int i = 0; i < startingDayOfMonth - 1; i++) {
//            lblDay[i + 7].setTextFill(Color.LIGHTGRAY);
//            lblDay[i + 7].setText(daysInPrecedingMonth
//                    - startingDayOfMonth + 2 + i + "");
//        }

        // Display days of this month
        int daysInCurrentMonth = calendar.getActualMaximum(
                Calendar.DAY_OF_MONTH);
        for (int i = 1; i <= daysInCurrentMonth; i++) {

            lblDay[i - 2 + startingDayOfMonth + 7].setText(i + "");

            if(isAppointment(calendar.get(Calendar.MONTH), i - 2 + startingDayOfMonth + 7)){
                lblDay[i - 2 + startingDayOfMonth + 7].setTextFill(Color.RED);
            } else {
                lblDay[i - 2 + startingDayOfMonth + 7].setTextFill(Color.BLACK);
            }
        }
        lblDay[today + daysInPrecedingMonth - 6].setTextFill(Color.RED);


        // Fill the calendar with the days after this month
//        int j = 1;
//        for (int i = daysInCurrentMonth - 1 + startingDayOfMonth + 7;
//             i < 49; i++) {
//            lblDay[i].setTextFill(Color.LIGHTGRAY);
//            lblDay[i].setText(j++ + "");
//        }
    }

    private boolean isAppointment(int month, int day) throws ParseException, SQLException, ClassNotFoundException {
        // get appointments in this month
        ObservableList<Appointment> apptsThisMonth = AppointmentDao.getAppointmentsWithinMonth(2);

        // show appointments that match lblDay
        for(Appointment appointment: apptsThisMonth){
            Calendar startDate = appointment.getStart();
            int startMonth = startDate.get(Calendar.MONTH);
            int startDay = startDate.get(Calendar.DAY_OF_MONTH);

            if(month == startMonth && day == startDay){
                lblAppointment[startDay - 1].setTextFill(Color.RED);
                lblAppointment[startDay - 1].setText(appointment.getContact());
                return true;
            }
        }
        return false;
    }

    /** Set the calendar to the first day of the
     * specified month and year
     */
    public void updateCalendar() {
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DATE, 1);
        calendar.set(Calendar.DAY_OF_WEEK_IN_MONTH, today);
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int newMonth) throws ParseException, SQLException, ClassNotFoundException {
        month = newMonth;
        updateCalendar();
        showHeader();
        showDays();
    }

    public int getYear() {
        return year;
    }

    public void setYear(int newYear) throws ParseException, SQLException, ClassNotFoundException {
        year = newYear;
        updateCalendar();
        showHeader();
        showDays();
    }

    public void setLocale(Locale locale) throws ParseException, SQLException, ClassNotFoundException {
        this.locale = locale;
        updateCalendar();
        showDayNames();
        showHeader();
        showDays();
    }
}
