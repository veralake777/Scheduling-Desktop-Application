package DAO;

import javafx.collections.ObservableList;
import utils.DBUtils;
import utils.Queries;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;

public class DaoMethods {
    public static Object getRow(String name) throws ClassNotFoundException, SQLException, ParseException {
        return null;
    }

    public static ObservableList getAllRows() throws ClassNotFoundException, SQLException, ParseException{
        return null;
    };

    public static void updateRow(String tableName, String colName, String newValue, int rowId) throws ClassNotFoundException {
        // UPDATE `table_name` SET `column_name` = `new_value' [WHERE condition];
        try {
            DBUtils.startConnection();
            String sqlStatement = "UPDATE " + tableName + " SET " + colName + " = '" + newValue + "'" + " WHERE " + tableName + "Id = " + rowId;
            Queries.createQuery(sqlStatement);
            ResultSet result=Queries.getResult();
            System.out.print(result);
        }catch(ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
    };


    public void deleteRow(int rowId) // DELETE FROM `table_name` [WHERE condition];
    {

    }

    public void add(Object row) // INSERT INTO `table_name`(column_1,column_2,...) VALUES (value_1,value_2,...);
    {

    }
}
