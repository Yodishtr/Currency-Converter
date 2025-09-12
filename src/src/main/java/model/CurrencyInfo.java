package model;

/**
 * This class represents all info for a currency. It holds:
 *  - currency code
 *  - currency name
 *  - description of the currency
 * It is used by the TSVFileReader class to create an active storage of
 * the currencies info which can be easily retrieved by the ServiceWiring class.
 */
public class CurrencyInfo {
    private String currencyCode;
    private String currencyName;
    private String currencyDescription;

    public CurrencyInfo(String currencyCode, String currencyName, String currencyDescription){
        this.currencyCode = currencyCode;
        this.currencyName = currencyName;
        this.currencyDescription = currencyDescription;
    }

    // Getters
    public String getCurrencyCode() {
        return currencyCode;
    }

    public String getCurrencyName(){
        return currencyName;
    }

    public String getCurrencyDescription(){
        return currencyDescription;
    }

    // Setters
    public void setCurrencyCode(String currencyCode){
        this.currencyCode = currencyCode;
    }

    public void setCurrencyName(String currencyName){
        this.currencyName = currencyName;
    }

    public void setCurrencyDescription(String currencyDescription){
        this.currencyDescription = currencyDescription;
    }
}
