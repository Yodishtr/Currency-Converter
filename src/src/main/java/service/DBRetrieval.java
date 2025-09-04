package service;

import dao.SelectLogs;
import dao.SelectLogsList;
import util.DBConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * This class represents the service of retrieving the conversions log
 * made by the user, and so we are able to edit this list to the last 5 and
 * display them in the ui. The ServiceWiring class will use this class
 * to enable the conversion logs display by the controller.
 */
public class DBRetrieval {
   private final DBConnection dbConnection = new DBConnection();
   private final SelectLogsList selectLogsList = new SelectLogsList();

   public ArrayList<SelectLogs> retrieveLogsList() {
       String selectStmt = "SELECT source_currency, end_currency, amount, result, converted_at FROM conversion_log";
       try {
           PreparedStatement preparedStatement = dbConnection.prepareStatement(selectStmt);
           ResultSet resultSet = preparedStatement.executeQuery();
           while (resultSet.next()){
               SelectLogs currentLog = new SelectLogs();
               currentLog.setBaseCurrency(resultSet.getString("source_currency"));
               currentLog.setFinalCurrency(resultSet.getString("end_currency"));
               currentLog.setAmount(resultSet.getBigDecimal("amount"));
               currentLog.setResult(resultSet.getBigDecimal("result"));
               currentLog.setTimeStamp(resultSet.getTimestamp("converted_at").toLocalDateTime());
               selectLogsList.getLogsList().add(0, currentLog);
           }
           dbConnection.conn.close();
       } catch (SQLException e) {
           System.out.println("Unable to retrieve logs from database. Error: " + e.getMessage());
       }
       int numberOfLogs = selectLogsList.getLogsList().size();
       if (numberOfLogs == 0) {
           return new ArrayList<>();
       } else if (numberOfLogs < 5) {
           return selectLogsList.getLogsList();
       } else {
           return new ArrayList<>(selectLogsList.getLogsList().subList(0, 6));
       }

   }


}
