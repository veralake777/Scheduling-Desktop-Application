package DAO;


import model.User;
import utils.DBUtils;
import utils.Queries;
import utils.TimeMethods;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;

import static utils.Queries.createQuery;
import static utils.Queries.getResult;

public class UserDao {
    // flag for active user
    private static boolean activeBool;
    // get single User
    public static User getUser(String userName) throws SQLException, Exception{
        // type is name or phone, value is the name or the phone #
        DBUtils.startConnection();
        String sqlStatement="select * FROM user WHERE userName  = '" + userName+ "'";
        System.out.print(sqlStatement);
        //  String sqlStatement="select FROM address";
        Queries.createQuery(sqlStatement);
        User userResult;
        ResultSet result= getResult();
        System.out.println();
        while(result.next()){
            int userid=result.getInt("userid");
//            System.out.println(userid);
            String userName1 =result.getString("userName");
//            System.out.println(userName1);
            String password=result.getString("password");
//            System.out.println(password);
            int active=result.getInt("active");
//            System.out.println(active);
            if(active ==1){ activeBool = true;}
//            System.out.println(activeBool);
            String createDate=result.getString("createDate");
//            System.out.println(createDate);
            String createdBy=result.getString("createdBy");
//            System.out.println(createdBy);
            String lastUpdate=result.getString("lastUpdate");
//            System.out.println(lastUpdate);
            String lastUpdateby=result.getString("lastUpdateBy");
//            System.out.println(lastUpdateby);
            Calendar createDateCalendar=TimeMethods.stringToCalendar(createDate);
//            System.out.println(createDateCalendar);
            Calendar lastUpdateCalendar=TimeMethods.stringToCalendar(lastUpdate);
//            System.out.println(lastUpdateCalendar);
            //   s(int addressId, String address, String address2, int cityId, String postalCode, String phone, Calendar createDate, String createdBy, Calendar lastUpdate, String lastUpdateBy)
            userResult= new User(userid, userName1, password, activeBool, createDateCalendar, createdBy, lastUpdateCalendar, lastUpdateby);
            return userResult;
        }
        DBUtils.closeConnection();
        return null;
    }



    // validate user input
    public static boolean validateUserInput(String selectRow, String fromTable, String whereCol, String isValue) throws SQLException, ClassNotFoundException {

        String query = "SELECT " + selectRow + " FROM " + fromTable;
        createQuery(query);
        try (ResultSet rs = getResult()) {
            while (rs.next()) {
                if (rs.getString(whereCol).equals(isValue)) {
                    return true;
                }
            }} catch (SQLException e) {
            System.out.println(e.getErrorCode());
        }
        return false;
    }
}
