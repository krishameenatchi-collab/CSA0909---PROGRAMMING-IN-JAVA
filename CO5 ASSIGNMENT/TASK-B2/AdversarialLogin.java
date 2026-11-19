package TaskB2;

import java.sql.*;

public class AdversarialLogin {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/campus_db";
    private static final String DB_USER = "root";
    private static final String DB_PASS = "password";

    public static boolean checkLoginUnsafe(Connection conn, String id, String pwd) throws SQLException {
        // UNSAFE: Direct string concatenation allows SQL Injection
        String sql = "SELECT * FROM users WHERE id='" + id + "' AND pwd='" + pwd + "'";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            return rs.next(); // Returns true if any row matched
        }
    }

    public static boolean checkLoginSafe(Connection conn, String id, String pwd) throws SQLException {
        // SAFE: PreparedStatement parameterized query protects against SQL Injection
        String sql = "SELECT * FROM users WHERE id=? AND pwd=?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, id);
            pstmt.setString(2, pwd);
            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next();
            }
        }
    }
}
