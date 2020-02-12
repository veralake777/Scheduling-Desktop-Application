package utils;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

public class DaoMethods {
    private static String sqlStatement;
    private static boolean activeBool = false;

    public static void updateRow(String tableName, String colName, String newValue, int rowId) throws ClassNotFoundException {
        // UPDATE `table_name` SET `column_name` = `new_value' [WHERE condition];
        try {
            DBUtils.startConnection();
            sqlStatement = "UPDATE " + tableName + " SET " + colName + " = '" + newValue + "'" + " WHERE " + tableName + "Id = " + rowId;
            Queries.createQuery(sqlStatement);
            ResultSet result = Queries.getResult();
            System.out.print(result);
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    ;

    public static void deleteRow(String tableName, String colName, String matchCondition) {
        // check if colName is int
        colName = colName.toLowerCase();
        if (colName.contains("id")) {
            int intCondition = Integer.parseInt(matchCondition);
            sqlStatement = "DELETE FROM " + tableName + " WHERE " + colName + " = " + intCondition;
        } else {
            sqlStatement = "DELETE FROM " + tableName + " WHERE " + colName + " = " + matchCondition;
        }
        // DELETE FROM `table_name` [WHERE condition];
        try {
            DBUtils.startConnection();
            Queries.createQuery(sqlStatement);
            ResultSet result = Queries.getResult();
            System.out.print(result);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    public static ObservableList<String> getColumnNames(ResultSet resultSet) throws SQLException {
        // builds STRING columnNames for the row to be passed to getColumnValues
        ObservableList<String> columnNames = FXCollections.observableArrayList( new ArrayList<String>());
        ResultSetMetaData columns = resultSet.getMetaData();
        int i = 0;
        while (i < columns.getColumnCount()) {
            i++;
            if(columns.getColumnLabel(i) != null) {
                System.out.print(columns.getColumnName(i) + "\t");
                columnNames.add(columns.getColumnName(i));
            } else {
                System.out.println("ADDING NULL COLUMN NAME");
                columnNames.add("null");
            }
        }
        return columnNames;
    }

    public static ObservableList<String> getColumnValues(ResultSet resultSet) throws SQLException {
        ObservableList<String> columnNames = getColumnNames(resultSet);
        ObservableList<String> valuesList = FXCollections.observableArrayList( new ArrayList<String>());
        int i = 0;
        if(!resultSet.wasNull()){
            while (resultSet.next()) {
                System.out.println("IN WHILE LOOP");
                i++;
                String col_name = columnNames.get(i);
                // TODO: this is returning the column name instead of the value, why?
                try{
                valuesList.add(resultSet.getString(col_name));
                    System.out.println(resultSet.getString(col_name));
                } catch(Throwable e) {
                    valuesList.add(String.valueOf(resultSet.getInt(col_name)));
                    System.out.println(String.valueOf(resultSet.getInt(col_name)));
                }
                if (resultSet.getString("active") == "1") {
                    activeBool = true;
                }
            }
        }else {
            System.out.println("RESULT SET IS EMPTY");
        }

        return valuesList;
    }


    public static void add(Object row, String tableName, ObservableList<String> columns, ObservableList<String> values)
    {
        // INSERT INTO `table_name`(column_1,column_2,...) VALUES (value_1,value_2,...);
        // build sqlstatement with ObserverableLists Columns and Values

    }
}
