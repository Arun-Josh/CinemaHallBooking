import java.util.LinkedList;

public final class BookedTickets {
    private static LinkedList<Ticket> tickets = new LinkedList<>();

    private BookedTickets(){}

    static void addTicket(Ticket ticket){
        tickets.add(ticket);
    }

    static int getTicketId(){
        return tickets.size();
    }

    static LinkedList<Ticket> getBookedTickets(){
        return tickets;
    }

}
