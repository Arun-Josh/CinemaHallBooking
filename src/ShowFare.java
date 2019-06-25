import java.util.HashMap;

public class ShowFare {
    private HashMap<String,Double> seatType;

    public ShowFare(HashMap<String, Double> seatType) {
        this.seatType = seatType;
    }

    public HashMap<String, Double> getSeatType() {
        return seatType;
    }
}
