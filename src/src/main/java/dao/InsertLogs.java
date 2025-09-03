package dao;

import java.math.BigDecimal;

/**
 * This class represents the sql queries to be executed to log the latest conversion transaction in
 * the database.
 * It provides the base currency (source_currency), final currency (end_currency),
 * amount converted (amount), conversion result (result).
 */
public class InsertLogs {
    private String baseCurrency;
    private String endCurrency;
    private BigDecimal amount;
    private BigDecimal result;

    public InsertLogs(String baseCurrency, String endCurrency, BigDecimal amount, BigDecimal result ) {
        this.baseCurrency = baseCurrency;
        this.endCurrency = endCurrency;
        this.amount = amount;
        this.result = result;
    }

    // Getters
    public String getBaseCurrency() {
        return baseCurrency;
    }

    public String getEndCurrency() {
        return endCurrency;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public BigDecimal getResult() {
        return result;
    }

    // Setters
    public void setBaseCurrency(String baseCurrency){
        this.baseCurrency = baseCurrency;
    }

    public void setEndCurrency(String endCurrency){
        this.endCurrency = endCurrency;
    }

    public void setAmount(BigDecimal amount){
        this.amount = amount;
    }

    public void setResult(BigDecimal result){
        this.result = result;
    }



}
