package utils.Database;

import com.mysql.jdbc.PreparedStatement;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;

public class DaoMethods {
    private String query;
    private Connection connection;
    PreparedStatement st;

    /**
     * All classes have the following columns in common
     *      createDate, createdBy, lastUpdate, lastUpdateBy
     *
     * @param table
     * @param idColumnName
     * @param id
     *
     *
     */
    public void updateDatesAndUser(String table, String idColumnName, int id) throws SQLException, ClassNotFoundException {
        // (1) connect to database
        DBUtils.startConnection();
        // (2) get Calendar instance
        Calendar calendar = Calendar.getInstance();

        // (3) create a java sql date object we want to insert
        // format "YYYY-MM-DD HH:mm:ss"
        Date currentDateAndTime = new java.sql.Date(calendar.getTime().getTime());

        // (4) create our date insert statement
        query =
                "UPDATE (?)" +
                        "SET" +
                        "createDate = (?), " +
                        "createdBy = (?), " +
                        "lastUpdate = (?), " +
                        "lastUpdateBy) = (?) " +
                        "WHERE (?) = (?)";

        connection = DBUtils.getConn(); // get connection
        PreparedStatement st = (PreparedStatement) connection.prepareStatement(query); // create statement

        // set statement (?) values
        st.setString(1, table);
        st.setDate(2, (java.sql.Date) currentDateAndTime);
        // TODO get current user
        st.setString(3, "test");
        // TODO get previous date
        st.setDate(4, (java.sql.Date) currentDateAndTime);
        // TODO get current user
        st.setString(5, "test");
        st.setString(6, idColumnName);
        st.setInt(7, id);

        // execute statement
        st.executeUpdate();

        // close statement
        st.close();

        // (5) close database connection
        DBUtils.closeConnection();
    }

    public void updateRow(String updateTable, String setColumn, String setValue, String whereColumn, String whereValue) throws SQLException, ClassNotFoundException {
        // (1) connect to database
        DBUtils.startConnection();

        // (4) create our date insert statement
        query =
                "UPDATE (?)" +
                        "SET" +
                        "(?) = (?) " +
                        "WHERE (?) = (?)";

        connection = DBUtils.getConn(); // get connection
        PreparedStatement st = (PreparedStatement) connection.prepareStatement(query); // create statement

        // set statement (?) values
        st.setString(1, updateTable);
        st.setString(2, setColumn);
        st.setString(3, setValue);
        st.setString(4, whereColumn);
        if(whereColumn.toLowerCase().contains("id")){
            int whereValueIsId = Integer.parseInt(whereValue);
            st.setInt(5, whereValueIsId);
        } else {
            st.setString(5, whereValue);
        }

        // execute statement
        st.executeQuery();

        // close statement
        st.close();

        // (5) close database connection
        DBUtils.closeConnection();
    }

    public void updateRows(String updateTable, String setColumn, String setValue, String whereColumn, String[] whereValue) {
        // do something
//        int[] ids = { 1, 2, 3, 4 };
//        StringBuilder sql = new StringBuilder();
//        sql.append("select jedi_name from jedi where id in(");
//        for (int i = 0; i < ids.length; i++) {
//            sql.append("?");
//            if(i+1 < ids.length){
//                sql.append(",");
//            }
//        }
//        sql.append(")");
//        System.out.println(sql.toString());
//
//        try (Connection con = DriverManager.getConnection(...)) {
//
//            PreparedStatement stm = con.prepareStatement(sql.toString());
//            for(int i=0; i < ids.length; i++){
//                stm.setInt(i+1, ids[i]);
//            }
//
//            ResultSet rs = stm.executeQuery();
//            while (rs.next()) {
//                System.out.println(rs.getString("jedi_name"));
//            }
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
    }
}
