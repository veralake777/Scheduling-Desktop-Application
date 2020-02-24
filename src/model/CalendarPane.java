package model;

import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

public class CalendarPane {
    // Locale
    private Locale locale;

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

    public CalendarPane(Locale locale, Label[] dayLabels, Label[] appointments) {
        this.locale = locale;

        // Controller is dependent on GregorianCalendar
        this.calendar = new GregorianCalendar();
        this.currentMonth = this.calendar.get(Calendar.MONTH);
        this.currentYear = this.calendar.get(Calendar.YEAR);
        this.totalDaysInCurrentMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        this.today = calendar.get(Calendar.DAY_OF_MONTH);;
        this.currentMonthString = this.calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, this.locale);
        this.monthYearHeader = new Label(this.currentMonthString + " " + this.currentYear);
        this.dayLabels = dayLabels;
        this.appointments = appointments;
        this.isAppointment = false;
        this.isAppointmentCircle = null;
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

    public void setCalendar(Calendar calendar) {
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

    public void setIsAppointmentCircle(Circle isAppointmentCircle, Color color) {
        if (this.isAppointment()) {
            isAppointmentCircle.setFill(color);
            this.isAppointmentCircle = isAppointmentCircle;
        } else {
            this.isAppointmentCircle = null;
        }
    }


}
