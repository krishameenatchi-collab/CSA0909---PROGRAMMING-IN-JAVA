Assignment 2 - Advanced Java Collections Engineering Design
Course: Programming in Java 
Repository Structure: assignment2/ folder containing all tasks
Java Version: Java 17+ / Java 21

Directory Structure
assignment2/
├── Task01_StringBenchmark.java      # Q1: String, StringBuilder & StringBuffer Benchmarks
├── Task02_PlaylistEditor.java       # Q2: ArrayList, LinkedList & ArrayDeque Experiments
├── Task03_Attendance.java           # Q3: HashSet & hashCode-equals Contract De-duplication
├── Task04_PharmacyInventory.java    # Q4: HashMap, TreeMap & LinkedHashMap Stock Lookups
├── Task05_Leaderboard.java          # Q5: Generic Leaderboard & Comparator Chain Ranking
├── Task06_Triage.java               # Q6: PriorityQueue Hospital Emergency Room Triage
├── Task07_RedesignChallenge.java    # Q7: Day-4 Design Challenge - Dual-Index Redesign
└── README.md                        # Documentation & Execution Guide
Instructions to Compile and Run
Make sure Java 17 or higher is installed.

Compile All Tasks
javac -d bin assignment2/*.java
Run Individual Tasks
# Task 01: Railway Ticket Text Building Benchmark
java -cp bin assignment2.Task01_StringBenchmark

# Task 02: Music Playlist Editor & Queue Experiments
java -cp bin assignment2.Task02_PlaylistEditor

# Task 03: Event Attendance De-duplication & hashCode-equals
java -cp bin assignment2.Task03_Attendance

# Task 04: Pharmacy Stock Inventory Map Benchmarks
java -cp bin assignment2.Task04_PharmacyInventory

# Task 05: Generic Leaderboard Ranking Component
java -cp bin assignment2.Task05_Leaderboard

# Task 06: Hospital Emergency Triage Priority Queue
java -cp bin assignment2.Task06_Triage

# Task 07: Day-4 Design Challenge Redesign
java -cp bin assignment2.Task07_RedesignChallenge
Summary of Results & Engineering Analysis
Task 01: String vs StringBuilder vs StringBuffer (6 Marks)
Observed Behavior:
String (+ operator): Time complexity is O(N²). Building 10,000 tickets with 50 fields creates hundreds of thousands of intermediate String objects, triggering heavy Garbage Collection.
StringBuilder: Time complexity is O(N) amortized. Minimum memory allocation and maximum execution speed.
StringBuffer: Time complexity is O(N) amortized. Slightly slower than StringBuilder due to synchronized method overhead.
Multithreaded Server Recommendation:
Per-Ticket Generation: Recommend StringBuilder. Local variables are confined to the thread stack, rendering synchronization unnecessary.
Shared Booking Log: Recommend StringBuffer (or Concurrent Async Appender) to prevent race conditions during concurrent logging.

Task 02: ArrayList vs LinkedList vs ArrayDeque (4 Marks)
ArrayList vs LinkedList:
ArrayList dominates in Add at End, Get k-th element (O(1) time), and Iteration due to CPU cache locality (contiguous memory slots).
LinkedList incurs heavy node allocation overhead (24-32 bytes per node pointer) and pointer chasing.
Changed Condition ("Up Next" Queue):
ArrayDeque is strictly superior to LinkedList for double-ended queues (O(1) amortized head/tail operations without GC pointer allocation).

Task 03: HashSet & the hashCode-equals Contract (6 Marks)
Before hashCode() override: HashSet.size() returned 3 (failed de-duplication) because Object.hashCode() used identity memory addresses.
After hashCode() override: HashSet.size() returned 2 (correct de-duplication) as duplicate Student instances mapped to the same hash bucket and evaluated equals() == true.
Mutation Vulnerability: Mutating regNo after set insertion orphans the object in the hash bucket, making contains() return false.

Task 04: HashMap vs TreeMap vs LinkedHashMap (6 Marks)
Lookups (100,000 ops): HashMap (O(1) average) & LinkedHashMap (O(1)) average ~60-150 ns per lookup. TreeMap (O(log N)) averages ~350-900 ns due to tree traversal.
Reporting: TreeMap naturally yields Code-Order reports (O(N) traversal), while LinkedHashMap naturally yields Insertion-Order reports.
Hashtable Status: Obsolete due to coarse method-level synchronization lock contention; replaced by ConcurrentHashMap and HashMap.

Task 05: Generics & Multi-field Comparator (7 Marks)
Completed Code Output: [Eagles(9,6), Bisons(9,4), Panthers(9,4), Cobras(6,1)].
Tie-breaker Analysis: Bisons (9 pts, 4 diff) ranks above Panthers (9 pts, 4 diff) due to lexicographical name tie-breaking ("Bisons".compareTo("Panthers") < 0).
Comparator Chain: Uses Comparator.comparing(...).thenComparing(...) for type safety, transitivity, and clean generic reuse across Team, Player, and College.

Task 06: PriorityQueue & Fairness Triage (8 Marks)
Completed Code Output: Meena(S1) Kumar(S1) Lata(S2) Ravi(S3) John(S3) Asha(S5).
FIFO Tie-breaker: Arrival counter (long arrival = counter++) guarantees strict FIFO service among patients with identical severity.
Starvation Mitigation: Implemented an Aging Rule dynamically reducing effective severity number based on waiting duration to prevent minor cases from waiting indefinitely.

Task 07: Day-4 Design Challenge - Dual-Index Redesign (13 Marks)
Diagnosed Bottleneck: In Q4, choosing HashMap required expensive O(N log N) sorting for evening reports, while TreeMap slowed daytime lookups to O(log N).
Redesign: Built a Dual-Index Hybrid Inventory combining HashMap<String, Medicine> (O(1) lookups) and a secondary TreeSet<Medicine> index (instant sorted traversal).
Validation: Preserved O(1) daytime lookup speed while making report generation up to 10x-50x faster by eliminating runtime sorting overhead.
Diagnosed Bottleneck: In Q4, choosing HashMap required expensive O(N log N) sorting for evening reports, while TreeMap slowed daytime lookups to O(log N).
Redesign: Built a Dual-Index Hybrid Inventory combining HashMap<String, Medicine> (O(1) lookups) and a secondary TreeSet<Medicine> index (instant sorted traversal).
Validation: Preserved O(1) daytime lookup speed while making report generation up to 10x-50x faster by eliminating runtime sorting overhead.
