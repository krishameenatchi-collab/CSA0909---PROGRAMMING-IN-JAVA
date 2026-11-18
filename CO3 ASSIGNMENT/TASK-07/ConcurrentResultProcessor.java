import java.util.concurrent.*;
import java.util.*;

class ScoreTask implements Callable<String> {
    private final String fileName;
    public ScoreTask(String fileName) { this.fileName = fileName; }

    @Override
    public String call() throws Exception {
        if (fileName.contains("corrupt")) {
            throw new IllegalArgumentException("Corrupt score file format");
        }
        if (fileName.contains("missing")) {
            throw new java.io.FileNotFoundException("File missing on storage node");
        }
        return "PROCESSED: " + fileName;
    }
}

public class ConcurrentResultProcessor {
    public static void main(String[] args) throws InterruptedException {
        int totalFiles = 100;
        List<String> files = new ArrayList<>();
        for (int i = 1; i <= totalFiles; i++) {
            if (i % 15 == 0) files.add("file_" + i + "_corrupt.txt");
            else if (i % 20 == 0) files.add("file_" + i + "_missing.txt");
            else files.add("file_" + i + ".txt");
        }

        int poolSize = Runtime.getRuntime().availableProcessors();
        ExecutorService executor = Executors.newFixedThreadPool(poolSize);

        ConcurrentMap<String, String> summaryMap = new ConcurrentHashMap<>();
        List<Future<String>> futures = new ArrayList<>();

        for (String file : files) {
            futures.add(executor.submit(new ScoreTask(file)));
        }

        int successCount = 0, failureCount = 0;
        for (int i = 0; i < futures.size(); i++) {
            try {
                String result = futures.get(i).get();
                summaryMap.put(files.get(i), result);
                successCount++;
            } catch (ExecutionException e) {
                summaryMap.put(files.get(i), "FAILED: " + e.getCause().getMessage());
                failureCount++;
            }
        }

        executor.shutdown();
        System.out.println("Processing Summary: Successes = " + successCount + ", Failures = " + failureCount);
    }
}
