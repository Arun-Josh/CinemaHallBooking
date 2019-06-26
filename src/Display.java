import java.util.HashMap;
import java.util.LinkedList;
import java.util.Scanner;

public class Display {
    Scanner scan;
    Validation validation = new Validation();
    Display(){
        scan = new Scanner(System.in);
    }

    public final boolean listShows(LinkedList<Shows> shows){
        if(shows.size()==0){
            System.out.println("\n-----------------NO-SHOWS-AVAILABLE-----------------");
            return false;
        }
        System.out.printf("\n  S.No       Screen_Name           Movie_Name                      Show_Time          Duration           PLATINUM            GOLD           SILVER\n\n");
        for(int i = 1; i <= shows.size() ; i++){
            System.out.printf("   %d     ",i);
            System.out.printf("     %10s ",shows.get(i-1).getScreenName());
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
//        System.out.print("Enter Number of Screens : ");
//        int screensCount = scan.nextInt();
//        return screensCount;
        do{
            System.out.print("Enter Number of Screens : ");
            String screensCount = scan.nextLine();
            if(validation.getNumberOfScreens(screensCount)){
                return Integer.valueOf(screensCount);
            }else {
                System.out.println("--------------Enter a valid Value !--------------");
            }
        }while (true);
    }

    public final void addScreen(boolean flag){
        Utils utils = new Utils();
//        if(flag){
//            scan.nextLine();
//        }
        System.out.print("\nEnter Screen Name : ");
        String screenName = scan.nextLine();
//        System.out.println();
//        System.out.print("Enter Number of Movies : ");
//        int movieCount = scan.nextInt();
        int movieCount = 4;
        String[] showTime = {"05:50","12:30","06:30","10:30"};
//        scan.nextLine();
        for (int i=1;i<=movieCount;i++){
            System.out.print("Enter Movie "+i+" Name : ");
            String movieName = scan.nextLine();
            utils.addScreen(screenName,movieName,showTime[i-1]);
        }

    }

    public final int getChoice(int from, int to){
        System.out.println();
            do{
                System.out.print("Enter Your choice : ");
                String strChoice = scan.nextLine();
                if(validation.getChoice(from,to,strChoice)){
                    return Integer.valueOf(strChoice);
                }else {
                    System.out.println("--------------Enter a valid Value !--------------");
                }
            }while (true);
    }

    public final int getSerialNumber(){
        System.out.println();
//        System.out.print("Enter the Serial Number : ");
//        int sno = scan.nextInt();
//        return sno;

        do{
            System.out.print("Enter the Serial Number : ");
            String strSerialNumber = scan.nextLine();
            if(validation.getSerialNumber(strSerialNumber,1,Utils.shows.size()-1)){
                return Integer.valueOf(strSerialNumber);
            }else {
                System.out.println("--------------Enter a valid Value !--------------");
            }
        }while (true);


    }

    public final int getAudienceCount(){
        do{
            System.out.print("Enter the number of Persons : ");
            String strAudienceCount = scan.nextLine();
            if(validation.getAudienceCount(strAudienceCount)){
                return Integer.valueOf(strAudienceCount);
            }else {
                System.out.println("--------------Enter a valid Value !--------------");
            }
        }while (true);

    }

    public final String getSeatType(){
//        scan.nextLine();
        do{
            System.out.print("Enter Seat Type   : ");
            String seattype = scan.nextLine();
            if(validation.getSeatType(seattype)){
                return seattype.toUpperCase();
            }else {
                System.out.println("--------------Enter a valid Value !--------------");
            }
        }while (true);

    }

    final int reBook(){
//        System.out.println("                                      Sorry requested seat(s) Not Available ! :(\n");
        do{
            System.out.println("1.   Book another Class Ticket\n" +
                    "0.   Go Back\n");
            System.out.print("Enter your choice : ");
            String strChoice = scan.nextLine();
            if(validation.getChoice(0,1,strChoice)){
                return Integer.valueOf(strChoice);
            }
        }while (true);

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
        if(tickets.size()==0){
            System.out.println("\n-----------------No tickets Booked !-----------------");
            return;
        }
        for (int i=0;i<tickets.size();i++){
            System.out.println(tickets.get(i));
        }
    }

    final public int getTicketId(){
        System.out.print("\nEnter Ticket id to cancel : ");
        return scan.nextInt();
    }

}
