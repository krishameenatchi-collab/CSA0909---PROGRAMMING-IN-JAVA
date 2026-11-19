// TASK-01: FeeWindowRepaired.java (Presentation Layer)
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class FeeWindowRepaired extends JFrame {
    private final JTextField regNoField = new JTextField(15);
    private final JTextField amountField = new JTextField(15);
    private final JButton payButton = new JButton("Pay");
    private final JButton clearButton = new JButton("Clear");
    private final JButton receiptButton = new JButton("Receipt");
    private final JLabel statusLabel = new JLabel("Status: Ready", JLabel.CENTER);

    private final FeeService feeService;

    public FeeWindowRepaired(FeeService feeService) {
        super("Student Fee Payment System");
        this.feeService = feeService;

        setLayout(new BorderLayout(10, 10));

        JPanel formPanel = new JPanel(new GridLayout(2, 2, 5, 5));
        formPanel.add(new JLabel("Register Number:"));
        formPanel.add(regNoField);
        formPanel.add(new JLabel("Amount (INR):"));
        formPanel.add(amountField);
        add(formPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(payButton);
        buttonPanel.add(receiptButton);
        buttonPanel.add(clearButton);
        add(buttonPanel, BorderLayout.SOUTH);

        add(statusLabel, BorderLayout.NORTH);

        payButton.addActionListener(this::handlePay);
        clearButton.addActionListener(e -> clearFields());
        receiptButton.addActionListener(e -> JOptionPane.showMessageDialog(this, 
            "Receipt generation feature coming soon.", "Information", JOptionPane.INFORMATION_MESSAGE));

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
    }

    private void handlePay(ActionEvent e) {
        payButton.setEnabled(false);
        statusLabel.setText("Status: Processing payment...");

        SwingWorker<Boolean, Void> worker = new SwingWorker<>() {
            @Override
            protected Boolean doInBackground() throws Exception {
                return feeService.processPayment(regNoField.getText(), amountField.getText());
            }

            @Override
            protected void done() {
                payButton.setEnabled(true);
                try {
                    boolean success = get();
                    if (success) {
                        statusLabel.setText("Status: Payment successful!");
                        JOptionPane.showMessageDialog(FeeWindowRepaired.this, 
                            "Payment recorded successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                        clearFields();
                    }
                } catch (Exception ex) {
                    Throwable cause = ex.getCause() != null ? ex.getCause() : ex;
                    statusLabel.setText("Status: Payment failed.");
                    JOptionPane.showMessageDialog(FeeWindowRepaired.this, 
                        cause.getMessage(), "Payment Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        };
        worker.execute();
    }

    private void clearFields() {
        regNoField.setText("");
        amountField.setText("");
        statusLabel.setText("Status: Ready");
    }

    public static void main(String[] args) {
        FeeService service = new FeeService("jdbc:mysql://localhost:3306/university", "root", "password");
        SwingUtilities.invokeLater(() -> new FeeWindowRepaired(service).setVisible(true));
    }
}
