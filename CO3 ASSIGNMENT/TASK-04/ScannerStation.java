class StationThread extends Thread {
    private String stationName;
    public StationThread(String name) { this.stationName = name; }

    @Override
    public void run() {
        System.out.println(stationName + " scanning started on thread: " + Thread.currentThread().getName());
        try { Thread.sleep(500); } catch (InterruptedException e) {}
        System.out.println(stationName + " completed.");
    }
}

class StationRunnable implements Runnable {
    private String stationName;
    public StationRunnable(String name) { this.stationName = name; }

    @Override
    public void run() {
        System.out.println(stationName + " scanning started on thread: " + Thread.currentThread().getName());
        try { Thread.sleep(500); } catch (InterruptedException e) {}
        System.out.println(stationName + " completed.");
    }
}

public class ScannerStation {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("--- Starting Scanner Stations ---");
        Thread s1 = new StationThread("Station-1");
        Thread s2 = new Thread(new StationRunnable("Station-2"));

        s1.start();
        s2.start();

        System.out.println("--- Calling run() directly (Main Thread Execution) ---");
        StationThread s3 = new StationThread("Station-3-DirectRun");
        s3.run();

        s1.join();
        s2.join();
        System.out.println("All scanner stations completed. Final total calculated.");
    }
}
