Stage 2: System Design
                       ┌─────────────────────────┐
                       │  Main Result Controller │
                       └────────────┬────────────┘
                                    │ Submits Tasks
                                    ▼
                       ┌─────────────────────────┐
                       │ ExecutorService (Pool)  │
                       └────────────┬────────────┘
                                    │ Workers execute Callable
            ┌───────────────────────┼───────────────────────┐
            ▼                       ▼                       ▼
   ┌─────────────────┐     ┌─────────────────┐     ┌─────────────────┐
   │ ScoreTask 1     │     │ ScoreTask 2     │     │ ScoreTask N     │
   │ (Valid File)    │     │ (Corrupt File)  │     │ (Valid File)    │
   └────────┬────────┘     └────────┬────────┘     └────────┬────────┘
            │ Return Result         │ Throw Ex.             │ Return Result
            ▼                       ▼                       ▼
   ┌─────────────────────────────────────────────────────────────────┐
   │ ConcurrentHashMap<String, String> (Thread-Safe Shared Results) │
   └─────────────────────────────────────────────────────────────────┘
Fault-Tolerance Mechanism Justification
Isolation via Callable<T>: Tasks throw exceptions encapsulated within their execution context.
Future.get() Exception Wrapping: When a task fails, Future.get() throws ExecutionException. Catching it inside the processing loop records failure without aborting execution of remaining worker threads.
ConcurrentHashMap: Thread-safe concurrent reads/writes without global lock contention.
