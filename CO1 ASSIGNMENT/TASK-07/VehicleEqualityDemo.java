class Vehicle {

    private final String regNo;

    Vehicle(String regNo) {
        this.regNo = regNo;
    }

    public String getRegNo() {
        return regNo;
    }

    @Override
    public String toString() {
        return "Vehicle{regNo='" + regNo + "'}";
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof Vehicle
                && regNo.equals(((Vehicle) o).regNo);
    }

    @Override
    public int hashCode() {
        return regNo.hashCode();
    }
}

public class VehicleEqualityDemo {

    public static void main(String[] args) {

        Vehicle v1 = new Vehicle("TN01AB1111");
        Vehicle v2 = new Vehicle("TN01AB1111");

        System.out.println(v1);
        System.out.println(v1.equals(v2));
        System.out.println(v1.hashCode() == v2.hashCode());
    }
}
