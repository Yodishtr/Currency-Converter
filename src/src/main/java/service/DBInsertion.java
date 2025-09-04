package service;

import dao.InsertLogs;
import util.DBConnection;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * This class represents the process by which the logs for recently converted amounts will
 * be inserted into the database.
 * This class will be used by the service wiring class to insert the log into the db
 * following conversion
 */
public class DBInsertion {
    private InsertLogs currentInsertionLog = new InsertLogs();
    private final DBConnection dbConnection = new DBConnection();

    public DBInsertion() {}


    /**
     * instance variables of InsertLog object currentInsertionLog should first be set
     * before calling this method
     */
    public void insertLog() {

        String source_currency = currentInsertionLog.getBaseCurrency();
        String end_currency = currentInsertionLog.getEndCurrency();
        BigDecimal amount = currentInsertionLog.getAmount();
        BigDecimal result = currentInsertionLog.getResult();

        String insertStmt = "INSERT INTO conversion_log(source_currency, end_currency, amount, result) VALUES(?, ?, ?, ?)";
        try{
            PreparedStatement preparedStatement = dbConnection.prepareStatement(insertStmt);
            preparedStatement.setString(1, source_currency);
            preparedStatement.setString(2, end_currency);
            preparedStatement.setBigDecimal(3, amount);
            preparedStatement.setBigDecimal(4, result);
            preparedStatement.executeUpdate();
            System.out.println("Insertion successful");
            dbConnection.conn.close();

        } catch (SQLException e) {
            System.out.println("Unable to insert conversion log. Error: " + e.getMessage());
        }
    }

    // Getters
    public InsertLogs getCurrentInsertionLog() {
        return currentInsertionLog;
    }

    // Setters
    public void setCurrentInsertionLog(InsertLogs currentInsertionLog){
        this.currentInsertionLog = currentInsertionLog;
    }
}
