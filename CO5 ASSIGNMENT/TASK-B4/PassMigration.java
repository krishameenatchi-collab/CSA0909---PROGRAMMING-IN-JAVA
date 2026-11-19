package TaskB4;

import java.io.*;
import java.sql.*;
import java.util.*;

public class PassMigration {
    public static void migrateFileToDatabase(File csvFile, Connection conn) throws Exception {
        String sql = "INSERT INTO bus_pass(pass_id, reg_no, name, route_no, amount) VALUES (?, ?, ?, ?, ?)";
        boolean oldAuto = conn.getAutoCommit();
        conn.setAutoCommit(false);
        
        int migrated = 0;
        int failed = 0;
        
        try (BufferedReader reader = new BufferedReader(new FileReader(csvFile));
             PreparedStatement pstmt = conn.prepareStatement(sql);
             BufferedWriter errWriter = new BufferedWriter(new FileWriter("migration_errors.log"))) {
             
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] tokens = line.split(",");
                if (tokens.length != 5) {
                    errWriter.write("INVALID_FIELDS: " + line + "\n");
                    failed++;
                    continue;
                }
                try {
                    pstmt.setString(1, tokens[0].trim());
                    pstmt.setString(2, tokens[1].trim());
                    pstmt.setString(3, tokens[2].trim());
                    pstmt.setInt(4, Integer.parseInt(tokens[3].trim()));
                    pstmt.setDouble(5, Double.parseDouble(tokens[4].trim()));
                    pstmt.addBatch();
                    migrated++;
                    if (migrated % 1000 == 0) pstmt.executeBatch();
                } catch (Exception e) {
                    errWriter.write("PARSE_ERROR (" + e.getMessage() + "): " + line + "\n");
                    failed++;
                }
            }
            pstmt.executeBatch();
            conn.commit();
            System.out.println("Migration Completed: " + migrated + " rows migrated, " + failed + " records failed.");
        } catch (Exception e) {
            conn.rollback();
            throw e;
        } finally {
            conn.setAutoCommit(oldAuto);
        }
    }
}
