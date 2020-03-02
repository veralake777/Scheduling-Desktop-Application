package DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import DAO.POJO.User;
import utils.Database.DBUtils;
import utils.Database.QueryUtils;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;

public abstract class DAO {
    static String sqlStatement;
    private static boolean activeBool = false;
    ObservableList<String> columnNames;
    ObservableList<String> columnValues;

    static ResultSet getResultSet(String query) throws ClassNotFoundException {
        sqlStatement = query;
        DBUtils.startConnection();
        //  String sqlStatement="select FROM address";
        QueryUtils.createQuery(sqlStatement);
//        User userResult;
        return QueryUtils.getResult();
    };

    static ObservableList<String> getColumnValues(String query) throws SQLException, ClassNotFoundException {
        ResultSet rs = null;
        try {
            rs = getResultSet(query);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        ObservableList<String> columnNames = getColumnNames(query);
        ObservableList<String> valuesList = FXCollections.observableArrayList( new ArrayList<String>());

        int i = 0;
        assert rs != null;
        if(!rs.wasNull()){
            while (rs.next()) {
                System.out.println("IN WHILE LOOP");
                i++;
                String col_name = columnNames.get(i);
                // TODO: this is returning the column name instead of the value, why?
                try{
                    valuesList.add(rs.getString(col_name));
                    System.out.println(rs.getString(col_name));
                } catch(Throwable e) {
                    valuesList.add(String.valueOf(rs.getInt(col_name)));
                }
                if (rs.getString("active").equals("1")) {
                    activeBool = true;
                }
            }
        }else {
            System.out.println("RESULT SET IS EMPTY");
        }
        return valuesList;
    };

    static ObservableList<String> getColumnNames(String query) throws SQLException, ClassNotFoundException {
        // builds STRING columnNames for the row to be passed to getColumnValues
        ObservableList<String> columnNames = FXCollections.observableArrayList( new ArrayList<String>());
        ResultSet rs = null;
        try {
            rs = getResultSet(query);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        assert rs != null;
        ResultSetMetaData columns = rs.getMetaData();
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

    // CRUD methods
    abstract void addRow();
    abstract Object getRow() throws ClassNotFoundException, SQLException, ParseException;
    abstract ObservableList<User> getAllRows() throws ClassNotFoundException, SQLException, ParseException;
    abstract void updateRow();
    abstract void updateAllRows();
    abstract void deleteRow();
}
