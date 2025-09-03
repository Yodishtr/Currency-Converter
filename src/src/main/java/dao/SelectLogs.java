package dao;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * This class represents the conversions that the user has done.
 * It classifies it with the amount converted, resulting conversion, base and end currency,
 * as well as the time the conversion was done.
 * This data is retrieved from the database and multiple instances of this class are created
 * to be displayed in the ui for the user to view. Generally there should only be the 5 latest.
 */
public class SelectLogs {
    private String baseCurrency;
    private String finalCurrency;
    private BigDecimal amount;
    private BigDecimal result;
    private LocalDateTime timeStamp;

    public SelectLogs(String baseCurrency, String finalCurrency, BigDecimal amount, BigDecimal result, LocalDateTime timeStamp) {
        this.baseCurrency = baseCurrency;
        this.finalCurrency = finalCurrency;
        this.amount = amount;
        this.result = result;
        this.timeStamp = timeStamp;
    }
}
