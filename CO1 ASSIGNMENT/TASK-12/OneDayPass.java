import java.time.LocalDate;

public class OneDayPass implements Pass {

    private final LocalDate date;

    public OneDayPass(LocalDate date) {
        this.date = date;
    }

    @Override
    public boolean isValid() {
        return date.equals(LocalDate.now());
    }

    @Override
    public double discount() {
        return 0;
    }
}
