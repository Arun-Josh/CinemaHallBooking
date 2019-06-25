import java.util.LinkedList;
import java.util.Scanner;

public class Display {
    Scanner scan;
    Display(){
        scan = new Scanner(System.in);
    }

    public final void listShows(LinkedList<Shows> shows){
        System.out.printf("  S.No    Screen_Number          Movie_Name                Show_Time          Duration           PLATINUM           GOLD           SILVER\n\n");
        for(int i = 1; i <= shows.size() ; i++){
            System.out.printf("   %d     ",i);
            System.out.printf("     %d ",shows.get(i-1).getScreenNo());
            System.out.print("        ");
            System.out.printf("%-30s ",shows.get(i-1).getMovieName());
            System.out.printf("%9s ",shows.get(i-1).getShowTime());
            String fullTime = shows.get(i-1).getMovieDuration().toString();
            String[] time  = fullTime.split(":");
            System.out.printf("        %s Hours %s Minutes",time[0],time[1]);
            System.out.printf("%10d", seatsAvailable(shows.get(i-1).getScreenInfo().get("PLATINUM")));
            System.out.printf("       %10d", seatsAvailable(shows.get(i-1).getScreenInfo().get("GOLD")));
            System.out.printf("      %10d", seatsAvailable(shows.get(i-1).getScreenInfo().get("SILVER")));
            System.out.println();
        }
    }

    public final int seatsAvailable(Object seatArrayObj){
        Integer[][] seatArray = (Integer[][])seatArrayObj;
        int seatsAvailable = 0;
//        System.out.println(seatArray.length + " "+seatArray[0].length);
        for(int i=0;i<seatArray.length;i++){
            for(int j=0;j<seatArray[0].length;j++){
                if(seatArray[i][j]==0){
                    seatsAvailable++;
                }
            }
        }
//        int seatsAvailable = (Integer) seatArrayObj;
        return seatsAvailable;
    }

    public final void listOptions(){
        System.out.println("\n1. Book a Ticket\n" +
                "2. Available Seats\n" +
                "3. Booked Tickets\n" +
                "4. Generate Report\n" +
                "0. Exit");
    }

    public final int getChoice(){
        System.out.println();
//        Scanner scan = new Scanner(System.in);
        System.out.print("Enter Your choice : ");
        int choice = scan.nextInt();
//        System.out.println();
        return choice;
    }

    public final int getPassengerCount(){
//        System.out.println();
//        Scanner scan = new Scanner(System.in);
        System.out.print("Enter the number of Passengers : ");
        int passengerCount = scan.nextInt();
        System.out.println();
        return passengerCount;
    }

    public final String getSeatType(){
//        System.out.println();
//        Scanner scan = new Scanner(System.in);
        scan.nextLine();
        System.out.print("Enter Seat Type   : ");
        String seattype = scan.nextLine();
//        System.out.println();
        return seattype.toUpperCase();
    }

    final public int reBook(){
//        Scanner scan = new Scanner(System.in);
        System.out.println("                                      Sorry requested seat(s) Not Available ! :(\n");
        System.out.println("   1   Book another Class Ticket\n" +
                "   0   Go Back\n");
        System.out.print("Enter your choice : ");
        return scan.nextInt();
    }

    final public void showSeats(Object obj){
        System.out.println("\n            Seats Available\n");
        Integer[][] seats = (Integer[][]) obj;
        for(int i=0;i<seats.length;i++){
            for(int j=0;j<seats[0].length;j++){
                if(j==0){
                    System.out.print("            ");
                }
                if(j==(seats[0].length/2)){
                    System.out.print(" ");
                }
                System.out.print(+seats[i][j]+" ");
            }
            System.out.println();
        }
    }

    final public void bookedTickets(){
        LinkedList<Ticket> tickets = BookedTickets.getBookedTickets();
        for (int i=0;i<tickets.size();i++){
            System.out.println(tickets.get(i));
        }
    }

}
