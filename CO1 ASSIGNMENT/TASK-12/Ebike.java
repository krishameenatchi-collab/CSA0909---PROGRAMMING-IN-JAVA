public class EBike extends Vehicle {

    public EBike(String regNo) {
        super(regNo);
    }

    @Override
    protected double ratePerHour() {
        return 15;
    }

    public double flatChargingFee() {
        return 50;
    }
}
