import java.time.LocalTime;
import java.util.HashMap;
import java.util.LinkedList;

public class Shows {
    private HashMap screenInfo;
    private String movieName;
    private LocalTime showTime;
    private LocalTime movieDuration;
    private int screenNo;
    private HashMap<String,Double> showfFare;
    public Shows(Integer screenNo, HashMap<String,Integer> screenInfo, String movieName, String showTime, String movieDuration, HashMap<String, Double> showFare) {
        this.screenNo = screenNo;
        this.screenInfo = screenInfo;
        this.movieName = movieName;
        String time[] = showTime.split(":");
        this.showTime = LocalTime.of(Integer.valueOf(time[0]),Integer.valueOf(time[1]));
        time = movieDuration.split(":");
        this.movieDuration = LocalTime.of(Integer.valueOf(time[0]),Integer.valueOf(time[1]));
        this.showfFare = showFare;
    }

    public int getScreenNo() {
        return screenNo;
    }

    public HashMap<String, Double> getShowfFare() {
        return showfFare;
    }

    public HashMap getScreenInfo() {
        return screenInfo;
    }

    public String getMovieName() {
        return movieName;
    }

    public LocalTime getShowTime() {
        return showTime;
    }

    public LocalTime getMovieDuration() {
        return movieDuration;
    }
}
