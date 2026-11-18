import java.io.*;
import java.util.logging.*;

public class ReportGenerator {
    private static final Logger LOGGER = Logger.getLogger(ReportGenerator.class.getName());

    // Version 1: Manual try-catch-finally (Prone to Resource Leaks)
    public static void generateManualReport(String[] storeFiles, String outputFile) {
        BufferedReader reader = null;
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(outputFile));
            for (String file : storeFiles) {
                LOGGER.info("Opening file: " + file);
                reader = new BufferedReader(new FileReader(file));
                String line = reader.readLine();
                if (line != null) {
                    writer.write(line);
                    writer.newLine();
                }
                reader.close();
            }
        } catch (IOException e) {
            LOGGER.severe("Manual Version Failed: " + e.getMessage());
        } finally {
            try {
                if (writer != null) writer.close();
            } catch (IOException e) {
                LOGGER.severe("Error closing writer: " + e.getMessage());
            }
        }
    }

    // Version 2: Robust try-with-resources
    public static void generateTWRReport(String[] storeFiles, String outputFile) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {
            for (String file : storeFiles) {
                LOGGER.info("Processing file: " + file);
                try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                    String line = reader.readLine();
                    if (line != null) {
                        writer.write(line);
                        writer.newLine();
                    }
                }
            }
        } catch (IOException e) {
            LOGGER.severe("TWR Version Handled Error cleanly: " + e.getMessage());
        }
    }
}
