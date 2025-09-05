package service;

import dao.ConversionRequest;
import dao.ConversionResult;
import dao.RateResponse;
import util.APIClient;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;

/**
 * This class represents the conversion service that we will be doing
 * for converting from our base currency to our final currency.
 */
public class Conversion {
    private final APIClient apiClient = new APIClient();
    private RateResponse rateResponse;

    public Conversion() {
        rateResponse = apiClient.fetchRate();
    }

    /**
     * The argument to this will be provided by the ServiceWiring class which
     * will use arguments received from the controller.
     * @param toConvert
     * @return
     */
    public ConversionResult convert(ConversionRequest toConvert) {
        BigDecimal currentAmount = toConvert.getAmount();
        String currBaseCurrency = toConvert.getBaseCurrency();
        String finalCurrency = toConvert.getFinalCurrency();
        Map<String, BigDecimal> ratesMap = rateResponse.getRates();

        if (currBaseCurrency.equals("EUR")){
            // if from eur to some other currency
            BigDecimal currentRate = ratesMap.get(finalCurrency);
            BigDecimal resultAmount = currentAmount.multiply(currentRate);
            ConversionResult conversionAnswer = new ConversionResult(currBaseCurrency, currentAmount, currentRate,
                    finalCurrency, resultAmount);
            return conversionAnswer;
        } else if (finalCurrency.equals("EUR")) {
            // if from a base currency to EUR
            BigDecimal eurToBaseRate = ratesMap.get(currBaseCurrency);
            BigDecimal baseToEurRate = BigDecimal.ONE.divide(eurToBaseRate, RoundingMode.HALF_UP);
            BigDecimal resultAmount = currentAmount.multiply(baseToEurRate);
            ConversionResult conversionAnswer = new ConversionResult(currBaseCurrency, currentAmount, baseToEurRate,
                    finalCurrency, resultAmount);
            return conversionAnswer;
        } else {
            // if from a base currency != eur to another currency != eur
            BigDecimal eurToBaseRate = ratesMap.get(currBaseCurrency);
            BigDecimal eurToFinalRate = ratesMap.get(finalCurrency);
            BigDecimal baseToFinalRate = eurToFinalRate.divide(eurToBaseRate, RoundingMode.HALF_UP);
            BigDecimal resultAmount = currentAmount.multiply(baseToFinalRate);
            ConversionResult conversionAnswer = new ConversionResult(currBaseCurrency, currentAmount, baseToFinalRate,
                    finalCurrency, resultAmount);
            return conversionAnswer;

        }
    }

}
