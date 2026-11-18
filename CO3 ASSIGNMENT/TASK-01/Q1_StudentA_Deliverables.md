A1. Throwable Hierarchy Diagram (ATM System Scope)
                          Throwable
                              │
             ┌────────────────┴────────────────┐
             │                                 │
           Error                           Exception
     (Hardware/JVM Failure)                    │
                                ┌──────────────┴──────────────┐
                                │                             │
                        Checked Exceptions            Unchecked Exceptions
                        (Compile-time recovery)      (RuntimeException Subclasses)
                                │                             │
                ┌───────────────┴──────────────┐      ┌───────┴────────────────────────┐
                │                              │      │                                │
      FileNotFoundException    InsufficientBalanceException  InputMismatchException  IllegalArgumentException
A2. Failure Point Mapping Table
Failure Point	Trigger Condition	Exception Class	Checked / Unchecked	Catch Block Location
Missing Card File	card_reader.log missing	FileNotFoundException	Checked	catch (FileNotFoundException e)
Non-numeric Input	User enters letters (e.g., "ABC")	InputMismatchException	Unchecked	`catch (InputMismatchException \
Negative Amount	User enters -500	IllegalArgumentException	Unchecked	`catch (... \
Excess Withdrawal	User requests $10,000	InsufficientBalanceException	Checked	catch (InsufficientBalanceException e)
Out of Memory	Heap exhaustion	OutOfMemoryError	Unchecked (Error)	catch (Throwable t)
A3. Justification of Catch Block Order
In Java, catch blocks must be ordered from the most specific subclass to the most general superclass. If a broad parent class (e.g., Exception or Throwable) is placed before a specific subclass (e.g., FileNotFoundException), the parent catch block intercepts the exception first, causing a compilation error (exception has already been caught).

A4. Guarantees Provided by the finally Block
The finally block guarantees execution regardless of whether an exception is thrown, caught, or left uncaught. In this ATM program:

Resource Cleanup: Ensures the Scanner stream is closed, preventing resource leaks.
State Security: Guarantees card ejection (Card ejected safely) so customer cards are never retained during program crash.
Joint Design Question & Alternative Analysis
Joint Design Question
Catching everything with catch (Exception e): Masks specific failure root causes, treats input typos the same as hardware failures, prevents specific recovery strategies, and reduces diagnostic clarity. Acceptable only in top-level fallback logging frameworks.
Alternative Concept & Complexity Analysis
Avoiding Exceptions (Scanner checking & Error Codes):
Implementation: Pre-checking input using scanner.hasNextDouble() and returning integer error codes (0 = Success, -1 = Missing file, -2 = Negative input, -3 = Overdrawn).
Readability & Maintenance: Mixes error-handling code with business logic, leading to deeply nested if-else branches.
Time & Space Complexity:
Error Codes: Time O(1), Space O(1). Zero object instantiation cost.
Exceptions: Time O(1) amortized, but throwing an exception incurs O(d) time to capture stack trace of depth d. Memory footprint includes allocating Throwable objects on the heap.
