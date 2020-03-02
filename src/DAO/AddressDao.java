package DAO;

import DAO.POJO.Address;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utils.Database.DBUtils;
import utils.Database.QueryUtils;
import utils.DateTime.DateTimeUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Calendar;

public class AddressDao {
    // flag for active address
    private static boolean activeBool;
    private static String sqlStatement;

    // methods for dynamic creation of TableViews and args for DaoMethods.add()
    public static ObservableList<String> getAddressColumns() throws SQLException, ClassNotFoundException {
        sqlStatement = "SELECT * FROM address";
        try {
            return DAO.getColumnNames(sqlStatement);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static ObservableList<String> getAddressColumnValues() throws SQLException, ClassNotFoundException {
        sqlStatement = "SELECT * FROM address";
        try {
            return DAO.getColumnValues(sqlStatement);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    // get, update, delete, add
    public static Address getAddress(int addressId) throws ClassNotFoundException, SQLException, ParseException {
        String sqlStatement = "select * FROM address WHERE addressId = '" + addressId + "'";
        Address addressResult;
        ResultSet result = DAO.getResultSet(sqlStatement);
        while (result.next()) {
            int addressIdG = result.getInt("addressId");
            String address1 = result.getString("address");
            String address2 = result.getString("address2");
            int cityId = result.getInt("cityId");
            String postalCode = result.getString("postalCode");
            String phone = result.getString("phone");

            String createDate = result.getString("createDate");
            String createdBy = result.getString("createdBy");
            String lastUpdate = result.getString("lastUpdate");
            String lastUpdateby = result.getString("lastUpdateBy");
            Calendar createDateCalendar = DateTimeUtils.stringToCalendar(createDate);

            Calendar lastUpdateCalendar = DateTimeUtils.stringToCalendar(lastUpdate);
            addressResult = new Address(addressIdG, address1, address2, cityId, postalCode, phone, createDateCalendar, createdBy, lastUpdateCalendar, lastUpdateby);
            return addressResult;
        }
        DBUtils.closeConnection();
        return null;
    }

    public static ObservableList<Address> getAllAddresses() throws ClassNotFoundException, SQLException, ParseException {
        ObservableList<Address> allAddresses = FXCollections.observableArrayList();
        DBUtils.startConnection();
        String sqlStatement = "select * from address";
        QueryUtils.createQuery(sqlStatement);
        ResultSet result = QueryUtils.getResult();
        while (result.next()) {
            int addressIdG = result.getInt("addressId");
            String address1 = result.getString("address");
            String address2 = result.getString("address2");
            int cityId = result.getInt("cityId");
            String postalCode = result.getString("postalCode");
            String phone = result.getString("phone");

            String createDate = result.getString("createDate");
            String createdBy = result.getString("createdBy");
            String lastUpdate = result.getString("lastUpdate");
            String lastUpdateby = result.getString("lastUpdateBy");
            Calendar createDateCalendar = DateTimeUtils.stringToCalendar(createDate);

            Calendar lastUpdateCalendar = DateTimeUtils.stringToCalendar(lastUpdate);
            Address a = new Address(addressIdG, address1, address2, cityId, postalCode, phone, createDateCalendar, createdBy, lastUpdateCalendar, lastUpdateby);
            allAddresses.add(a);
        }
        DBUtils.closeConnection();
        return allAddresses;
    }

    public static void updateAddress(String updateCol, String setColValue, int rowId) throws ClassNotFoundException {
        try {
            // UPDATE `table_name` SET `column_name` = `new_value' [WHERE condition];
            // TODO: check colVal type....if int without single quotes, else with single quotes (like below)
            sqlStatement = "UPDATE address SET " + updateCol + " = '" + setColValue + "' WHERE addressId = " + rowId;
            DBUtils.startConnection();
            QueryUtils.createQuery(sqlStatement);
            ResultSet result = QueryUtils.getResult();
        } catch (ClassNotFoundException e) {
            System.out.println("ADDRESSDAO UPDATE CLASS NOT FOUND");
            e.getException();
        }
    }

    public void deleteAddress(int rowId) {
        // Todo: POPUP "Are you sure? This is permanent."
        //DELETE FROM `table_name` [WHERE condition];
        sqlStatement = "DELETE FROM address WHERE addressId = " + rowId;
    }

    public static void addAddress(int addressId, String address1, String address2, int cityId, String postalCode, String phone, String createDate, String createdBy,String lastUpdate, String lastUpdateBy) throws ClassNotFoundException {
        // INSERT INTO `table_name`(column_1,column_2,...) VALUES (value_1,value_2,...);
        sqlStatement = "INSERT INTO address(addressId, address, address2, cityId, postalCode, phone, createDate, createdBy, lastUpdate, lastUpdateBy) " +
                "VALUES (" + addressId + " , '" + address1 + "' , '" + address2 + "' , " + cityId + " , '" + address2 + "' , " + postalCode + "' , '" + phone + "' , " + createDate + ", '" + createdBy + "' , " +lastUpdate + ", '" + lastUpdateBy + "')";
        DBUtils.startConnection();
        QueryUtils.createQuery(sqlStatement);
        ResultSet result = QueryUtils.getResult();
    }
}
