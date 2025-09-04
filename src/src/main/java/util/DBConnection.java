package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * This class represents the connection between this app to the database where currency logs are stored.
 * It provides a constructor that makes the connection to the database.
 * author: Yodishtr
 */
public class DBConnection {
    public  Connection conn;
    private final String url = "jdbc:postgresql://localhost:5432/currency";
    private final String user = "yodishtrvythilingum";
    private final String password = "";

    public DBConnection() {

        try{
            Class.forName("org.postgresql.Driver");
            System.out.println("Connecting to the database...");
            conn = DriverManager.getConnection(url, user, password);
            System.out.println("Successfully connected to the database!");
        } catch (ClassNotFoundException e) {
            System.out.println("Failed to load Postgresql Driver or Dependency could not be found!");
        } catch (SQLException e1){
            System.out.println("Failed to connect to the database");
        }

    }

    public PreparedStatement prepareStatement(String sql) throws SQLException {
        return conn.prepareStatement(sql);
    }


}
