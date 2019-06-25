import java.util.HashMap;

public class Ticket {
    private int ticketId;
    private String ticketStatus = "PAID";
    private int screenNumber;
    private HashMap<Seat,String> seats;
    private String seatType;
    private String showTime;
    private double ticketPrice;
    private double refund;

    public Ticket(int ticketId, int screenNumber, HashMap<Seat,String> seats, String seatType, String showTime, Double ticketPrice) {
        this.ticketId = ticketId;
        this.screenNumber = screenNumber;
        this.seats = seats;
        this.seatType = seatType;
        this.showTime = showTime;
        this.ticketPrice = ticketPrice;
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

    public int getScreenNumber() {
        return screenNumber;
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
        return "Ticket{" +
                "ticketId=" + ticketId +
                ", screenNumber=" + screenNumber +
                ", seats=" + seats +
                ", seatType='" + seatType + '\'' +
                ", showTime='" + showTime + '\'' +
                ", ticketPrice=" + ticketPrice +
                '}';
    }
}
