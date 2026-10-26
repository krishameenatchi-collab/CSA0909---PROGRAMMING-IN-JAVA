public class ParkingTicket {

    private static int nextTicketNumber = 1000;

    private final int ticketNumber;
    private final String vehicleNo;
    private final int floor;
    private final int slot;

    public ParkingTicket() {
        this(nextTicketNumber++, "UNKNOWN", -1, -1);
    }

    public ParkingTicket(String vehicleNo) {
        this(nextTicketNumber++, vehicleNo, -1, -1);
    }

    public ParkingTicket(int ticketNumber, String vehicleNo,
                         int floor, int slot) {
        this.ticketNumber = ticketNumber;
        this.vehicleNo = vehicleNo;
        this.floor = floor;
        this.slot = slot;
    }

    public int getTicketNumber() {
        return ticketNumber;
    }

    public String getVehicleNo() {
        return vehicleNo;
    }

    public int getFloor() {
        return floor;
    }

    public int getSlot() {
        return slot;
    }

    @Override
    public String toString() {
        return "Ticket " + ticketNumber
                + " | Vehicle: " + vehicleNo
                + " | Floor: " + floor
                + " | Slot: " + slot;
    }
}
