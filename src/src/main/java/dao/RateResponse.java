package dao;

import java.math.BigDecimal;
import java.util.Map;

/**
 * This Class represents the json data received from the API in a java format.
 * It matches the structure of the json data allowing us to extract the necessary information.
 * This will be then converted to a ConversionResult object
 */
public class RateResponse {
    private boolean success;
    private String base;
    private String date;
    private Map<String, BigDecimal> rates;


    // Setters
    public void setSuccess(boolean success){
        this.success = success;
    }

    public void setBase(String base){
        this.base = base;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setRates(Map<String, BigDecimal> rates){
        this.rates = rates;
    }

    // Getters
    public boolean getSuccess(){
        return success;
    }

    public String getBase(){
        return base;
    }

    public String getDate(){
        return date;
    }

    public Map<String, BigDecimal> getRates(){
        return rates;
    }
}
