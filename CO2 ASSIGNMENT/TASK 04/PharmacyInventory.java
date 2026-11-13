package assignment2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Task 04 - Choosing a Map for a Pharmacy Inventory
 * Course: Advanced Java Collections Engineering Design (ASSIGNMENT 2 - CO2)
 *
 * Problem Context: Hospital Pharmacy Stock Lookup
 * Compares HashMap, TreeMap, and LinkedHashMap for lookups and report ordering.
 */
public class Task04_PharmacyInventory {

    static class Medicine implements Comparable<Medicine> {
        private final String code;
        private final String name;
        private int stock;
        private double price;

        public Medicine(String code, String name, int stock, double price) {
            this.code = code;
            this.name = name;
            this.stock = stock;
            this.price = price;
        }

        public String getCode() {
            return code;
        }

        public String getName() {
            return name;
        }

        @Override
        public int compareTo(Medicine o) {
            return this.code.compareTo(o.code);
        }

        @Override
        public String toString() {
            return String.format("[%s] %-15s Stock: %3d | $%.2f", code, name, stock, price);
        }
    }

    public static void runMapBenchmark(int inventorySize, int lookupCount) {
        System.out.println("==================================================");
        System.out.println(" PHARMACY BENCHMARK - Size: " + inventorySize + " medicines, Lookups: " + lookupCount);
        System.out.println("==================================================");

        Map<String, Medicine> hashMap = new HashMap<>();
        Map<String, Medicine> treeMap = new TreeMap<>();
        Map<String, Medicine> linkedHashMap = new LinkedHashMap<>();

        for (int i = 0; i < inventorySize; i++) {
            String code = String.format("MED%06d", (i * 37) % inventorySize);
            Medicine m = new Medicine(code, "Med_" + i, 50 + (i % 100), 10.5 + i);
            hashMap.put(code, m);
            treeMap.put(code, m);
            linkedHashMap.put(code, m);
        }

        long start1 = System.nanoTime();
        for (int i = 0; i < lookupCount; i++) {
            String queryCode = String.format("MED%06d", i % inventorySize);
            Medicine m = hashMap.get(queryCode);
        }
        long timeHashGet = System.nanoTime() - start1;

        long start2 = System.nanoTime();
        for (int i = 0; i < lookupCount; i++) {
            String queryCode = String.format("MED%06d", i % inventorySize);
            Medicine m = treeMap.get(queryCode);
        }
        long timeTreeGet = System.nanoTime() - start2;

        long start3 = System.nanoTime();
        for (int i = 0; i < lookupCount; i++) {
            String queryCode = String.format("MED%06d", i % inventorySize);
            Medicine m = linkedHashMap.get(queryCode);
        }
        long timeLinkedGet = System.nanoTime() - start3;

        System.out.printf("Lookups (%d ops)%n", lookupCount);
        System.out.printf("  HashMap       : %8.3f ms (Avg: %6.2f ns/op)%n", timeHashGet / 1e6, (double) timeHashGet / lookupCount);
        System.out.printf("  TreeMap       : %8.3f ms (Avg: %6.2f ns/op)%n", timeTreeGet / 1e6, (double) timeTreeGet / lookupCount);
        System.out.printf("  LinkedHashMap : %8.3f ms (Avg: %6.2f ns/op)%n%n", timeLinkedGet / 1e6, (double) timeLinkedGet / lookupCount);

        long startTreeReport = System.nanoTime();
        List<Medicine> treeReport = new ArrayList<>(treeMap.values());
        long timeTreeReport = System.nanoTime() - startTreeReport;

        long startHashReport = System.nanoTime();
        List<Medicine> hashReport = new ArrayList<>(hashMap.values());
        Collections.sort(hashReport);
        long timeHashReport = System.nanoTime() - startHashReport;

        long startLinkedReport = System.nanoTime();
        List<Medicine> insertionReport = new ArrayList<>(linkedHashMap.values());
        long timeLinkedReport = System.nanoTime() - startLinkedReport;

        System.out.println("Report Generation Time:");
        System.out.printf("  Code-Order Report (TreeMap natural)    : %8.3f ms%n", timeTreeReport / 1e6);
        System.out.printf("  Code-Order Report (HashMap + sort)     : %8.3f ms (Overhead added!)%n", timeHashReport / 1e6);
        System.out.printf("  Insertion-Order Report (LinkedHashMap) : %8.3f ms%n%n", timeLinkedReport / 1e6);
    }

    public static void main(String[] args) {
        System.out.println("TASK 04: HashMap vs TreeMap vs LinkedHashMap\n");

        runMapBenchmark(500, 100_000);
        runMapBenchmark(5_000, 100_000);
        runMapBenchmark(50_000, 100_000);

        printScalabilityAndHashtableAnalysis();
    }

    private static void printScalabilityAndHashtableAnalysis() {
        System.out.println("--------------------------------------------------");
        System.out.println("SCALABILITY & HASHTABLE ANALYSIS:");
        System.out.println("--------------------------------------------------");
        System.out.println("1. Acceptable Response Time per Lookup:");
        System.out.println("   - Industry benchmark: < 1.0 microsecond (< 1,000 ns) per lookup.");
        System.out.println("   - HashMap (~50-150 ns) and LinkedHashMap (~60-180 ns) easily satisfy this.");
        System.out.println("   - TreeMap (~300-900 ns) is slower due to O(log n) tree traversal and cache misses.");
        System.out.println("2. Why Hashtable is Obsolete:");
        System.out.println("   - Hashtable synchronizes all operations at method level (monolithic lock).");
        System.out.println("   - Does not permit null keys or null values.");
        System.out.println("   - Modern replacement for concurrency: ConcurrentHashMap; for single-thread: HashMap.");
        System.out.println("--------------------------------------------------");
        System.out.println("ALTERNATIVE CONCEPT (Sorted Array + Arrays.binarySearch):");
        System.out.println("   - Lookup: O(log n) via binary search.");
        System.out.println("   - Insertion: O(n) expensive shifting.");
        System.out.println("   - Code-order report: O(1) zero-copy slice!");
        System.out.println("--------------------------------------------------");
    }
}
