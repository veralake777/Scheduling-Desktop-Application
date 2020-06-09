package utils.Database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class QueryUtils {

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

                try {
                    statement.executeUpdate(q);
                    System.out.println(statement.getResultSet().getInt("addressId"));
                } catch (SQLException e) {
                    System.out.println("ERROR in UPDATE");
                    e.printStackTrace();
                }
        }
        catch(Exception ex){
            System.out.println("Error: "+ex.getMessage());
        }
    }

    public static ResultSet getResult(){
        return result;
    }

//    public static void commitQuery() throws SQLException {
////        try {
////           statement.getConnection().commit();
////        } catch (SQLException e) {
//////            if(Locale.getDefault().getLanguage().equals("fr")) {
//////                ResourceBundle fr = JavaFxApplications.resources("resources/bundles/app_fr");
//////                String message = fr.getString(alertTitle);
//////            }
////            Alert alert = new Alert(Alert.AlertType.valueOf("ERROR"));
////            alert.setTitle("SQL Error");
////            alert.setContentText("Statement failed to commit to the database. Error code: " + e.getErrorCode());
////            alert.showAndWait();
////        }
//    }
}
