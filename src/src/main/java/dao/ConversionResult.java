package dao;

import java.math.BigDecimal;

/**
 * This class represents the result of the conversion.
 * It contains the base currency code, the initial amount, the rate and the final currency code.
 * It is used by the controller to display the results of the conversion on the UI.
 */
public class ConversionResult {
    private String baseCurrencyCode;
    private BigDecimal initialAmount;
    private BigDecimal rate;
    private String finalCurrencyCode;
    private BigDecimal finalAmount;

    public ConversionResult(String baseCurrencyCode, BigDecimal initialAmount, BigDecimal rate,
                            String finalCurrencyCode, BigDecimal finalAmount) {
        this.baseCurrencyCode = baseCurrencyCode;
        this.initialAmount = initialAmount;
        this.rate = rate;
        this.finalCurrencyCode = finalCurrencyCode;
        this.finalAmount = finalAmount;
    }

    // Getters
    public String getBaseCurrencyCode() {
        return baseCurrencyCode;
    }

    public BigDecimal getInitialAmount() {
        return initialAmount;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public String getFinalCurrencyCode() {
        return finalCurrencyCode;
    }

    public BigDecimal getFinalAmount() {
        return finalAmount;
    }


    // Setters
    public void setBaseCurrencyCode(String baseCurrencyCode){
        this.baseCurrencyCode = baseCurrencyCode;
    }

    public void setInitialAmount(BigDecimal initialAmount){
        this.initialAmount = initialAmount;
    }

    public void setRate(BigDecimal rate){
        this.rate = rate;
    }

    public void setFinalCurrencyCode(String finalCurrencyCode){
        this.finalCurrencyCode = finalCurrencyCode;
    }

    public void setFinalAmount(BigDecimal finalAmount){
        this.finalAmount = finalAmount;
    }

}
