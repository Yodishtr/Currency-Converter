package service;

import dao.ConversionRequest;
import dao.ConversionResult;
import dao.InsertLogs;
import dao.SelectLogs;
import model.CurrencyCatalog;
import model.CurrencyInfo;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

/**
 * This class represents the wiring of all the available services. This would
 * allow the controller to instantiate a single object through which conversions,
 * insertions into the database and retrieving information from the database can
 * all be done.
 */
public class ServiceWiring {
    private final Conversion conversionService = new Conversion();
    private final DBInsertion dbInsertionService = new DBInsertion();
    private final DBRetrieval dbRetrievalService = new DBRetrieval();
    private final TSVFileReader tsvFileReader = new TSVFileReader();

    public ServiceWiring() {}

    public ConversionResult performConversion(String baseCurrency, BigDecimal amount, String finalCurrency) {
        ConversionRequest conversionRequest = new ConversionRequest(baseCurrency, amount, finalCurrency);
        ConversionResult conversionResult = conversionService.convert(conversionRequest);
        return conversionResult;
    }

    public void performInsertion(String baseCurrency, String endCurrency, BigDecimal amount, BigDecimal result){
        InsertLogs currentInsertLog = new InsertLogs(baseCurrency, endCurrency, amount, result);
        dbInsertionService.setCurrentInsertionLog(currentInsertLog);
        dbInsertionService.insertLog();
    }

    public ArrayList<SelectLogs> performRetrieval(){
        return dbRetrievalService.retrieveLogsList();
    }

    public CurrencyCatalog getCurrencyCatalog(){
        CurrencyCatalog currCatalog = conversionService.populateCatalog();
        return currCatalog;
    }

    public void closeRetrievalConnection() throws SQLException {
        dbRetrievalService.getDBConnection().conn.close();
    }

    public void closeInsertionConnection() throws SQLException {
        dbInsertionService.getDBConnection().conn.close();
    }

    public Map<String, CurrencyInfo> getCurrencyDesc(){
        return tsvFileReader.getCurrencyInfoMap();
    }

}
