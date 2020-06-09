package MVC.model;

import javafx.scene.control.Label;
import javafx.scene.shape.Circle;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * CalendarMonthModel contains the CalendarMonth data.
 */
public class CalendarMonthModel {
    // Locale
    private Locale locale;

    // CALENDAR PARTS
    // Calendar data getters
    private GregorianCalendar calendar;
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

    public CalendarMonthModel(Locale locale, GregorianCalendar calendar, int currentMonth, int currentYear,
                              int totalDaysInCurrentMonth, int today, String currentMonthString, Label monthYearHeader,
                              Label[] dayLabels, Label[] appointments, boolean isAppointment,
                              Circle isAppointmentCircle) {
        this.locale = locale;
        this.calendar = calendar;
        this.currentMonth = currentMonth;
        this.currentYear = currentYear;
        this.totalDaysInCurrentMonth = totalDaysInCurrentMonth;
        this.today = today;
        this.currentMonthString = currentMonthString;
        this.monthYearHeader = monthYearHeader;
        this.dayLabels = dayLabels;
        this.appointments = appointments;
        this.isAppointment = isAppointment;
        this.isAppointmentCircle = isAppointmentCircle;
    }

    public String getCurrentMonthString() {
        return currentMonthString;
    }

    public void setCurrentMonthString(String currentMonthString) {
        this.currentMonthString = currentMonthString;
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public Calendar getCalendar() {
        return calendar;
    }

    public void setCalendar(GregorianCalendar calendar) {
        this.calendar = calendar;
    }

    public int getCurrentMonth() {
        return currentMonth;
    }

    public void setCurrentMonth(int currentMonth) {
        this.currentMonth = currentMonth;
    }

    public int getCurrentYear() {
        return currentYear;
    }

    public void setCurrentYear(int currentYear) {
        this.currentYear = currentYear;
    }

    public int getTotalDaysInCurrentMonth() {
        return totalDaysInCurrentMonth;
    }

    public void setTotalDaysInCurrentMonth(int totalDaysInCurrentMonth) {
        this.totalDaysInCurrentMonth = totalDaysInCurrentMonth;
    }

    public int getToday() {
        return today;
    }

    public void setToday(int today) {
        this.today = today;
    }


    public Label getMonthYearHeader() {
        return monthYearHeader;
    }

    public void setMonthYearHeader(Label monthYearHeader) {
        this.monthYearHeader = monthYearHeader;
    }


    public Label[] getDayLabels() {
        return dayLabels;
    }

    public void setDayLabels(Label[] dayLabels) {
        this.dayLabels = dayLabels;
    }

    public Label[] getAppointments() {
        return appointments;
    }

    public void setAppointments(Label[] appointments) {
        this.appointments = appointments;
    }

    public boolean isAppointment() {
        return isAppointment;
    }

    public void setAppointment(boolean appointment) {
        isAppointment = appointment;
    }

    public Circle getIsAppointmentCircle() {
        return isAppointmentCircle;
    }

    public void setIsAppointmentCircle(Circle isAppointmentCircle) {
        this.isAppointmentCircle = isAppointmentCircle;
    }
}
