package MVC.model;

import DAO.POJO.Appointment;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;

public class WeeklyViewModel {
    // Table Parts
    private TableView<TableRow> table;
    private TableRow<TableColumn> row;
    private TableColumn<TableRow, String> column;

    // TableView Label data
    private String weekDayName;
    private int weekDate;

    // Appointment data
    private ObservableList<Appointment> appointments;
    private ObservableList<Appointment> appointmentsToday;
    private Appointment singleAppointment;

    public WeeklyViewModel(TableView table, TableRow row, TableColumn column, String weekDayName, int weekDate,
                           ObservableList<Appointment> appointments, ObservableList<Appointment> appointmentsToday,
                           Appointment singleAppointment) {
        this.table = table;
        this.row = row;
        this.column = column;
        this.weekDayName = weekDayName;
        this.weekDate = weekDate;
        this.appointments = appointments;
        this.appointmentsToday = appointmentsToday;
        this.singleAppointment = singleAppointment;
    }

    public TableView getTable() {
        return table;
    }

    public void setTable(TableView table) {
        this.table = table;
    }

    public TableRow getRow() {
        return row;
    }

    public void setRow(TableRow row) {
        this.row = row;
    }

    public TableColumn getColumn() {
        return column;
    }

    public void setColumn(TableColumn column) {
        this.column = column;
    }

    public String getWeekDayName() {
        return weekDayName;
    }

    public void setWeekDayName(String weekDayName) {
        this.weekDayName = weekDayName;
    }

    public int getWeekDate() {
        return weekDate;
    }

    public void setWeekDate(int weekDate) {
        this.weekDate = weekDate;
    }

    public ObservableList<Appointment> getAppointments() {
        return appointments;
    }

    public void setAppointments(ObservableList<Appointment> appointments) {
        this.appointments = appointments;
    }

    public ObservableList<Appointment> getAppointmentsToday() {
        return appointmentsToday;
    }

    public void setAppointmentsToday(ObservableList<Appointment> appointmentsToday) {
        this.appointmentsToday = appointmentsToday;
    }

    public Appointment getSingleAppointment() {
        return singleAppointment;
    }

    public void setSingleAppointment(Appointment singleAppointment) {
        this.singleAppointment = singleAppointment;
    }
}
