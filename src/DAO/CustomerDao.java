package DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Customer;
import utils.DBUtils;
import utils.DaoMethods;
import utils.Queries;
import utils.TimeMethods;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Calendar;

import static utils.Queries.getResult;

public class CustomerDao {
    private static String sqlStatement;
    private static boolean activeBool = false;

    public static ObservableList<String> getCustomerColumns(ResultSet rs) throws SQLException {
        return DaoMethods.getColumnNames(rs);
    }

    public static ObservableList<String> getCustomerColumnValues(ResultSet rs) throws SQLException {
        return DaoMethods.getColumnValues(rs);
    }

    // get, update, delete, add
    public static Customer getCustomer(int customerId) throws ClassNotFoundException, SQLException, ParseException {
        DBUtils.startConnection();
        String sqlStatement = "select * FROM customer WHERE customerId  = " + customerId;
        Queries.createQuery(sqlStatement);
        Customer customerResult;
        ResultSet result = getResult();
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
            Calendar createDateCalendar = TimeMethods.stringToCalendar(createDate);
            Calendar lastUpdateCalendar = TimeMethods.stringToCalendar(lastUpdate);
            customerResult = new Customer(customerIdG, customerName, addressId, activeBool, createDateCalendar, createdBy, lastUpdateCalendar, lastUpdateby);
            return customerResult;
        }
        DBUtils.closeConnection();
        return null;
    };
    public static ObservableList<Customer> getAllCustomers() throws ClassNotFoundException, SQLException, ParseException {
        ObservableList<Customer> allCustomers = FXCollections.observableArrayList();
        DBUtils.startConnection();
        String sqlStatement = "select * from customer";
        Queries.createQuery(sqlStatement);
        ResultSet result = Queries.getResult();
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
            Calendar createDateCalendar = TimeMethods.stringToCalendar(createDate);
            Calendar lastUpdateCalendar = TimeMethods.stringToCalendar(lastUpdate);
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
            DBUtils.startConnection();
            Queries.createQuery(sqlStatement);
            ResultSet result = Queries.getResult();
        } catch (ClassNotFoundException e) {
            System.out.println("CustomerDoa UPDATE CLASS NOT FOUND");
            e.getException();
        }
    };
    public static void deleteCustomer(int rowId){
        // Todo: POPUP "Are you sure? This is permanent."
        //DELETE FROM `table_name` [WHERE condition];
        sqlStatement = "DELETE FROM customer WHERE customerId = " + rowId;
    };
    public static void addCustomer(int customerId, String customerName, int addressId, int active, String createDate, String createdBy,String lastUpdate, String lastUpdateBy) throws ClassNotFoundException {
        // INSERT INTO `table_name`(column_1,column_2,...) VALUES (value_1,value_2,...);
        sqlStatement = "INSERT INTO customer(customerId, customerName, addressId, active, createDate, createdBy, lastUpdate, lastUpdateBy) " +
                "VALUES (" + customerId + " , '" + customerName + "' , '" + addressId + "' , " + active + "," + createDate + ", '" + createdBy + "' , " +lastUpdate + ", '" + lastUpdateBy + "')";
        DBUtils.startConnection();
        Queries.createQuery(sqlStatement);
        ResultSet result = Queries.getResult();
    }

}
