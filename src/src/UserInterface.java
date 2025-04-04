import javax.swing.*;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UserInterface implements ActionListener {
    private JFrame frame;
    private JPanel panel;
    private JTextField user_input_amount;
    private JTextField user_current_currency;
    private JTextField currency_to_convert_to;
    private JTextField result;
    private JButton calculate_button;
    private APICaller apicaller;
    private CurrencyCalculator currencyCalculator;


    public UserInterface(){
        apicaller = new APICaller();
        frame = new JFrame();
        frame.setTitle("Simple Currency Converter");


        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        Dimension field_size = new Dimension(100, 50);

        user_input_amount = new JTextField();
        user_input_amount.setColumns(10);
        user_input_amount.setMaximumSize(field_size);

        user_current_currency = new JTextField();
        user_current_currency.setColumns(10);
        user_current_currency.setMaximumSize(field_size);

        currency_to_convert_to = new JTextField();
        currency_to_convert_to.setColumns(10);
        currency_to_convert_to.setMaximumSize(field_size);

        result = new JTextField();
        result.setMaximumSize(field_size);
        result.setEditable(false);



        panel.add(new JLabel("Amount"));
        panel.add(user_input_amount);
        panel.add(Box.createVerticalStrut(10));
        panel.add(new JLabel("Currency"));
        panel.add(user_current_currency);
        panel.add(Box.createVerticalStrut(10));
        panel.add(new JLabel("Final"));
        panel.add(currency_to_convert_to);
        panel.add(Box.createVerticalStrut(10));
        panel.add(new JLabel("Result"));
        panel.add(result);
        panel.add(Box.createVerticalStrut(10));
        panel.setVisible(true);

        currencyCalculator = new CurrencyCalculator();

        final JPanel button = new JPanel();
        calculate_button = new JButton("Calculate");
        button.add(calculate_button);
        calculate_button.addActionListener(this);

        frame.add(panel);
        frame.add(button);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

    }

    @Override public void actionPerformed(ActionEvent e) {
        if (e.getSource() == calculate_button) {
            String user_current_amount = user_input_amount.getText();
            String user_curr_currency = user_current_currency.getText();
            String to_currency = currency_to_convert_to.getText();

            currencyCalculator.setCurrency_convert_to(to_currency);
            currencyCalculator.setUser_input_currency(user_curr_currency);

            double current_exchange_rate = apicaller.get_conversion_rate(user_curr_currency, to_currency);
            String result_amount = currencyCalculator.conversion_calculator(current_exchange_rate, user_current_amount);
            result.setText(result_amount);
        }
    }
}
