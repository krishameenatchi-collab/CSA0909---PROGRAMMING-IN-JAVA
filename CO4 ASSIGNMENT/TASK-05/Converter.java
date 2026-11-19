// TASK-05: Converter.java
import javax.swing.*;
import java.awt.*;

public class Converter extends JFrame {
    private final JTextField amountField = new JTextField(10);
    private final JComboBox<String> currencyCombo = new JComboBox<>(new String[]{"USD to EUR", "EUR to USD", "USD to INR", "INR to USD"});
    private final JButton convertButton = new JButton("Convert");
    private final JLabel resultLabel = new JLabel("Result: ---", JLabel.CENTER);

    public Converter() {
        super("Currency Converter");

        setLayout(new GridLayout(4, 1, 10, 10));

        JPanel p1 = new JPanel(new FlowLayout());
        p1.add(new JLabel("Amount:"));
        p1.add(amountField);
        add(p1);

        JPanel p2 = new JPanel(new FlowLayout());
        p2.add(new JLabel("Conversion:"));
        p2.add(currencyCombo);
        add(p2);

        JPanel p3 = new JPanel(new FlowLayout());
        p3.add(convertButton);
        add(p3);

        add(resultLabel);

        convertButton.addActionListener(e -> performConversion());

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
    }

    private void performConversion() {
        try {
            double input = Double.parseDouble(amountField.getText().trim());
            String mode = (String) currencyCombo.getSelectedItem();
            double result = 0.0;
            String symbol = "";

            switch (mode) {
                case "USD to EUR": result = input * 0.925; symbol = "EUR"; break;
                case "EUR to USD": result = input * 1.081; symbol = "USD"; break;
                case "USD to INR": result = input * 83.45; symbol = "INR"; break;
                case "INR to USD": result = input * 0.012; symbol = "USD"; break;
            }

            resultLabel.setText(String.format("Result: %.2f %s", result, symbol));
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter a valid numeric amount.", "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Converter().setVisible(true));
    }
}
