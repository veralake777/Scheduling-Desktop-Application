package DAO;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.User;
import utils.DBUtils;
import utils.DaoMethods;
import utils.Queries;
import utils.TimeMethods;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Calendar;

import static utils.Queries.getResult;

//import utils.DaoMethods;


public class UserDao {
    // flag for active user
    private static boolean activeBool;
    private static String sqlStatement;


    // methods for dynamic creation of TableViews and args for DaoMethods.add()
    public static ObservableList<String> getUserColumns(ResultSet rs) throws SQLException {
        return DaoMethods.getColumnNames(rs);
    }

    public static ObservableList<String> getUserColumnValues(ResultSet rs) throws SQLException {
        return DaoMethods.getColumnValues(rs);
    }

    public static User getUser(String userName) throws ClassNotFoundException, SQLException, ParseException {
        DBUtils.startConnection();
        String sqlStatement = "select * FROM user WHERE userName  = '" + userName + "'";
        //  String sqlStatement="select FROM address";
        Queries.createQuery(sqlStatement);
        User userResult;
        ResultSet result = getResult();
        while (result.next()) {
            int userid = result.getInt("userId");
//            System.out.println(userid);
            String userName1 = result.getString("userName");
//            System.out.println(userName1);
            String password = result.getString("password");
//            System.out.println(password);
            int active = result.getInt("active");
//            System.out.println(active);
            if (active == 1) {
                activeBool = true;
            }
//            System.out.println(activeBool);
            String createDate = result.getString("createDate");
//            System.out.println(createDate);
            String createdBy = result.getString("createdBy");
//            System.out.println(createdBy);
            String lastUpdate = result.getString("lastUpdate");
//            System.out.println(lastUpdate);
            String lastUpdateby = result.getString("lastUpdateBy");
//            System.out.println(lastUpdateby);
            Calendar createDateCalendar = TimeMethods.stringToCalendar(createDate);
//            System.out.println(createDateCalendar);
            Calendar lastUpdateCalendar = TimeMethods.stringToCalendar(lastUpdate);
//            System.out.println(lastUpdateCalendar);
            //   s(int addressId, String address, String address2, int cityId, String postalCode, String phone, Calendar createDate, String createdBy, Calendar lastUpdate, String lastUpdateBy)
            userResult = new User(userid, userName1, password, activeBool, createDateCalendar, createdBy, lastUpdateCalendar, lastUpdateby);
            return userResult;
        }
        DBUtils.closeConnection();
        return null;
    }

    public static ObservableList<User> getAllUsers() throws ClassNotFoundException, SQLException, ParseException {
        ObservableList<User> allUsers = FXCollections.observableArrayList();
        DBUtils.startConnection();
        String sqlStatement = "select * from user";
        Queries.createQuery(sqlStatement);
        ResultSet result = Queries.getResult();
        while (result.next()) {
            int userid = result.getInt("userid");
            String userNameG = result.getString("userName");
            String password = result.getString("password");
            int active = result.getInt("active");
            if (active == 1) activeBool = true;
            String createDate = result.getString("createDate");
            String createdBy = result.getString("createdBy");
            String lastUpdate = result.getString("lastUpdate");
            String lastUpdateby = result.getString("lastUpdateBy");
            Calendar createDateCalendar = TimeMethods.stringToCalendar(createDate);
            Calendar lastUpdateCalendar = TimeMethods.stringToCalendar(lastUpdate);
            //   s(int addressId, String address, String address2, int cityId, String postalCode, String phone, Calendar createDate, String createdBy, Calendar lastUpdate, String lastUpdateBy)
            User userResult = new User(userid, userNameG, password, activeBool, createDateCalendar, createdBy, lastUpdateCalendar, lastUpdateby);
            allUsers.add(userResult);
            System.out.println(userResult);
        }
        DBUtils.closeConnection();
        return allUsers;
    }

    public static void updateUser(String updateCol, String setColValue, int rowId) throws ClassNotFoundException {
        try {
            // UPDATE `table_name` SET `column_name` = `new_value' [WHERE condition];
            sqlStatement = "UPDATE user SET " + updateCol + " = " + setColValue + " WHERE userId = " + rowId;
            DBUtils.startConnection();
            Queries.createQuery(sqlStatement);
            ResultSet result = Queries.getResult();
        } catch (ClassNotFoundException e) {
            System.out.println("UserDao UPDATE CLASS NOT FOUND");
            e.getException();
        }
    }

    public void deleteUser(int rowId) {
        // Todo: POPUP "Are you sure? This is permanent."
        //DELETE FROM `table_name` [WHERE condition];
        sqlStatement = "DELETE FROM user WHERE userId = " + rowId;
    }


    public static void addUser(int userId, String userName, String password, int active, String createDate, String createdBy,String lastUpdate, String lastUpdateBy) throws ClassNotFoundException {
        // INSERT INTO `table_name`(column_1,column_2,...) VALUES (value_1,value_2,...);
        sqlStatement = "INSERT INTO user(userId, userName, password, active, createDate, createdBy, lastUpdate, lastUpdateBy) " +
                "VALUES (" + userId + " , '" + userName + "' , '" + password + "' , " + active + "," + createDate + ", '" + createdBy + "' , " +lastUpdate + ", '" + lastUpdateBy + "')";
        System.out.println(sqlStatement);
        DBUtils.startConnection();
        Queries.createQuery(sqlStatement);
        ResultSet result = Queries.getResult();
    }
}
