package DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import DAO.POJO.Customer;
import utils.Database.DBUtils;
import utils.DateTime.DateTimeUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Calendar;

public class CustomerDao {
    private static String sqlStatement;
    private static boolean activeBool = false;

    public static ObservableList<String> getCustomerColumns(String query) throws SQLException, ClassNotFoundException {
        try {
            return DAO.getColumnNames(query);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static ObservableList<String> getCustomerColumnValues(String query) throws SQLException, ClassNotFoundException {
        try {
            return DAO.getColumnValues(query);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }

    // get, update, delete, add
    public static Customer getCustomer(int customerId) throws ClassNotFoundException, SQLException, ParseException {
        sqlStatement = "SELECT * FROM customer WHERE customerId = " + customerId;
        Customer customerResult;
        ResultSet result = null;
        try {
            result = DAO.getResultSet(sqlStatement);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        assert result != null;
        while (result.next()) {
            int customerIdG = result.getInt("customerId");
            String customerName = result.getString("customerName");
            int addressId = result.getInt("addressId");
            int active = result.getInt("active");
            if (active == 1) {
                activeBool = true;
            }
            String createDate = result.getString("createDate");
            String createdBy = result.getString("createdBy");
            String lastUpdate = result.getString("lastUpdate");
            String lastUpdateby = result.getString("lastUpdateBy");
            Calendar createDateCalendar = DateTimeUtils.stringToCalendar(createDate);
            Calendar lastUpdateCalendar = DateTimeUtils.stringToCalendar(lastUpdate);
            customerResult = new Customer(customerIdG, customerName, addressId, activeBool, createDateCalendar, createdBy, lastUpdateCalendar, lastUpdateby);
            return customerResult;
        }
        DBUtils.closeConnection();
        return null;
    };

    public static ObservableList<Customer> getAllCustomers() throws ClassNotFoundException, SQLException, ParseException {
        ObservableList<Customer> allCustomers = FXCollections.observableArrayList();
        sqlStatement = "select cust.customerId, cust.customerName, cust.active, a.address, a.address2, c.city, count.country " +
                "from customer cust " +
                "inner join address a " +
                "on cust.addressId = a.addressId " +
                "inner join city c " +
                "on a.cityId = c.cityId " +
                "inner join country count " +
                "on c.countryId = count.countryId";

        ResultSet result = null;
        try {
            result = DAO.getResultSet(sqlStatement);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        assert result != null;
        while (result.next()) {
            int customerIdG = result.getInt("customerId");
            String customerName = result.getString("customerName");
            int addressId = result.getInt("addressId");
            int active = result.getInt("active");
            if (active == 1) {
                activeBool = true;
            }
            String createDate = result.getString("createDate");
            String createdBy = result.getString("createdBy");
            String lastUpdate = result.getString("lastUpdate");
            String lastUpdateby = result.getString("lastUpdateBy");
            Calendar createDateCalendar = DateTimeUtils.stringToCalendar(createDate);
            Calendar lastUpdateCalendar = DateTimeUtils.stringToCalendar(lastUpdate);
            Customer customerResult = new Customer(customerIdG, customerName, addressId, activeBool, createDateCalendar, createdBy, lastUpdateCalendar, lastUpdateby);
            allCustomers.add(customerResult);
        }
        DBUtils.closeConnection();
        return allCustomers;
    };
    public static void updateCustomer(String updateCol, String setColValue, int rowId) throws ClassNotFoundException, SQLException, ParseException {
        try {
            // UPDATE `table_name` SET `column_name` = `new_value' [WHERE condition];
            sqlStatement = "UPDATE customer SET " + updateCol + " = '" + setColValue + "' WHERE customerId = " + rowId;
            ResultSet result = DAO.getResultSet(sqlStatement);
        } catch (ClassNotFoundException e) {
            System.out.println("CustomerDoa UPDATE CLASS NOT FOUND");
            e.getException();
        }
    };
    public static void deleteCustomer(int rowId) throws ClassNotFoundException {
        // Todo: POPUP "Are you sure? This is permanent."
        //DELETE FROM `table_name` [WHERE condition];
        sqlStatement = "DELETE FROM customer WHERE customerId = " + rowId;
        try {
            ResultSet result = DAO.getResultSet(sqlStatement);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        DBUtils.closeConnection();
    };
    public static void addCustomer(int customerId, String customerName, int addressId, int active, String createDate, String createdBy,String lastUpdate, String lastUpdateBy) throws ClassNotFoundException {
        // INSERT INTO `table_name`(column_1,column_2,...) VALUES (value_1,value_2,...);
        sqlStatement = "INSERT INTO customer(customerId, customerName, addressId, active, createDate, createdBy, lastUpdate, lastUpdateBy) " +
                "VALUES (" + customerId + " , '" + customerName + "' , '" + addressId + "' , " + active + "," + createDate + ", '" + createdBy + "' , " +lastUpdate + ", '" + lastUpdateBy + "')";
        try {
            ResultSet result = DAO.getResultSet(sqlStatement);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}
