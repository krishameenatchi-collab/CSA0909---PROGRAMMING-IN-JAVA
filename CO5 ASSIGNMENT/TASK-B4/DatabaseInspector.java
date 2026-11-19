package TaskB4;

import java.sql.*;

public class DatabaseInspector {
    public static void inspectDatabase(Connection conn) throws SQLException {
        DatabaseMetaData meta = conn.getMetaData();
        System.out.println("Database Product Name : " + meta.getDatabaseProductName());
        System.out.println("Database Product Ver  : " + meta.getDatabaseProductVersion());
        System.out.println("Driver Name           : " + meta.getDriverName());
        System.out.println("Driver Version        : " + meta.getDriverVersion());
        
        try (ResultSet tables = meta.getTables(null, null, "%", new String[]{"TABLE"})) {
            System.out.println("Tables Discovered:");
            while (tables.next()) {
                System.out.println(" - " + tables.getString("TABLE_NAME"));
            }
        }
    }
}
