package DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.City;
import utils.DBUtils;
import utils.DateTimeUtils;
import utils.QueryUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Calendar;

import static utils.QueryUtils.getResult;

public class CityDao {
    // flag for active city
    private static boolean activeBool;
    private static String sqlStatement;

    // methods for dynamic creation of TableViews and args for DaoMethods.add()
    public static ObservableList<String> getCityColumns(ResultSet rs) throws SQLException {
        return DAO.getColumnNames(rs);
    }

    public static ObservableList<String> getCityColumnValues(ResultSet rs) throws SQLException {
        return DAO.getColumnValues(rs);
    }

    // get, update, delete, add
    public static City getCity(String city) throws ClassNotFoundException, SQLException, ParseException {
        DBUtils.startConnection();
        String sqlStatement = "select * FROM city WHERE city  = '" + city + "'";
        QueryUtils.createQuery(sqlStatement);
        City cityResult;
        ResultSet result = getResult();
        while (result.next()) {
            int cityIdG = result.getInt("cityId");
            String cityName = result.getString("city");
            int countryId = result.getInt("countryId");
            String createDate = result.getString("createDate");
            String createdBy = result.getString("createdBy");
            String lastUpdate = result.getString("lastUpdate");
            String lastUpdateby = result.getString("lastUpdateBy");
            Calendar createDateCalendar = DateTimeUtils.stringToCalendar(createDate);

            Calendar lastUpdateCalendar = DateTimeUtils.stringToCalendar(lastUpdate);
            cityResult = new City(cityIdG, cityName, countryId, createDateCalendar, createdBy, lastUpdateCalendar, lastUpdateby);
            return cityResult;
        }
        DBUtils.closeConnection();
        return null;
    }

    public static ObservableList<City> getAllcities() throws ClassNotFoundException, SQLException, ParseException {
        ObservableList<City> allCities = FXCollections.observableArrayList();
        DBUtils.startConnection();
        String sqlStatement = "select * from city";
        QueryUtils.createQuery(sqlStatement);
        ResultSet result = QueryUtils.getResult();
        while (result.next()) {
            int cityIdG = result.getInt("cityId");
            String cityNameG = result.getString("city");
            int countryId = result.getInt("countryId");
            String createDate = result.getString("createDate");
            String createdBy = result.getString("createdBy");
            String lastUpdate = result.getString("lastUpdate");
            String lastUpdateby = result.getString("lastUpdateBy");
            Calendar createDateCalendar = DateTimeUtils.stringToCalendar(createDate);
            Calendar lastUpdateCalendar = DateTimeUtils.stringToCalendar(lastUpdate);
            //   s(int addressId, String address, String address2, int cityId, String postalCode, String phone, Calendar createDate, String createdBy, Calendar lastUpdate, String lastUpdateBy)
            City cityResult = new City(cityIdG, cityNameG, countryId, createDateCalendar, createdBy, lastUpdateCalendar, lastUpdateby);
            allCities.add(cityResult);
        }
        DBUtils.closeConnection();
        return allCities;
    }

    public static void updateCity(String updateCol, String setColValue, int rowId) throws ClassNotFoundException {
        try {
            // UPDATE `table_name` SET `column_name` = `new_value' [WHERE condition];
            // TODO: check colVal type....if int without single quotes, else with single quotes (like below)
            sqlStatement = "UPDATE city SET " + updateCol + " = '" + setColValue + "' WHERE cityId = " + rowId;
            DBUtils.startConnection();
            QueryUtils.createQuery(sqlStatement);
            ResultSet result = QueryUtils.getResult();
        } catch (ClassNotFoundException e) {
            System.out.println("CITYDao UPDATE CLASS NOT FOUND");
            e.getException();
        }
    }

    public void deleteCity(int rowId) {
        // Todo: POPUP "Are you sure? This is permanent."
        //DELETE FROM `table_name` [WHERE condition];
        sqlStatement = "DELETE FROM city WHERE cityId = " + rowId;
    }

    public static void addCity(int cityId, String cityName, int countryId, String createDate, String createdBy,String lastUpdate, String lastUpdateBy) throws ClassNotFoundException {
        // INSERT INTO `table_name`(column_1,column_2,...) VALUES (value_1,value_2,...);
        sqlStatement = "INSERT INTO city(cityId, city, countryId, createDate, createdBy, lastUpdate, lastUpdateBy) " +
                "VALUES (" + cityId + " , '" + cityName + "' , " + countryId + "," + createDate + ", '" + createdBy + "' , " +lastUpdate + ", '" + lastUpdateBy + "')";
        System.out.println(sqlStatement);
        DBUtils.startConnection();
        QueryUtils.createQuery(sqlStatement);
        ResultSet result = QueryUtils.getResult();
    }
}
