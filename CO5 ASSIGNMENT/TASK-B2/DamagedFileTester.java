package TaskB2;

import java.io.*;

public class DamagedFileTester {
    public static void testDamagedFile(File file) {
        System.out.println("Processing damaged pass file: " + file.getName());
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            int lineNumber = 0;
            while ((line = reader.readLine()) != null) {
                lineNumber++;
                if (line.trim().isEmpty()) {
                    System.err.println("[WARN] Line " + lineNumber + ": Empty line skipped.");
                    continue;
                }
                String[] tokens = line.split(",");
                if (tokens.length != 5) {
                    System.err.println("[WARN] Line " + lineNumber + ": Invalid field count (" + tokens.length + "/5). Skipped.");
                    continue;
                }
                try {
                    int route = Integer.parseInt(tokens[3].trim());
                    double amount = Double.parseDouble(tokens[4].trim());
                    System.out.println("[VALID] Line " + lineNumber + ": Pass ID=" + tokens[0] + ", Amount=" + amount);
                } catch (NumberFormatException e) {
                    System.err.println("[WARN] Line " + lineNumber + ": Numeric parse failure. Skipped.");
                }
            }
        } catch (IOException e) {
            System.err.println("[ERROR] File reading failed: " + e.getMessage());
        }
    }
}
