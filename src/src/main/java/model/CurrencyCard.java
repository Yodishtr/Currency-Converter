package model;

/**
 * It is an index card that holds
 *  - the ISO code (like USD),
 *  - the human name (US Dollar),
 *  - and optionally a symbol ($).
 * This card also knows how it wants to be shown in the UI (for example: “USD — US Dollar ($)”).
 * I overrode the toString method to achieve this and
 * that way, when these cards are put into the filter combo boxes, they look nice and are easy to search.
 */
public class CurrencyCard {
    private String currencyCode;

    public CurrencyCard(String currencyCode){
        this.currencyCode = currencyCode;
    }

    @Override
    public String toString() {
        return currencyCode;
    }

    public String getCurrencyCode(){
        return currencyCode;
    }

    public void setCurrencyCode(String newCurrencyCode){
        currencyCode = newCurrencyCode;
    }
}
