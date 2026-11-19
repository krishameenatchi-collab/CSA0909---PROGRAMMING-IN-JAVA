package TaskB1;

import java.io.*;

public class LineCounter {
    public static long countUnbuffered(File file) throws IOException {
        long lines = 0;
        try (InputStream in = new FileInputStream(file)) {
            int b;
            while ((b = in.read()) != -1) {
                if (b == '\n') lines++;
            }
        }
        return lines;
    }

    public static long countBuffered(File file) throws IOException {
        long lines = 0;
        try (InputStream in = new BufferedInputStream(new FileInputStream(file))) {
            byte[] buffer = new byte[8192];
            int bytesRead;
            while ((bytesRead = in.read(buffer)) != -1) {
                for (int i = 0; i < bytesRead; i++) {
                    if (buffer[i] == '\n') lines++;
                }
            }
        }
        return lines;
    }
}
