import java.time.LocalTime;

public class DeliveryVan extends Car {

    public DeliveryVan(String regNo) {
        super(regNo);
    }

    public double calculateDeliveryFee(
            int minutes,
            LocalTime entryTime) {

        double baseFee = calculateFee(minutes);

        LocalTime start = LocalTime.of(9, 0);
        LocalTime end = LocalTime.of(17, 0);

        if (!entryTime.isBefore(start)
                && entryTime.isBefore(end)) {
            return baseFee * 2;
        }

        return baseFee;
    }
}
