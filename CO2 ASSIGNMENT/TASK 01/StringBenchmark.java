package assignment2;

/**
 * Task 01 - Building Text Under Load: String, StringBuilder or StringBuffer?
 * Course: Advanced Java Collections Engineering Design (ASSIGNMENT 2 - CO2)
 *
 * Problem Context: Railway e-Ticket Generation
 * Investigates performance, memory usage, object allocation, and thread safety.
 */
public class Task01_StringBenchmark {

    private static final String PNR_PREFIX = "PNR";
    private static final String PASSENGER_PREFIX = "Passenger_";
    private static final String COACH = "B3";
    private static final String BERTH_TYPE = "LOWER";
    private static final String TRAIN_NAME = "Express 12626";

    public static String buildTicketWithString(int fieldsCount) {
        String ticket = "";
        for (int i = 0; i < fieldsCount; i++) {
            ticket = ticket + PNR_PREFIX + i + ":" + PASSENGER_PREFIX + i + ":" + COACH + ":" + BERTH_TYPE + ":" + TRAIN_NAME + "; ";
        }
        return ticket;
    }

    public static String buildTicketWithStringBuilder(int fieldsCount) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < fieldsCount; i++) {
            sb.append(PNR_PREFIX).append(i)
              .append(":").append(PASSENGER_PREFIX).append(i)
              .append(":").append(COACH)
              .append(":").append(BERTH_TYPE)
              .append(":").append(TRAIN_NAME).append("; ");
        }
        return sb.toString();
    }

    public static String buildTicketWithStringBuffer(int fieldsCount) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < fieldsCount; i++) {
            sb.append(PNR_PREFIX).append(i)
              .append(":").append(PASSENGER_PREFIX).append(i)
              .append(":").append(COACH)
              .append(":").append(BERTH_TYPE)
              .append(":").append(TRAIN_NAME).append("; ");
        }
        return sb.toString();
    }

    public static void runBenchmark(String workloadName, int ticketCount, int fieldsPerTicket) {
        System.out.println("==================================================");
        System.out.println(" WORKLOAD: " + workloadName + " (" + ticketCount + " tickets, " + fieldsPerTicket + " fields/ticket)");
        System.out.println("==================================================");

        System.gc();
        long memBefore1 = getUsedMemory();
        long startTime1 = System.nanoTime();
        for (int i = 0; i < ticketCount; i++) {
            buildTicketWithString(fieldsPerTicket);
        }
        long elapsedTime1 = System.nanoTime() - startTime1;
        long memAfter1 = getUsedMemory();
        double timeMs1 = elapsedTime1 / 1_000_000.0;
        long estimatedObj1 = (long) ticketCount * fieldsPerTicket * 6L;

        System.out.printf("String (+)        : Time = %10.3f ms | Est. Objects = %10d | Mem Delta = %8.2f KB%n",
                timeMs1, estimatedObj1, (memAfter1 - memBefore1) / 1024.0);

        System.gc();
        long memBefore2 = getUsedMemory();
        long startTime2 = System.nanoTime();
        for (int i = 0; i < ticketCount; i++) {
            buildTicketWithStringBuilder(fieldsPerTicket);
        }
        long elapsedTime2 = System.nanoTime() - startTime2;
        long memAfter2 = getUsedMemory();
        double timeMs2 = elapsedTime2 / 1_000_000.0;
        long estimatedObj2 = ticketCount;

        System.out.printf("StringBuilder     : Time = %10.3f ms | Est. Objects = %10d | Mem Delta = %8.2f KB%n",
                timeMs2, estimatedObj2, (memAfter2 - memBefore2) / 1024.0);

        System.gc();
        long memBefore3 = getUsedMemory();
        long startTime3 = System.nanoTime();
        for (int i = 0; i < ticketCount; i++) {
            buildTicketWithStringBuffer(fieldsPerTicket);
        }
        long elapsedTime3 = System.nanoTime() - startTime3;
        long memAfter3 = getUsedMemory();
        double timeMs3 = elapsedTime3 / 1_000_000.0;
        long estimatedObj3 = ticketCount;

        System.out.printf("StringBuffer      : Time = %10.3f ms | Est. Objects = %10d | Mem Delta = %8.2f KB%n",
                timeMs3, estimatedObj3, (memAfter3 - memBefore3) / 1024.0);
        System.out.println();
    }

    private static long getUsedMemory() {
        Runtime runtime = Runtime.getRuntime();
        return runtime.totalMemory() - runtime.freeMemory();
    }

    public static void main(String[] args) {
        System.out.println("TASK 01: String vs StringBuilder vs StringBuffer Benchmarking\n");

        runBenchmark("Workload 1 (Short)", 1_000, 5);
        runBenchmark("Workload 2 (Large)", 10_000, 50);
        runBenchmark("Workload 3 (Custom Heavy)", 50_000, 20);

        printDesignDecisionDefense();
    }

    private static void printDesignDecisionDefense() {
        System.out.println("--------------------------------------------------");
        System.out.println("MULTITHREADED BOOKING SERVER DESIGN DECISION DEFENSE:");
        System.out.println("--------------------------------------------------");
        System.out.println("1. Per-Ticket Text Generation: RECOMMEND StringBuilder");
        System.out.println("   - Reason: Each ticket is constructed within a single thread's local execution stack.");
        System.out.println("   - Local variables are intrinsically thread-confined, making synchronization unnecessary.");
        System.out.println("   - StringBuilder avoids lock acquisition overhead and provides maximum throughput (O(n) time).");
        System.out.println("2. Shared Booking Log: RECOMMEND StringBuffer (or Concurrent Logger)");
        System.out.println("   - Reason: Multiple booking threads concurrently write log messages into a shared buffer.");
        System.out.println("   - StringBuffer methods are synchronized, ensuring thread-safe appends and preventing data corruption.");
        System.out.println("--------------------------------------------------");
        System.out.println("BIG-O COMPLEXITY ANALYSIS:");
        System.out.println("   - String (+ operator inside loop): Time O(n^2) due to copying existing char array on each iteration.");
        System.out.println("   - StringBuilder / StringBuffer: Time O(n) amortized due to dynamic array expansion.");
        System.out.println("   - Space: String (+) creates O(n^2) intermediate char copies; StringBuilder creates O(n) total space.");
        System.out.println("--------------------------------------------------");
    }
}
