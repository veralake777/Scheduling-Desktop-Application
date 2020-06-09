package utils;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import static java.sql.DriverManager.getConnection;

public class DBUtils {

    // initialize db Statement
    public static Statement db = null;

    // JDBC URL parts
    private static final String protocol = "jdbc";
    private static final String vendorName = ":mysql:";
    private static final String ipAddress = "//3.227.166.251/U05LVV";

    // JDBC Url
    private static final String jdbcURL = protocol + vendorName + ipAddress;

    // Driver Interface reference
    private static final String MYSQLJDBCdriver = "com.mysql.jdbc.Driver";
    static Connection conn;

    private static final String userName = "U05LVV"; // Username
    private static final String password = "53688536372"; // Password

    public static Connection getConn() {
        return conn;
    }

    // DB Connection
    public static void startConnection() throws ClassNotFoundException {
        // find MYSQL JDBC Driver
        try {
            Class.forName(MYSQLJDBCdriver);
            conn = getConnection(jdbcURL, userName, password);
            // It's faster when auto commit is off
            conn.setAutoCommit(true);
            db = conn.createStatement();
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }
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

    public static DataSource getMySQLDataSource() throws IOException {
        Properties props = new Properties();
        FileInputStream fis;
        MysqlDataSource mysqlDS = null;

        try {
            fis = new FileInputStream("src/utils/db.properties");
            props.load(fis);
            mysqlDS = new MysqlDataSource();
            mysqlDS.setURL(props.getProperty("MYSQL_DB_URL"));
            mysqlDS.setUser(props.getProperty("MYSQL_DB_USERNAME"));
            mysqlDS.setPassword(props.getProperty("MYSQL_DB_PASSWORD"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return mysqlDS;
    }
}

