package assignment2;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.ArrayDeque;
import java.util.List;

/**
 * Task 02 - When Is ArrayList an Appropriate Engineering Choice?
 * Course: Advanced Java Collections Engineering Design (ASSIGNMENT 2 - CO2)
 *
 * Problem Context: Music Playlist Editor & "Up Next" Queue
 * Compares ArrayList, LinkedList, and ArrayDeque for various list operations.
 */
public class Task02_PlaylistEditor {

    static class Song {
        private final String id;
        private final String title;
        private final String artist;

        public Song(String id, String title, String artist) {
            this.id = id;
            this.title = title;
            this.artist = artist;
        }

        @Override
        public String toString() {
            return title + " - " + artist;
        }
    }

    public static void runBenchmarkForSize(int size) {
        System.out.println("==================================================");
        System.out.println(" PLAYLIST EXPERIMENT - SIZE: " + size + " Songs");
        System.out.println("==================================================");

        List<Song> arrayList = new ArrayList<>();
        List<Song> linkedList = new LinkedList<>();

        long start1 = System.nanoTime();
        for (int i = 0; i < size; i++) {
            arrayList.add(new Song("S" + i, "Song " + i, "Artist " + i));
        }
        long timeArrayListAddEnd = System.nanoTime() - start1;

        long start2 = System.nanoTime();
        for (int i = 0; i < size; i++) {
            linkedList.add(new Song("S" + i, "Song " + i, "Artist " + i));
        }
        long timeLinkedListAddEnd = System.nanoTime() - start2;

        System.out.printf("Add at End (N=%d)      | ArrayList: %8.3f ms | LinkedList: %8.3f ms%n",
                size, timeArrayListAddEnd / 1e6, timeLinkedListAddEnd / 1e6);

        int lookups = Math.min(10_000, size);
        long start3 = System.nanoTime();
        for (int i = 0; i < lookups; i++) {
            int index = (i * 37) % size;
            Song s = arrayList.get(index);
        }
        long timeArrayListGet = System.nanoTime() - start3;

        long start4 = System.nanoTime();
        for (int i = 0; i < lookups; i++) {
            int index = (i * 37) % size;
            Song s = linkedList.get(index);
        }
        long timeLinkedListGet = System.nanoTime() - start4;

        System.out.printf("Get k-th (%d ops)       | ArrayList: %8.3f ms | LinkedList: %8.3f ms%n",
                lookups, timeArrayListGet / 1e6, timeLinkedListGet / 1e6);

        int frontOps = Math.min(1_000, size);
        long start5 = System.nanoTime();
        for (int i = 0; i < frontOps; i++) {
            arrayList.add(0, new Song("F" + i, "Front Song", "Artist"));
        }
        long timeArrayListInsertFront = System.nanoTime() - start5;

        long start6 = System.nanoTime();
        for (int i = 0; i < frontOps; i++) {
            linkedList.add(0, new Song("F" + i, "Front Song", "Artist"));
        }
        long timeLinkedListInsertFront = System.nanoTime() - start6;

        System.out.printf("Insert Front (%d ops)  | ArrayList: %8.3f ms | LinkedList: %8.3f ms%n",
                frontOps, timeArrayListInsertFront / 1e6, timeLinkedListInsertFront / 1e6);

        long start7 = System.nanoTime();
        long count1 = 0;
        for (Song s : arrayList) {
            count1++;
        }
        long timeArrayListIter = System.nanoTime() - start7;

        long start8 = System.nanoTime();
        long count2 = 0;
        for (Song s : linkedList) {
            count2++;
        }
        long timeLinkedListIter = System.nanoTime() - start8;

        System.out.printf("Iteration Full List    | ArrayList: %8.3f ms | LinkedList: %8.3f ms%n%n",
                timeArrayListIter / 1e6, timeLinkedListIter / 1e6);
    }

    public static void testUpNextQueue(int opsCount) {
        System.out.println("==================================================");
        System.out.println(" CHANGED CONDITION: 'UP NEXT' QUEUE (" + opsCount + " ops)");
        System.out.println("==================================================");

        LinkedList<Song> linkedListQueue = new LinkedList<>();
        ArrayDeque<Song> arrayDequeQueue = new ArrayDeque<>();

        long start1 = System.nanoTime();
        for (int i = 0; i < opsCount; i++) {
            Song s = new Song("Q" + i, "Track " + i, "Artist");
            if (i % 2 == 0) {
                linkedListQueue.addLast(s);
            } else {
                linkedListQueue.addFirst(s);
            }
            if (i > 100 && i % 3 == 0) {
                linkedListQueue.removeFirst();
            }
        }
        long timeLinkedList = System.nanoTime() - start1;

        long start2 = System.nanoTime();
        for (int i = 0; i < opsCount; i++) {
            Song s = new Song("Q" + i, "Track " + i, "Artist");
            if (i % 2 == 0) {
                arrayDequeQueue.addLast(s);
            } else {
                arrayDequeQueue.addFirst(s);
            }
            if (i > 100 && i % 3 == 0) {
                arrayDequeQueue.removeFirst();
            }
        }
        long timeArrayDeque = System.nanoTime() - start2;

        System.out.printf("LinkedList Queue Time : %8.3f ms%n", timeLinkedList / 1e6);
        System.out.printf("ArrayDeque Queue Time : %8.3f ms%n", timeArrayDeque / 1e6);
        System.out.printf("ArrayDeque Speedup    : %.2fx faster%n%n", (double) timeLinkedList / timeArrayDeque);
    }

    public static void main(String[] args) {
        System.out.println("TASK 02: ArrayList vs LinkedList vs ArrayDeque Benchmarking\n");

        runBenchmarkForSize(1_000);
        runBenchmarkForSize(10_000);
        runBenchmarkForSize(100_000);

        testUpNextQueue(100_000);

        printEngineeringAnalysis();
    }

    private static void printEngineeringAnalysis() {
        System.out.println("--------------------------------------------------");
        System.out.println("ENGINEERING OPERATING BOUNDARIES:");
        System.out.println("--------------------------------------------------");
        System.out.println("1. When is ArrayList appropriate?");
        System.out.println("   - When operations are dominated by append (add at end), random access (get by index),");
        System.out.println("     and linear iteration. CPU cache locality makes ArrayList dramatically faster.");
        System.out.println("2. When to STOP recommending ArrayList?");
        System.out.println("   - When frequent insertions and removals occur at the front or middle of large lists (O(n) array shifts).");
        System.out.println("3. Changed Condition ('Up Next' Queue):");
        System.out.println("   - ArrayDeque is strictly superior to LinkedList for double-ended queues.");
        System.out.println("   - ArrayDeque uses a circular array buffer (O(1) amortized add/remove at both ends) without pointer overhead.");
        System.out.println("--------------------------------------------------");
        System.out.println("TIME AND SPACE COMPLEXITY SUMMARY:");
        System.out.println("  Operation        | ArrayList     | LinkedList    | ArrayDeque");
        System.out.println("  -----------------+---------------+---------------+---------------");
        System.out.println("  Add at End       | O(1) amortized| O(1)          | O(1) amortized");
        System.out.println("  Insert at Front  | O(n) shift    | O(1)          | O(1) amortized");
        System.out.println("  Insert Middle    | O(n) shift    | O(n) traversal| N/A");
        System.out.println("  Get k-th         | O(1)          | O(n)          | O(1) (index)");
        System.out.println("  Space / Element  | 4-8 bytes ref | 24-32 bytes   | 4-8 bytes ref");
        System.out.println("--------------------------------------------------");
    }
}
