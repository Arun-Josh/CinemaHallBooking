import java.util.HashMap;
import java.util.LinkedList;

public class Report {

    static MysqlDB mysqlDB = new MysqlDB();

    static void generateReportBySeatType(String seatType) throws Exception{
        System.out.println("\n------------------------------REPORT--------------------------------\n");
        HashMap seatReport = mysqlDB.getSeatReport(seatType);
        if(seatReport.get("ticketsCount")==null){
            System.out.println("--------------------------NO BOOKING DONE-------------------------");
        }else {
            System.out.println("Total Tickets Sold       : "+seatReport.get("ticketsCount"));
            System.out.println("Total Seats Sold         : "+seatReport.get("seatsCount"));
            System.out.println("Total Income             : "+seatReport.get("ticketPrice"));
            System.out.println("Total Money Refunded     : "+seatReport.get("refundPrice"));
            System.out.println("Total Profit             : "+seatReport.get("profit"));
        }
        System.out.println("\n---------------------------END-OF-REPORT----------------------------\n");
    }

    static void generateReportByMovieName(String movieName) throws Exception{
        System.out.println("\n------------------------------REPORT--------------------------------\n");
        HashMap movieReport = mysqlDB.getMovieReport(movieName);
        if(movieReport.get("ticketsCount")==null){
            System.out.println("--------------------------NO BOOKING DONE-------------------------");
        }else {
            System.out.println("Total Tickets Sold       : "+movieReport.get("ticketsCount"));
            System.out.println("Total Seats Sold         : "+movieReport.get("seatsCount"));
            System.out.println("Total Income             : "+movieReport.get("ticketPrice"));
            System.out.println("Total Money Refunded     : "+movieReport.get("refundPrice"));
            System.out.println("Total Profit             : "+movieReport.get("profit"));
        }
        System.out.println("\n---------------------------END-OF-REPORT----------------------------\n");
    }

    static void generatetSeatReportByScreen(String screenName, String seatName)throws Exception{
        System.out.println("\n------------------------------REPORT--------------------------------\n");
        HashMap report = mysqlDB.getSeatReportByScreen(screenName, seatName);
        if(report.get("ticketsCount")==null){
            System.out.println("--------------------------NO BOOKING DONE-------------------------");
        }else {
            System.out.println("Total Tickets Sold       : "+report.get("ticketsCount"));
            System.out.println("Total Seats Sold         : "+report.get("seatsCount"));
            System.out.println("Total Income             : "+report.get("ticketPrice"));
            System.out.println("Total Money Refunded     : "+report.get("refundPrice"));
            System.out.println("Total Profit             : "+report.get("profit"));
        }
        System.out.println("\n---------------------------END-OF-REPORT----------------------------\n");
    }

    static void generateReportByScreen(String screenName)throws Exception{
        System.out.println("\n------------------------------REPORT--------------------------------\n");
        HashMap report = mysqlDB.getReportByScreen(screenName);
        if(report.get("ticketsCount")==null){
            System.out.println("--------------------------NO BOOKING DONE-------------------------");
        }else {
            System.out.println("Total Tickets Sold       : "+report.get("ticketsCount"));
            System.out.println("Total Seats Sold         : "+report.get("seatsCount"));
            System.out.println("Total Income             : "+report.get("ticketPrice"));
            System.out.println("Total Money Refunded     : "+report.get("refundPrice"));
            System.out.println("Total Profit             : "+report.get("profit"));
        }
        System.out.println("\n---------------------------END-OF-REPORT----------------------------\n");
    }

    static void generateNetReport() throws Exception{
        System.out.println("\n------------------------------REPORT--------------------------------\n");
        HashMap netReport = mysqlDB.getNetReport();
        if(netReport.get("ticketsCount")==null){
            System.out.println("--------------------------NO BOOKING DONE-------------------------");
        }else {
            System.out.println("Total Tickets Sold       : "+netReport.get("ticketsCount"));
            System.out.println("Total Seats Sold         : "+netReport.get("seatsCount"));
            System.out.println("Total Income             : "+netReport.get("ticketPrice"));
            System.out.println("Total Money Refunded     : "+netReport.get("refundPrice"));
            System.out.println("Total Profit             : "+netReport.get("profit"));
        }
        System.out.println("\n---------------------------END-OF-REPORT----------------------------\n");
    }

    static void generateReport(){
        LinkedList<Ticket> bookedTickets =  BookedTickets.getBookedTickets();
        double income = 0;
        double refunded = 0;
        double profit = 1;
        for(int i=0;i<bookedTickets.size();i++){
            income+=bookedTickets.get(i).getTicketPrice();
            double ticketRefund = bookedTickets.get(i).getRefund();
            if(ticketRefund!=0){
                refunded += bookedTickets.get(i).getTicketPrice() - ticketRefund;
            }

        }
        profit = income - refunded;
        System.out.println("\n------------------------------REPORT--------------------------------\n");
        if(bookedTickets.size()==0){
            System.out.println("--------------------------NO BOOKING DONE-------------------------");
        }else {
            System.out.println("Total Tickets Sold Today : "+bookedTickets.size());
            System.out.println("Total Income             : "+income);
            System.out.println("Total Money Refunded     : "+refunded);
            System.out.println("Total Profit             : "+profit);
        }

        System.out.println("\n---------------------------END-OF-REPORT----------------------------\n");
    }
}
