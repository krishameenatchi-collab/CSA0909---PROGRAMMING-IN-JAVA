package assignment2;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Task 03 - Engineer the hashCode-equals Contract
 * Course: Advanced Java Collections Engineering Design (ASSIGNMENT 2 - CO2)
 *
 * Problem Context: Event Attendance De-duplication
 * Demonstrates the contract between equals() and hashCode() in hash-based collections.
 */

class UncorrectedStudent {
    String regNo;
    String name;

    UncorrectedStudent(String regNo, String name) {
        this.regNo = regNo;
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UncorrectedStudent)) return false;
        return regNo.equals(((UncorrectedStudent) o).regNo);
    }
    // hashCode() is deliberately NOT overridden here
}

class Student {
    private final String regNo;
    private final String name;

    Student(String regNo, String name) {
        this.regNo = regNo;
        this.name = name;
    }

    public String getRegNo() {
        return regNo;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Student)) return false;
        return regNo.equals(((Student) o).regNo);
    }

    // TODO 1: Override hashCode() so that it is consistent with equals()
    @Override
    public int hashCode() {
        return Objects.hash(regNo);
    }

    @Override
    public String toString() {
        return regNo + " " + name;
    }
}

class MutableStudent {
    private String regNo;
    private String name;

    MutableStudent(String regNo, String name) {
        this.regNo = regNo;
        this.name = name;
    }

    public void setRegNo(String regNo) {
        this.regNo = regNo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MutableStudent)) return false;
        return Objects.equals(regNo, ((MutableStudent) o).regNo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(regNo);
    }
}

public class Task03_Attendance {

    public static void main(String[] args) {
        System.out.println("TASK 03: Engineer the hashCode-equals Contract\n");

        System.out.println("--- 1. BEFORE TODO 1 (Without hashCode override) ---");
        Set<UncorrectedStudent> uncorrectedSet = new HashSet<>();
        uncorrectedSet.add(new UncorrectedStudent("192311001", "Arun"));
        uncorrectedSet.add(new UncorrectedStudent("192311002", "Divya"));
        uncorrectedSet.add(new UncorrectedStudent("192311001", "Arun"));

        System.out.println("Output BEFORE TODO 1 (Actual size): " + uncorrectedSet.size());
        System.out.println("Explanation: Object.hashCode() identity hash causes duplicate Arun to land in a different bucket.\n");

        System.out.println("--- 2. AFTER TODO 1 (Corrected Attendance Main Program) ---");
        Set<Student> present = new HashSet<>();
        present.add(new Student("192311001", "Arun")); // scanned at Gate 1
        present.add(new Student("192311002", "Divya"));
        present.add(new Student("192311001", "Arun")); // scanned again at Gate 3

        System.out.println("Unique students: " + present.size());
        System.out.println("Expected output: Unique students: 2\n");

        runDesignedTests();
        printAlternativeAnalysis();
    }

    private static void runDesignedTests() {
        System.out.println("--------------------------------------------------");
        System.out.println("DESIGNED EXPERIMENTAL TESTS:");
        System.out.println("--------------------------------------------------");

        System.out.println("[Test 1 - Working Case]: Adding 3 distinct students");
        Set<Student> set1 = new HashSet<>();
        set1.add(new Student("101", "Alice"));
        set1.add(new Student("102", "Bob"));
        set1.add(new Student("103", "Charlie"));
        System.out.println("Prediction: Size = 3 | Observed Size = " + set1.size());

        System.out.println("\n[Test 2 - Duplicate Exposure]: Scanning same student 5 times");
        Set<Student> set2 = new HashSet<>();
        for (int i = 0; i < 5; i++) {
            set2.add(new Student("192311001", "Arun"));
        }
        System.out.println("Prediction: Size = 1 | Observed Size = " + set2.size());

        System.out.println("\n[Test 3 - Mutation Vulnerability Test]: Modifying regNo after insertion");
        Set<MutableStudent> set3 = new HashSet<>();
        MutableStudent s1 = new MutableStudent("192311099", "Eve");
        set3.add(s1);

        System.out.println("Contains s1 before mutation: " + set3.contains(s1));
        s1.setRegNo("192311000");
        System.out.println("Contains s1 AFTER mutation : " + set3.contains(s1));
        System.out.println("Prediction: contains() returns false because bucket index shifted!");
        System.out.println("Observed Set Size: " + set3.size() + " (Element trapped/orphaned in hash table)");
        System.out.println("--------------------------------------------------\n");
    }

    private static void printAlternativeAnalysis() {
        System.out.println("--------------------------------------------------");
        System.out.println("ALTERNATIVE CONCEPT & COMPLEXITY ANALYSIS:");
        System.out.println("--------------------------------------------------");
        System.out.println("1. TreeSet<Student> with Comparator on regNo:");
        System.out.println("   - De-duplication depends entirely on Comparator.compare() == 0, NOT equals() or hashCode().");
        System.out.println("   - Time Complexity per scan: O(log n) comparison time.");
        System.out.println("2. HashMap<String, Student> keyed by regNo:");
        System.out.println("   - Key is String (regNo), which has an immutable, pre-computed hashCode().");
        System.out.println("   - Time Complexity per scan: O(1) average time.");
        System.out.println("--------------------------------------------------");
    }
}
