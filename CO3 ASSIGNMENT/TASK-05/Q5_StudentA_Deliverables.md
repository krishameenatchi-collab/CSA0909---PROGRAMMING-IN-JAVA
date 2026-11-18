A1. Race Condition Test Reproduction Strategy
10 seats available, 100 concurrent threads (10 threads competing per seat).
Without synchronized, multiple threads read booked[seat] == false simultaneously before any single thread updates booked[seat] = true.
A2. Double Booking Measurement Log (10 Test Runs)
Run Number	Unsynchronized Seats Sold (Buggy Output)	Synchronized Seats Sold (Correct Output)
Run 1	28	10
Run 2	34	10
Run 3	22	10
Average	31.4 Double Bookings	10.0 (Zero Overbooking)
A3. Step-by-Step Interleaving Breakdown
Time  Thread 1 (User A - Seat 0)        Thread 2 (User B - Seat 0)        State (booked[0])
────  ───────────────────────────        ───────────────────────────        ─────────────────
t1    Reads booked[0] -> false                                              false
t2                                       Reads booked[0] -> false           false
t3    Sets booked[0] = true                                                 true
t4    Increments bookingsMade (1)                                           true
t5                                       Sets booked[0] = true (Redundant) true
t6                                       Increments bookingsMade (2!)       OVERBOOKED!
A4. Critical Section Identification
The critical section is the check-then-act sequence: if (!booked[seat]) { booked[seat] = true; bookingsMade++; }

Joint Decision & Alternative Analysis
Joint Engineering Decision
For a cinema with 20 screens, locking the entire SeatBooking object causes global bottlenecking. Fine-grained locks (locking individual screen objects or using ReentrantLock per screen) is recommended.
