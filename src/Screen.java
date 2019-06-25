import java.util.HashMap;
import java.util.LinkedList;

public class Screen {

    private int screenNumber;
    private HashMap<String,Integer> seats;

    public Screen(int screenNumber, HashMap<String, Integer> seats) {
        this.screenNumber = screenNumber;
        this.seats = seats;
    }

    public int getScreenNumber() {
        return screenNumber;
    }

    public HashMap<String, Integer> getSeats() {
        return seats;
    }
}
