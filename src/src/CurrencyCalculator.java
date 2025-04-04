public class CurrencyCalculator {
    private String user_input_currency;
    private String currency_convert_to;


    public CurrencyCalculator(String user_input_currency, String currency_convert_to) {
        this.user_input_currency = user_input_currency;
        this.currency_convert_to = currency_convert_to;

    }

    public CurrencyCalculator() {
        this.user_input_currency = "";
        this.currency_convert_to = "";
    }

    public String getUser_input_currency() {
        return user_input_currency;
    }

    public String getCurrency_convert_to() {
        return currency_convert_to;
    }

    public void setUser_input_currency(String user_input_currency) {
        this.user_input_currency = user_input_currency;
    }

    public void setCurrency_convert_to(String currency_convert_to) {
        this.currency_convert_to = currency_convert_to;
    }

    public String conversion_calculator(double exchange_rate, String user_amount){
        int amount_to_convert = Integer.parseInt(user_amount);
        if (exchange_rate == 0){
            String message = ("Exchange rate could not be found");
            return message;
        } else {
            double converted_Amount = amount_to_convert * exchange_rate;
            return String.valueOf(converted_Amount);
        }
    }


}
