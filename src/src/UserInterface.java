import javax.swing.*;
import javax.swing.event.DocumentListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UserInterface implements ActionListener, DocumentListener {
    private JFrame frame;
    private JPanel panel;
    private JTextField user_input_amount;
    private JTextField user_current_currency;
    private JTextField currency_to_convert_to;
    private JButton calculate_button;


    public UserInterface(){
        frame = new JFrame();
        frame.setTitle("Simple Currency Converter");

        panel = new JPanel();
        panel.setLayout(null);

        user_input_amount = new JTextField("Enter the amount you want to convert");
        user_current_currency = new JTextField("Enter the currency you have: EUR, GBP,..");
        currency_to_convert_to = new JTextField("Enter the currency you want to convert: EUR, GBP,..");

        CurrencyCalculator currencyCalculator = new CurrencyCalculator(user_current_currency.getText(),
                currency_to_convert_to.getText());

        APICaller apiCaller = new APICaller();
        double current_exchange_rate = apiCaller.get_conversion_rate(user_current_currency.getText(),
                currency_to_convert_to.getText());

        final JPanel button = new JPanel();
        calculate_button = new JButton("Calculate");
        button.add(calculate_button);
        calculate_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == calculate_button) {
                    currencyCalculator.conversion_calculator(current_exchange_rate, user_input_amount.getText());
                }
            }
        });



    }
}
