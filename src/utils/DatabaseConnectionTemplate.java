/* Fill in your jdbc database information.
* Dependency: Maven Library mysql:mysql-connector-java:5.1.40
*   Read more about this on Stack Overflow -> source: https://stackoverflow.com/questions/30651830/use-jdbc-mysql-connector-in-intellij-idea
*/

package utils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static java.sql.DriverManager.getConnection;

public class DatabaseConnectionTemplate {
    // JDBC URL parts
    private static final String protocol = "your apps protocol name";
    private static final String vendorName = ":your apps vendor name:";
    private static final String ipAddress = "//ipaddress/dbname";

    // JDBC Url
    private static final String jdbcURL = protocol + vendorName + ipAddress;

    // Driver Interface reference
    private static final String MYSQLJDBCdriver = "ex: com.mysql.jdbc.Driver";
    private static Connection conn = null;

    // User Name and Password
    private static final String userName = "your db username"; // Username
    private static final String password = "your db password"; // Password

    // DB Connection
    public static Connection startConnection() throws ClassNotFoundException {
        // find MYSQL JDBC Driver
        try {
            Class.forName(MYSQLJDBCdriver);
            conn = (Connection) getConnection(jdbcURL, userName, password);
            System.out.println("Connection was successful!");
        }catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    public static void closeConnection() {
        try {
            conn.close();
            System.out.println("Connection closed!");
        } catch (SQLException e) {
            System.out.println("Cannot close connection:");
            e.printStackTrace();
        }
    }
}

