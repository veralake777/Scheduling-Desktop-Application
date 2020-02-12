package utils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Queries {

    private static ResultSet result;
    // data source
    private static Statement statement;

    static {
        try {
            statement = DBUtils.conn.createStatement();
        } catch (SQLException e) {
            System.out.println("SQL EXCEPTION IN QUERY: ");
            e.printStackTrace();
        }
    }

    public static void createQuery(String q){
        try{
            if(q.toLowerCase().startsWith("select"))
                result=statement.executeQuery(q);
            if(q.toLowerCase().startsWith("delete")|| q.toLowerCase().startsWith("insert")|| q.toLowerCase().startsWith("update"))
                statement.executeUpdate(q);
        }
        catch(Exception ex){
            System.out.println("Error: "+ex.getMessage());
        }
    }

    public static ResultSet getResult(){
        return result;
    }

}
