import java.time.LocalTime;
import java.util.HashMap;

public class Shows {
    private int showId;
    private HashMap<String, Integer[][]> screen;
    private String movieName;
    private LocalTime showTime;
    private LocalTime movieDuration;
    private String screenName;
    private HashMap<String,Double> showFare;
    public Shows(int showId, String screenName, HashMap<String,Integer[][]> screen, String movieName, String showTime, String movieDuration, HashMap<String, Double> showFare) {
        this.showId = showId;
        this.screenName = screenName;
        this.screen = screen;
        this.movieName = movieName;
        String time[] = showTime.split(":");
        this.showTime = LocalTime.of(Integer.valueOf(time[0]),Integer.valueOf(time[1]));
        time = movieDuration.split(":");
        this.movieDuration = LocalTime.of(Integer.valueOf(time[0]),Integer.valueOf(time[1]));
        this.showFare = showFare;
    }

    public int getShowId() {
        return showId;
    }

    public String getScreenName() {
        return screenName;
    }

    public HashMap<String, Double> getShowFare() {
        return showFare;
    }

    public HashMap getScreen() {
        return screen;
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

    @Override
    public String toString() {
        return "Shows{" +
                "screen=" + screen +
                ", movieName='" + movieName + '\'' +
                ", showTime=" + showTime +
                ", movieDuration=" + movieDuration +
                ", screenName=" + screenName +
                ", showFare=" + showFare +
                '}';
    }
}
