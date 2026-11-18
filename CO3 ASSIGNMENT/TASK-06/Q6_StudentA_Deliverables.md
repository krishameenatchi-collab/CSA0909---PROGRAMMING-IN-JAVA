A1. Producer-Consumer Design Analysis
while loop vs if statement: Mandatory to guard against spurious wakeups. If if were used and a thread woke up without re-checking count, it could read from an empty buffer or overwrite a full buffer.
notify() vs notifyAll(): Using notify() might wake up another waiting consumer when buffer is empty, leaving the cook sleeping indefinitely (lost notification deadlock). notifyAll() ensures all waiting threads re-evaluate predicates.
A2. Thread.sleep() vs Object.wait() Comparison Table
Feature	Thread.sleep()	Object.wait()
Lock Holding	Retains monitor locks during sleep	Releases monitor lock while waiting
Context	Can be called anywhere	Must be called inside synchronized block
Awakening	Time expiration or interrupt	notify() / notifyAll() or time expiration
