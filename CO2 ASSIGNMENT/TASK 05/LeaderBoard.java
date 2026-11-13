package assignment2;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Task 05 - Derive a Generic Ranking Component from a Principle
 * Course: Advanced Java Collections Engineering Design (ASSIGNMENT 2 - CO2)
 *
 * Problem Context: Inter-College Sports Leaderboard
 * Ranks teams by points (high first), tie-break by goal diff (high first), then name (A-Z).
 */
class Team {
    final String name;
    final int points;
    final int goalDiff;

    Team(String name, int points, int goalDiff) {
        this.name = name;
        this.points = points;
        this.goalDiff = goalDiff;
    }

    public String getName() {
        return name;
    }

    public int getPoints() {
        return points;
    }

    public int getGoalDiff() {
        return goalDiff;
    }

    @Override
    public String toString() {
        return name + "(" + points + "," + goalDiff + ")";
    }
}

class Leaderboard<T> {
    private final List<T> items = new ArrayList<>();
    private final Comparator<? super T> order;

    Leaderboard(Comparator<? super T> order) {
        this.order = order;
    }

    void add(T item) {
        items.add(item);
    }

    // TODO 1: Return a new sorted copy of items using order, without modifying items
    List<T> ranking() {
        List<T> sortedCopy = new ArrayList<>(items);
        sortedCopy.sort(order);
        return sortedCopy;
    }
}

public class Task05_Leaderboard {

    public static void main(String[] args) {
        System.out.println("TASK 05: Generic Leaderboard & Comparator Chain\n");

        // TODO 2: Build the comparator: points (high first), then goal difference (high first), then name (A-Z)
        Comparator<Team> byRank = Comparator
                .comparing(Team::getPoints, Comparator.reverseOrder())
                .thenComparing(Team::getGoalDiff, Comparator.reverseOrder())
                .thenComparing(Team::getName);

        Leaderboard<Team> board = new Leaderboard<>(byRank);
        board.add(new Team("Panthers", 9, 4));
        board.add(new Team("Eagles", 9, 6));
        board.add(new Team("Cobras", 6, 1));
        board.add(new Team("Bisons", 9, 4));

        List<Team> result = board.ranking();
        System.out.println("Actual Output   : " + result);
        System.out.println("Expected Output : [Eagles(9,6), Bisons(9,4), Panthers(9,4), Cobras(6,1)]");
        System.out.println("Match Verification: " + result.toString().equals("[Eagles(9,6), Bisons(9,4), Panthers(9,4), Cobras(6,1)]"));
        System.out.println();

        explainRankingResult();
        testFaultyComparator();
        printAlternativeAnalysis();
    }

    private static void explainRankingResult() {
        System.out.println("--------------------------------------------------");
        System.out.println("EXPLANATION OF RANKING RESULT (Bisons vs Panthers):");
        System.out.println("--------------------------------------------------");
        System.out.println("1. Points comparison: Bisons (9) == Panthers (9). Tie!");
        System.out.println("2. Goal difference: Bisons (4) == Panthers (4). Tie!");
        System.out.println("3. Name tie-breaker: 'Bisons'.compareTo('Panthers') < 0 (Alphabetical order).");
        System.out.println("Hence, Bisons is correctly ranked above Panthers.");
        System.out.println("--------------------------------------------------\n");
    }

    private static void testFaultyComparator() {
        System.out.println("--------------------------------------------------");
        System.out.println("TESTING FAULTY COMPARATOR (Demonstration of Failure):");
        System.out.println("--------------------------------------------------");
        Comparator<Team> faultyComparator = (t1, t2) -> t2.points - t1.points;

        Leaderboard<Team> faultyBoard = new Leaderboard<>(faultyComparator);
        faultyBoard.add(new Team("Panthers", 9, 4));
        faultyBoard.add(new Team("Eagles", 9, 6));
        faultyBoard.add(new Team("Cobras", 6, 1));
        faultyBoard.add(new Team("Bisons", 9, 4));

        System.out.println("Faulty Output: " + faultyBoard.ranking());
        System.out.println("Notice: Bisons and Panthers order is unstable/arbitrary because tie-breakers were ignored!");
        System.out.println("--------------------------------------------------\n");
    }

    private static void printAlternativeAnalysis() {
        System.out.println("--------------------------------------------------");
        System.out.println("ALTERNATIVE CONCEPT & REUSABILITY ANALYSIS:");
        System.out.println("--------------------------------------------------");
        System.out.println("1. Comparator Chain (Implemented):");
        System.out.println("   - Decouples ranking logic from entity classes.");
        System.out.println("   - Easily re-used for Players, Colleges, Quiz Teams with custom tie-breakers.");
        System.out.println("   - Single pass sort: O(n log n) comparisons.");
        System.out.println("2. Comparable<Team>:");
        System.out.println("   - Hardcodes natural ordering inside Team class; lacks flexibility.");
        System.out.println("--------------------------------------------------");
    }
}
