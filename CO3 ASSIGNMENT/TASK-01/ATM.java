import java.io.*;
import java.util.InputMismatchException;
import java.util.Scanner;

class InsufficientBalanceException extends Exception {
    public InsufficientBalanceException(String message) {
        super(message);
    }
}

public class ATM {
    private static double balance = 5000.00;

    public static void main(String[] args) {
        Scanner scanner = null;
        try {
            File cardLog = new File("card_reader.log");
            if (!cardLog.exists()) {
                throw new FileNotFoundException("Card-reader file missing.");
            }
            scanner = new Scanner(cardLog);

            System.out.println("Current Balance: $" + balance);
            System.out.print("Enter withdrawal amount: ");

            if (!scanner.hasNextDouble()) {
                throw new InputMismatchException("Invalid input: Withdrawal amount must be numerical.");
            }

            double amount = scanner.nextDouble();

            if (amount < 0) {
                throw new IllegalArgumentException("Withdrawal amount cannot be negative.");
            }
            if (amount > balance) {
                throw new InsufficientBalanceException("Requested amount exceeds available balance.");
            }

            balance -= amount;
            System.out.println("Dispensing cash: $" + amount);
            System.out.println("Remaining Balance: $" + balance);

        } catch (FileNotFoundException e) {
            System.err.println("[Checked Exception] Hardware Error: " + e.getMessage());
        } catch (InputMismatchException | IllegalArgumentException e) {
            System.err.println("[Unchecked Exception] Input Format Error: " + e.getMessage());
        } catch (InsufficientBalanceException e) {
            System.err.println("[Checked Exception] Business Rule Error: " + e.getMessage());
        } catch (Throwable t) {
            System.err.println("[Fatal Throwable] Unexpected system failure: " + t.getMessage());
        } finally {
            if (scanner != null) {
                scanner.close();
            }
            System.out.println("[Finally Block] Session ended. Card ejected safely.");
        }
    }
}
