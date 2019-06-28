import java.util.HashMap;
import java.util.LinkedList;

public class Ticket {
    private int ticketId;
    private int showId;
    private String movieName;
    private String ticketStatus = "PAID";
    private String screenName;
    private HashMap<Seat,String> seats;
    private LinkedList<Integer> seatNos;
    private String seatType;
    private String showTime;
    private double ticketPrice;
    private double refund;

    public Ticket(int ticketId, int showId, String movieName, String screenName, HashMap<Seat,String> seats, String seatType, String showTime, Double ticketPrice) {
        this.ticketId = ticketId;
        this.showId = showId;
        this.movieName = movieName;
        this.screenName = screenName;
        this.seats = seats;
        this.seatType = seatType;
        this.showTime = showTime;
        this.ticketPrice = ticketPrice;
    }

    public LinkedList<Integer> getSeatNos() {
        return seatNos;
    }

    public void setSeatNos(LinkedList<Integer> seatNos) {
        this.seatNos = seatNos;
    }

    public int getShowId() {
        return showId;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setRefund(double refund) {
        this.refund = refund;
    }

    public HashMap<Seat, String> getSeats() {
        return seats;
    }

    public int getTicketId() {
        return ticketId;
    }

    public String getScreenName() {
        return screenName;
    }

    public String getSeatType() {
        return seatType;
    }

    public String getShowTime() {
        return showTime;
    }

    public double getTicketPrice() {
        return ticketPrice;
    }

    public double getRefund() {
        return refund;
    }

    public String getTicketStatus() {
        return ticketStatus;
    }

    public void setTicketStatus(String ticketStatus) {
        this.ticketStatus = ticketStatus;
    }

    @Override
    public String toString() {

        String tickets = "";
        tickets+="\nTicket id       : "+ticketId;
//        tickets+="\nShow Id         : "+showId;
        tickets+="\nScreen Number   : "+ screenName;
        tickets+="\nMovie name      : "+movieName;
//        for(int i=0;i<seats.size();i++){
//            seats.get(i);
//        }
//        tickets+="\nSeat Numbers    : "+seats;
        tickets+="\nSeat Numbers    :";
        if (seatNos==null){
                    tickets+=" "+seats;
        }else {
            for(int i=0;i<seatNos.size();i++){
                tickets+=" "+seatNos.get(i);
            }
        }

        tickets+="\nSeat Type       : "+seatType;
        tickets+="\nTicket Price    : Rs. "+ticketPrice;
        tickets+="\nPayment Status  : "+ticketStatus;
        if(refund!=0){
            tickets+="\nRefunded Amount : Rs. "+refund;
        }
        tickets+="\n";


        return tickets;
//        return "Ticket{" +
//                "ticketId=" + ticketId +
//                ", screenName=" + screenName +
//                ", seats=" + seats +
//                ", seatType='" + seatType + '\'' +
//                ", showTime='" + showTime + '\'' +
//                ", ticketPrice=" + ticketPrice +
//                '}';
    }
}
