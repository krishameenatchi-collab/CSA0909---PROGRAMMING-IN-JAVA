                          ┌───────────────────────────┐
                          │   java.lang.Exception     │
                          └─────────────▲─────────────┘
                                        │
                          ┌─────────────┴─────────────┐
                          │       ExamException       │
                          └─────────────▲─────────────┘
                                        │
       ┌────────────────────────────────┼────────────────────────────────┐
       │                                │                                │
┌──────┴─────────────────────┐   ┌──────┴─────────────────────┐   ┌──────┴─────────────────────┐
│ SubmissionStorageException │   │ ExamWindowClosedException  │   │  UnregisteredUserException │
│ (Checked - Storage Layer)  │   │ (Checked - Service Layer)  │   │(Unchecked - RuntimeExcep.) │
└────────────────────────────┘   └────────────────────────────┘   └────────────────────────────┘
A2. Checked vs. Unchecked Exception Rationale
SubmissionStorageException (Checked): Recoverable storage issue (e.g., retrying upload or selecting backup storage node). Enforces compile-time handling.
ExamWindowClosedException (Checked): Expected business constraint violation that the caller must catch to display deadline expiration UI notification.
UnregisteredUserException (Unchecked): Represents a programming logic error or unauthorized state breach that should not force every intermediate layer to declare throws.
A3. Exception Propagation Path Tracing
[Storage Layer: Storage.save()]
    └── Throws low-level IOException("Disk quota exceeded")
    └── Caught & Wrapped into SubmissionStorageException("Could not store answer02.pdf", cause)
              │ (propagates via 'throws' clause)
              ▼
[Service Layer: ExamService.submit()]
    └── Does not handle; declares 'throws ExamException'
              │ (propagates up call stack)
              ▼
[Controller Layer: ExamPortalController.main()]
    └── Caught by catch(ExamException e)
    └── Formats user message: "Error: Could not store answer02.pdf"
    └── Logs cause detail: "Cause: Disk quota exceeded"
A4. Handling Location and UI Message Presentation
The exception is caught in the Controller Layer (main). The student sees a clean, user-friendly message (Error: Could not store answer02.pdf) without exposing raw internal infrastructure details, while administrators retain full cause visibility via e.getCause().

Joint Conclusion & Alternative Analysis
Joint Conclusion
Throwing a domain-specific SubmissionStorageException abstracts infrastructure details from higher layers. If a raw IOException was passed up, the UI layer would be tightly coupled to file system implementation details, breaking encapsulation principles.

Alternative Concept & Complexity Analysis
If Custom Exceptions Extended RuntimeException (Unchecked):
Method Signatures: throws SubmissionStorageException clauses can be removed from save() and intermediate methods.
Calling Code: Compiler will no longer mandate try-catch blocks, increasing risk of unhandled runtime crashes.
Stack Trace & Chaining Overhead: Capturing stack traces during exception object instantiation costs O(d) memory/time (where d is call stack depth). Chaining retains references to internal exception frames, keeping the underlying cause alive in garbage collection memory space.
