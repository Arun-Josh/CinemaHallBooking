import java.util.HashMap;
import java.util.LinkedList;
import java.util.Scanner;

public class Display {
    Scanner scan;
    Display(){
        scan = new Scanner(System.in);
    }

    public final boolean listShows(LinkedList<Shows> shows){
        if(shows.size()==0){
            System.out.println("\n-----------------NO-SHOWS-AVAILABLE-----------------");
            return false;
        }
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
            System.out.printf("%10d", seatsAvailable(shows.get(i-1).getScreen().get("PLATINUM")));
            System.out.printf("       %10d", seatsAvailable(shows.get(i-1).getScreen().get("GOLD")));
            System.out.printf("      %10d", seatsAvailable(shows.get(i-1).getScreen().get("SILVER")));
            System.out.println();
        }
        return  true;
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
                "5. Cancel Ticket\n" +
                "6. Add Screens\n" +
                "0. Exit");
    }

    public final int getNumberOfScreens(){
        System.out.print("Enter Number of Screens : ");
        return scan.nextInt();
    }

    public final void addScreen(){
        Utils utils = new Utils();
        System.out.print("\nEnter Screen Number : ");
        int screenNo = scan.nextInt();
        System.out.println();
//        System.out.print("Enter Number of Movies : ");
//        int movieCount = scan.nextInt();
        int movieCount = 4;
        String[] showTime = {"05:50","12:30","06:30","10:30"};
        scan.nextLine();
        for (int i=1;i<=movieCount;i++){
            System.out.print("Enter Movie "+i+" Name : ");
            String movieName = scan.nextLine();
            utils.addScreen(screenNo,movieName,showTime[i-1]);
        }

    }

    public final int getChoice(){
        System.out.println();
//        Scanner scan = new Scanner(System.in);
        System.out.print("Enter Your choice : ");
        int choice = scan.nextInt();
//        System.out.println();
        return choice;
    }

    public final int getSerialNumber(){
        System.out.println();
//        Scanner scan = new Scanner(System.in);
        System.out.print("Enter the Serial Number : ");
        int sno = scan.nextInt();
//        System.out.println();
        return sno;
    }

    public final int getAudienceCount(){
        System.out.print("Enter the number of Persons : ");
        int audienceCount = scan.nextInt();
        System.out.println();
        return audienceCount;
    }

    public final String getSeatType(){
        scan.nextLine();
        System.out.print("Enter Seat Type   : ");
        String seattype = scan.nextLine();
        return seattype.toUpperCase();
    }

    final public int reBook(){
//        System.out.println("                                      Sorry requested seat(s) Not Available ! :(\n");
        System.out.println("   1   Book another Class Ticket\n" +
                "   0   Go Back\n");
        System.out.print("Enter your choice : ");
        return scan.nextInt();
    }

    final public void showSeats(HashMap map){
        System.out.println("\n            Seats Available\n");
        Object obj = null;
        for(int k=0;k<3;k++){

            if(k==0){
                obj = map.get("PLATINUM");
                System.out.println("PLATINUM");
            }
            else if(k==1){
                obj = map.get("GOLD");
                System.out.println("GOLD");
            }
            else if(k==2){
                obj = map.get("SILVER");
                System.out.println("SILVER");
            }

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
    }

    final public void bookedTickets(){
        LinkedList<Ticket> tickets = BookedTickets.getBookedTickets();
        for (int i=0;i<tickets.size();i++){
            System.out.println(tickets.get(i));
        }
    }

    final public int getTicketId(){
        System.out.print("\nEnter Ticket id to cancel : ");
        return scan.nextInt();
    }

}
