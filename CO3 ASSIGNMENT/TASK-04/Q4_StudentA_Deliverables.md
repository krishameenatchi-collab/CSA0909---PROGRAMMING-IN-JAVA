A1. Thread Creation Comparison
extends Thread: Simple, but limits inheritance flexibility since Java does not support multiple class inheritance.
implements Runnable: Decouples task execution from thread management. Recommended for clean architecture and compatibility with ExecutorService pools.
A2. Thread Life Cycle State Diagram (Scanner Station Context)
  [NEW] ───(start())───► [RUNNABLE] ◄───(Scheduler Pick)
                           │   ▲
             (sleep(500))  │   │  (Time Elapsed)
                           ▼   │
                     [TIMED_WAITING]
                           │
                 (Task Completion)
                           ▼
                      [TERMINATED]
A3. Proof: run() vs. start()
When start() is invoked, the JVM requests a new OS-level native thread and transitions state to RUNNABLE. Calling run() directly executes run() inside the caller's thread (main), producing log output on thread: main instead of spawning parallel execution threads.

A4. Purpose of join()
The join() method causes the main thread to pause until s1 and s2 enter TERMINATED state. This guarantees that the final aggregated script tally is calculated only after all scanner stations complete.

Joint Conclusion & Alternative Analysis
Joint Conclusion
Multithreaded execution relies on the OS thread scheduler, making thread execution order non-deterministic. Robust concurrent applications must rely on explicit synchronization barriers (join, CountDownLatch) rather than assuming timing order.

Alternative Concept & Complexity Analysis
Refactoring to ExecutorService (Fixed Thread Pool):
Using Executors.newFixedThreadPool(p) reuses p worker threads for n tasks.
Completion Time: O(n/p) execution batches.
Memory Overhead: Reduces thread stack memory footprint from O(n×stack_size) to O(p×stack_size).
