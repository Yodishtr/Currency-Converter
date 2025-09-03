package dao;

import java.math.BigDecimal;

/**
 * This is a class representing the funds that the user wants to convert.
 * It contains info about the base currency, the amount to be converted,
 * and the final currency code.
 * It is used by the controller to send to the UI for conversion.
 */
public class ConversionRequest {
    private String baseCurrency;
    private BigDecimal amount;
    private String finalCurrency;

    public ConversionRequest(String baseCurrency, BigDecimal amount, String finalCurrency) {
        this.baseCurrency = baseCurrency;
        this.amount = amount;
        this.finalCurrency = finalCurrency;
    }

    // Getters
    public String getBaseCurrency() {
        return baseCurrency;
    }

    public String getFinalCurrency() {
        return finalCurrency;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    // Setters
    public void setBaseCurrency(String baseCurrency) {
        this.baseCurrency = baseCurrency;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public void setFinalCurrency(String finalCurrency) {
        this.finalCurrency = finalCurrency;
    }
}
