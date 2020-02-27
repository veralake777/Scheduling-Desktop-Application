package utils.Database;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;



public class DaoMethods {
//    // RESOURCE: https://www.tutorialspoint.com/java-resultsetmetadata-getcolumntype-method-with-example
//    private enum ColumnDataTypes {
//        ARRAY(2003),
//        BIG_INT(-5),
//        BINARY(-2),
//        BIT(-7),
//        BLOB(2004),
//        BOOLEAN(16),
//        CHAR(1),
//        CLOB(2005),
//        DATE(91),
//        DATALINK(70),
//        DECIMAL(3),
//        DISTINCT(3),
//        DOUBLE(8),
//        FLOAT(6),
//        INTEGER(4),
//        JAVAOBJECT(2000),
//        //    LONG(-16),
//        NCLOB(-15),
//        VARCHAR(12),
//        VARBINARY(-3),
//        TINYINT(-6),
//        TIMESTAMPTZ(2014),
//        TIMESTAMP(93),
//        TIME(92),
//        STRUCT(2002),
//        SQLXML(2009),
//        SMALLINT(5),
//        ROWID(-8),
//        REFCURSOR(2012),
//        REF(2006),
//        REAL(7),
//        NVARCHAR(-9),
//        NUMERIC(2),
//        NULL(0)
//        ;
//
//        private final int columnTypeCode;
//        private String name;
//        ColumnDataTypes(int i) {
//            this.columnTypeCode = i;
//        }
//
//        public int getColumnTypeCode() {
//            return columnTypeCode;
//        }
//    }
    private static String sqlStatement;
    private static boolean activeBool = false;
//
//
//    public static void updateRow(String tableName, String colName, String newValue, int rowId) throws ClassNotFoundException {
//        // UPDATE `table_name` SET `column_name` = `new_value' [WHERE condition];
//            try {
//            DBUtils.startConnection();
//            System.out.println("UPDATE CONNECTED");
//            sqlStatement = "UPDATE " + tableName + " SET " + colName + " = '" + newValue + "'" + " WHERE " + tableName + "Id = " + rowId;
//            System.out.println("UPDATE: " + sqlStatement);
//            Queries.createQuery(sqlStatement);
//            System.out.println("QUERY CREATED");
//            ResultSet result = Queries.getResult();
//            System.out.println("RESULT SET CREATED");
//            // get index of column
//            int colIndex = result;
//            System.out.println("update colindex:" + String.valueOf(colIndex));
//            String colTypeName = result.getString(colIndex);
//            System.out.println("update coltypename" + String.valueOf(colTypeName));
//        } catch (ClassNotFoundException | SQLException e) {
//            System.out.println(e.getMessage());
//        }
//    }
//
//    ;
//
//    public static void deleteRow(String tableName, String colName, String matchCondition) {
//        // check if colName is int
//        colName = colName.toLowerCase();
//        if (colName.contains("id")) {
//            int intCondition = Integer.parseInt(matchCondition);
//            sqlStatement = "DELETE FROM " + tableName + " WHERE " + colName + " = " + intCondition;
//        } else {
//            sqlStatement = "DELETE FROM " + tableName + " WHERE " + colName + " = " + matchCondition;
//        }
//        // DELETE FROM `table_name` [WHERE condition];
//        try {
//            DBUtils.startConnection();
//            Queries.createQuery(sqlStatement);
//            ResultSet result = Queries.getResult();
//            System.out.print(result);
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//
//    }
//

//
//
//    public static void add(Object row, String tableName, ObservableList<String> columns, ObservableList<String> values)
//    {
//        // INSERT INTO `table_name`(column_1,column_2,...) VALUES (value_1,value_2,...);
//        // build sqlstatement with ObserverableLists Columns and Values
//
//    }
}
