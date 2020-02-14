package DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Country;
import utils.DBUtils;
import utils.DateTimeUtils;
import utils.QueryUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Calendar;

import static utils.QueryUtils.getResult;

public class CountryDao {
    // flag for active country
    private static boolean activeBool;
    private static String sqlStatement;

    // methods for dynamic creation of TableViews and args for DaoMethods.add()
    public static ObservableList<String> getCountryColumns(ResultSet rs) throws SQLException {
        return DAO.getColumnNames(rs);
    }

    public static ObservableList<String> getCountryColumnValues(ResultSet rs) throws SQLException {
        return DAO.getColumnValues(rs);
    }

    // get, update, delete, add
    public static Country getCountry(int countryId) throws ClassNotFoundException, SQLException, ParseException {
        DBUtils.startConnection();
        String sqlStatement = "select * FROM country WHERE countryId  = '" + countryId + "'";
        QueryUtils.createQuery(sqlStatement);
        Country countryResult;
        ResultSet result = getResult();
        while (result.next()) {
            int countryid = result.getInt("countryId");
            String countryName1 = result.getString("country");
            String createDate = result.getString("createDate");
            String createdBy = result.getString("createdBy");
            String lastUpdate = result.getString("lastUpdate");
            String lastUpdateby = result.getString("lastUpdateBy");
            Calendar createDateCalendar = DateTimeUtils.stringToCalendar(createDate);
            Calendar lastUpdateCalendar = DateTimeUtils.stringToCalendar(lastUpdate);
            countryResult = new Country(countryid, countryName1, createDateCalendar, createdBy, lastUpdateCalendar, lastUpdateby);
            return countryResult;
        }
        DBUtils.closeConnection();
        return null;
    }

    public static ObservableList<Country> getAllCountries() throws ClassNotFoundException, SQLException, ParseException {
        ObservableList<Country> allCountries = FXCollections.observableArrayList();
        DBUtils.startConnection();
        String sqlStatement = "select * from country";
        QueryUtils.createQuery(sqlStatement);
        ResultSet result = QueryUtils.getResult();
        while (result.next()) {
            int countryIdG = result.getInt("countryId");
            String countryNameG = result.getString("country");
            String createDate = result.getString("createDate");
            String createdBy = result.getString("createdBy");
            String lastUpdate = result.getString("lastUpdate");
            String lastUpdateby = result.getString("lastUpdateBy");
            Calendar createDateCalendar = DateTimeUtils.stringToCalendar(createDate);
            Calendar lastUpdateCalendar = DateTimeUtils.stringToCalendar(lastUpdate);
            //   s(int addressId, String address, String address2, int cityId, String postalCode, String phone, Calendar createDate, String createdBy, Calendar lastUpdate, String lastUpdateBy)
            Country countryResult = new Country(countryIdG, countryNameG, createDateCalendar, createdBy, lastUpdateCalendar, lastUpdateby);
            allCountries.add(countryResult);
        }
        DBUtils.closeConnection();
        return allCountries;
    }

    public static void updateCountry(String updateCol, String setColValue, int rowId) throws ClassNotFoundException {
        try {
            // UPDATE `table_name` SET `column_name` = `new_value' [WHERE condition];
            // TODO: check colVal type....if int without single quotes, else with single quotes (like below)
            sqlStatement = "UPDATE country SET " + updateCol + " = '" + setColValue + "' WHERE countryId = " + rowId;
            DBUtils.startConnection();
            QueryUtils.createQuery(sqlStatement);
            ResultSet result = QueryUtils.getResult();
        } catch (ClassNotFoundException e) {
            System.out.println("CountryDao UPDATE CLASS NOT FOUND");
            e.getException();
        }
    }

    public void deleteCountry(int rowId) {
        // Todo: POPUP "Are you sure? This is permanent."
        //DELETE FROM `table_name` [WHERE condition];
        sqlStatement = "DELETE FROM country WHERE countryId = " + rowId;
    }

    public static void addCountry(int countryId, String countryName, String createDate, String createdBy,String lastUpdate, String lastUpdateBy) throws ClassNotFoundException {
        // INSERT INTO `table_name`(column_1,column_2,...) VALUES (value_1,value_2,...);
        sqlStatement = "INSERT INTO country(countryId, country, createDate, createdBy, lastUpdate, lastUpdateBy) " +
                "VALUES (" + countryId + " , '" + countryName + "' , " + createDate + ", '" + createdBy + "' , " +lastUpdate + ", '" + lastUpdateBy + "')";
        System.out.println(sqlStatement);
        DBUtils.startConnection();
        QueryUtils.createQuery(sqlStatement);
        ResultSet result = QueryUtils.getResult();
    }
}
