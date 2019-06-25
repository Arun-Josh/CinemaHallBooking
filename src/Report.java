import java.util.LinkedList;

public class Report {

    static void generateReport(){
        LinkedList<Ticket> bookedTickets =  BookedTickets.getBookedTickets();
        double income = 0;
        for(int i=0;i<bookedTickets.size();i++){
            income+=bookedTickets.get(i).getTicketPrice();
        }
        System.out.println("\n------------------------------REPORT--------------------------------\n");
        if(bookedTickets.size()==0){
            System.out.println("--------------------------NO BOOKING DONE-------------------------");
        }else {
            System.out.println("Total Tickets Sold Today : "+bookedTickets.size());
            System.out.println("Total Income             : "+income);
        }

        System.out.println("\n---------------------------END-OF-REPORT----------------------------\n");
    }
}
