package assignment2;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * Task 06 - Priority, Order and Fairness in a Queue
 * Course: Advanced Java Collections Engineering Design (ASSIGNMENT 2 - CO2)
 *
 * Problem Context: Hospital Emergency Triage
 * Manages patients by severity (1=critical, 5=minor) and preserves arrival order for ties.
 */
class Patient {
    final String name;
    final int severity;
    final long arrival;

    Patient(String name, int severity, long arrival) {
        this.name = name;
        this.severity = severity;
        this.arrival = arrival;
    }

    @Override
    public String toString() {
        return name + "(S" + severity + ")";
    }
}

public class Task06_Triage {
    private long counter = 0;

    // TODO 1: Create a PriorityQueue that serves the lowest severity number first and,
    // for equal severity, the earliest arrival first
    private final PriorityQueue<Patient> queue = new PriorityQueue<>(
            Comparator.comparingInt((Patient p) -> p.severity)
                      .thenComparingLong(p -> p.arrival)
    );

    void admit(String name, int severity) {
        queue.add(new Patient(name, severity, counter++));
    }

    Patient next() {
        return queue.poll();
    }

    public static void main(String[] args) {
        System.out.println("TASK 06: Hospital Emergency Triage PriorityQueue\n");

        Task06_Triage er = new Task06_Triage();
        er.admit("Ravi", 3);
        er.admit("Meena", 1);
        er.admit("John", 3);
        er.admit("Asha", 5);
        er.admit("Kumar", 1);
        er.admit("Lata", 2);

        StringBuilder order = new StringBuilder();
        Patient p;
        while ((p = er.next()) != null) {
            order.append(p).append(' ');
        }

        String actualOutput = order.toString().trim();
        System.out.println("Actual Output   : " + actualOutput);
        System.out.println("Expected Output : Meena(S1) Kumar(S1) Lata(S2) Ravi(S3) John(S3) Asha(S5)");
        System.out.println("Verification    : " + actualOutput.equals("Meena(S1) Kumar(S1) Lata(S2) Ravi(S3) John(S3) Asha(S5)"));
        System.out.println();

        explainArrivalCounter();
        explainHeapVsSortedOrder();
        demonstrateAgingFairRule();
        printDeploymentDecision();
    }

    private static void explainArrivalCounter() {
        System.out.println("--------------------------------------------------");
        System.out.println("ARRIVAL COUNTER TIE-BREAKING EXPLANATION:");
        System.out.println("--------------------------------------------------");
        System.out.println("- Each admit() call assigns a monotonically increasing long value 'counter++'.");
        System.out.println("- When two patients have equal severity (e.g., Meena S1 and Kumar S1),");
        System.out.println("  the comparator evaluates arrival times: Meena (counter=1) < Kumar (counter=4).");
        System.out.println("- This guarantees strict FIFO fairness within the same severity level.");
        System.out.println("--------------------------------------------------\n");
    }

    private static void explainHeapVsSortedOrder() {
        System.out.println("--------------------------------------------------");
        System.out.println("WHY PRINTING PriorityQueue DIRECTLY DOES NOT SHOW SORTED ORDER:");
        System.out.println("--------------------------------------------------");
        System.out.println("- PriorityQueue toString() iterates over its internal array representation (Binary Min-Heap).");
        System.out.println("- A binary heap guarantees only the heap property (parent <= children), NOT complete sorted order.");
        System.out.println("- To retrieve elements in priority order, elements MUST be sequentially removed using poll().");
        System.out.println("--------------------------------------------------\n");
    }

    private static void demonstrateAgingFairRule() {
        System.out.println("--------------------------------------------------");
        System.out.println("STARVATION PREVENTION: AGING FAIR TRIAGE RULE");
        System.out.println("--------------------------------------------------");
        System.out.println("Problem: Severe cases continuously arriving cause Severity-5 patients to wait indefinitely (Starvation).");
        System.out.println("Fair Rule Redesign: Dynamic Priority = severity - (waitTimeSeconds / agingThreshold).");
        System.out.println("If Asha (S5) waits > 30 minutes, her effective severity boosts to S4, then S3, ensuring eventual service.");
        System.out.println("--------------------------------------------------\n");
    }

    private static void printDeploymentDecision() {
        System.out.println("--------------------------------------------------");
        System.out.println("CITY HOSPITAL DEPLOYMENT DECISION (5,000 patients/day):");
        System.out.println("--------------------------------------------------");
        System.out.println("- 5,000 patients / day = ~3.5 admits/minute average.");
        System.out.println("- PriorityQueue add() and poll() take O(log N) time.");
        System.out.println("- For N = 500 active waiting patients, log2(500) ≈ 9 comparisons per operation.");
        System.out.println("- Execution time is under 1 microsecond per patient. Absolutely capable for city hospital scale.");
        System.out.println("--------------------------------------------------");
    }
}
