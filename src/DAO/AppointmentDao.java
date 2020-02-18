package DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointment;
import utils.DBUtils;
import utils.DateTimeUtils;
import utils.QueryUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Calendar;

public class AppointmentDao {
    // TODO: TEST APPOINTMENT CLASS
    // flag for active XXX
    private static boolean activeBool;
    private static String sqlStatement;

    // methods for dynamic creation of TableViews and args for DaoMethods.add()
    public static ObservableList<String> getAppointmentColumns() throws SQLException, ClassNotFoundException {
        sqlStatement = "SELECT * FROM appointment";
        try {
            return DAO.getColumnNames(sqlStatement);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ObservableList<String> getAppointmentColumnValues(String query) throws SQLException, ClassNotFoundException {
        sqlStatement = "SELECT * FROM appointment";

        try {
            return DAO.getColumnValues(sqlStatement);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    // get, update, delete, add
    public static Appointment getAppointment(int appointmentId) throws ClassNotFoundException, SQLException, ParseException {
        String sqlStatement = "select * FROM appointment WHERE appointmentId  = '" + appointmentId + "'";

        Appointment appointmentResult;
        ResultSet result = DAO.getResultSet(sqlStatement);
        while (result.next()) {
            int appointmnetIdG = result.getInt("appointmentId");
            int customerId  = result.getInt("customerId");
            int userId = result.getInt("userId");
            String title = result.getString("title");
            String description = result.getString("description");
            String location = result.getString("location");
            String contact = result.getString("contact");
            String type = result.getString("type");
            String url = result.getString("url");
            String start = result.getString("start");
            String end = result.getString("end");

            String createDate = result.getString("createDate");
            String createdBy = result.getString("createdBy");
            String lastUpdate = result.getString("lastUpdate");
            String lastUpdateby = result.getString("lastUpdateBy");

            Calendar startCalendar = DateTimeUtils.stringToCalendar(start);
            Calendar endCalendar = DateTimeUtils.stringToCalendar(end);
            Calendar createDateCalendar = DateTimeUtils.stringToCalendar(createDate);
            Calendar lastUpdateCalendar = DateTimeUtils.stringToCalendar(lastUpdate);
            appointmentResult = new Appointment(appointmnetIdG, customerId, userId, title, description, location, contact, type, url, startCalendar, endCalendar, createDateCalendar, createdBy, lastUpdateCalendar, lastUpdateby);
            return appointmentResult;
        }
        DBUtils.closeConnection();
        return null;
    }

    public static ObservableList<Appointment> getAllAppointments() throws ClassNotFoundException, SQLException, ParseException {
        sqlStatement = "select * from appointments";

        ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();
        ResultSet result = DAO.getResultSet(sqlStatement);
        while (result.next()) {
            int appointmnetIdG = result.getInt("appointmentId");
            int customerId  = result.getInt("customerId");
            int userId = result.getInt("userId");
            String title = result.getString("title");
            String description = result.getString("description");
            String location = result.getString("location");
            String contact = result.getString("contact");
            String type = result.getString("type");
            String url = result.getString("url");
            String start = result.getString("start");
            String end = result.getString("end");

            String createDate = result.getString("createDate");
            String createdBy = result.getString("createdBy");
            String lastUpdate = result.getString("lastUpdate");
            String lastUpdateby = result.getString("lastUpdateBy");

            Calendar startCalendar = DateTimeUtils.stringToCalendar(start);
            Calendar endCalendar = DateTimeUtils.stringToCalendar(end);
            Calendar createDateCalendar = DateTimeUtils.stringToCalendar(createDate);
            Calendar lastUpdateCalendar = DateTimeUtils.stringToCalendar(lastUpdate);
            Appointment a = new Appointment(appointmnetIdG, customerId, userId, title, description, location, contact, type, url, startCalendar, endCalendar, createDateCalendar, createdBy, lastUpdateCalendar, lastUpdateby);
            allAppointments.add(a);
        }
        DBUtils.closeConnection();
        return allAppointments;
    }

    public static ObservableList<Appointment> getAppointmentsWithinMonth(int month) throws ClassNotFoundException, SQLException, ParseException {
        switch (month) {
            case 1:
                sqlStatement = "SELECT * FROM appointment WHERE start >= '2020-01-01 00:00:00' AND start <= '2020-01-30 12:59:59'";
                break;
            case 2:
                sqlStatement = "SELECT * FROM appointment WHERE start >= '2019-01-01 00:00:00' AND start <= '2020-02-31 12:59:59'";
                break;
            case 3:
                sqlStatement = "SELECT * FROM appointment WHERE start >= '2020-02-01' AND start <= '2020-02-30'";
                break;
            case 4:
                sqlStatement = "SELECT * FROM appointment WHERE start >= '2020-02-01' AND start <= '2020-02-30'";
                break;
            case 5:
                sqlStatement = "SELECT * FROM appointment WHERE start >= '2020-02-01' AND start <= '2020-02-30'";
                break;
            case 6:
                sqlStatement = "SELECT * FROM appointment WHERE start >= '2020-02-01' AND start <= '2020-02-30'";
                break;
            case 7:
                sqlStatement = "SELECT * FROM appointment WHERE start >= '2020-02-01' AND start <= '2020-02-30'";
                break;
            case 8:
                sqlStatement = "SELECT * FROM appointment WHERE start >= '2020-02-01' AND start <= '2020-02-30'";
                break;
            case 9:
                sqlStatement = "SELECT * FROM appointment WHERE start >= '2020-02-01' AND start <= '2020-02-30'";
                break;
            case 10:
                sqlStatement = "SELECT * FROM appointment WHERE start >= '2020-02-01' AND start <= '2020-02-30'";
                break;
            case 11:
                sqlStatement = "SELECT * FROM appointment WHERE start >= '2020-02-01' AND start <= '2020-02-30'";
                break;
            case 12:
                sqlStatement = "SELECT * FROM appointment WHERE start >= '2020-02-01' AND start <= '2020-02-30'";
                break;
            default:
                break;
        }
        ObservableList<Appointment> monthAppointments = FXCollections.observableArrayList();
        ResultSet result = null;
        try {
            result = DAO.getResultSet(sqlStatement);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        assert result != null;
        while (result.next()) {
            int appointmnetIdG = result.getInt("appointmentId");
            int customerId = result.getInt("customerId");
            int userId = result.getInt("userId");
            String title = result.getString("title");
            String description = result.getString("description");
            String location = result.getString("location");
            String contact = result.getString("contact");
            String type = result.getString("type");
            String url = result.getString("url");
            String start = result.getString("start");
            String end = result.getString("end");

            String createDate = result.getString("createDate");
            String createdBy = result.getString("createdBy");
            String lastUpdate = result.getString("lastUpdate");
            String lastUpdateby = result.getString("lastUpdateBy");

            Calendar startCalendar = DateTimeUtils.stringToCalendar(start);
            Calendar endCalendar = DateTimeUtils.stringToCalendar(end);
            Calendar createDateCalendar = DateTimeUtils.stringToCalendar(createDate);
            Calendar lastUpdateCalendar = DateTimeUtils.stringToCalendar(lastUpdate);
            Appointment a = new Appointment(appointmnetIdG, customerId, userId, title, description, location, contact, type, url, startCalendar, endCalendar, createDateCalendar, createdBy, lastUpdateCalendar, lastUpdateby);
            monthAppointments.add(a);
//            Calendar startDate = a.getStart();
//            int startMonth = startDate.get(Calendar.DAY_OF_MONTH);
//            int startDay = startDate.get(Calendar.DAY_OF_WEEK_IN_MONTH);
//            System.out.println("a start: " + startMonth + startDay);
        }
        return monthAppointments;
    }

    public static void updateAppointment(String updateCol, String setColValue, int rowId) throws ClassNotFoundException {
        try {
            // UPDATE `table_name` SET `column_name` = `new_value' [WHERE condition];
            // TODO: check colVal type....if int without single quotes, else with single quotes (like below)
            sqlStatement = "UPDATE appointment SET " + updateCol + " = '" + setColValue + "' WHERE appointmentId = " + rowId;
            DBUtils.startConnection();
            QueryUtils.createQuery(sqlStatement);
            ResultSet result = QueryUtils.getResult();
        } catch (ClassNotFoundException e) {
            System.out.println("ApptDao UPDATE CLASS NOT FOUND");
            e.getException();
        }
    }

    public void deleteAppointment(int rowId) {
        // Todo: POPUP "Are you sure? This is permanent."
        //DELETE FROM `table_name` [WHERE condition];
        sqlStatement = "DELETE FROM appointment WHERE appointmentId = " + rowId;
    }

    public static void addAppointment(int appointmentId, int customerId, int userId, String title, String description, String location, String contact, String type, String url, String startDate, String endDate,  String createdBy,String lastUpdate, String lastUpdateBy) throws ClassNotFoundException {
        // INSERT INTO `table_name`(column_1,column_2,...) VALUES (value_1,value_2,...);
        sqlStatement = "INSERT INTO appointment(appointmentId, customerId, userId, title, description, location, contact, type, url, start, end, createDate, createdBy, lastUpdate, lastUpdatedBy) " +
                "VALUES (" + appointmentId + ", " + customerId + ", " +  userId + ", '" + title + "' , '" + description + "' , '" + location + "' , '" + contact + "' , '" + type + "' , '" + url + "' , " + startDate + ", " + endDate + ", '" + createdBy + "' , " + lastUpdate + ", '" + lastUpdateBy + "')";
        System.out.println(sqlStatement);
        ResultSet result = DAO.getResultSet(sqlStatement);
    }
}
