// TASK-01: FeeService.java (Business & Data Layer)
import java.sql.*;

public class FeeService {
    private final String dbUrl;
    private final String user;
    private final String password;

    public FeeService(String dbUrl, String user, String password) {
        this.dbUrl = dbUrl;
        this.user = user;
        this.password = password;
    }

    public boolean processPayment(String regNo, String amountStr) throws IllegalArgumentException, SQLException {
        if (regNo == null || regNo.trim().isEmpty()) {
            throw new IllegalArgumentException("Registration Number cannot be empty.");
        }
        double amount;
        try {
            amount = Double.parseDouble(amountStr);
            if (amount <= 0) {
                throw new IllegalArgumentException("Payment amount must be greater than zero.");
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid amount format. Please enter a valid number.");
        }

        String sql = "INSERT INTO fees (reg_no, amount, payment_date) VALUES (?, ?, CURRENT_TIMESTAMP)";
        try (Connection conn = DriverManager.getConnection(dbUrl, user, password);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, regNo.trim());
            pstmt.setDouble(2, amount);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        }
    }
}
