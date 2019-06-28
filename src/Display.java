import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Scanner;

public class Display {
    Scanner scan;
    MysqlDB mysqlDB = new MysqlDB();
    Validation validation = new Validation();
    Display(){
        scan = new Scanner(System.in);
    }

    public final boolean listShows() throws Exception{
        ResultSet rsShows = mysqlDB.getShows();
        boolean flag = true;

        System.out.printf("\n  S.No          Screen_Name             Movie_Name              Show_Time                     SEATS\n\n");
        while(rsShows.next()){
            int showId = rsShows.getInt("show_id");
            String screenName = rsShows.getString("screen_name");
            String movie_name = rsShows.getString("movie_name");
            String showTime   = rsShows.getString("time");
            flag = false;
            System.out.printf("   %2d     ",showId);
            System.out.printf("     %10s ",screenName);
            System.out.print("        ");
            System.out.printf("%-30s ",movie_name);
            System.out.print(showTime);
            String[] time  = showTime.split(":");
//            System.out.printf("%02s : %02s1",time[0],time[1]);
//            System.out.printf("%9s ",rsShows);
//            String fullTime = shows.get(i-1).getMovieDuration().toString();

//            System.out.printf("        %s Hours %s Minutes",time[0],time[1]);
            ResultSet rsSeatTypes = mysqlDB.getSeats(showId);
            System.out.print("       ");
            while(rsSeatTypes.next()){
                String seat_type = rsSeatTypes.getString("seat_type");
                int seatCount = 0;
                if(seat_type.equalsIgnoreCase("platinum")){
                    seatCount = rsSeatTypes.getInt("seat_count");
                    System.out.printf("%s : %2d    ", "PLATINUM",mysqlDB.getRemSeatCount(showId,"PLATINUM"));
                }
                if(seat_type.equalsIgnoreCase("gold")){
                    seatCount = rsSeatTypes.getInt("seat_count");
                    System.out.printf("%s : %2d    ", "GOLD    ",mysqlDB.getRemSeatCount(showId,"GOLD"));
                }
                if(seat_type.equalsIgnoreCase("silver")){
                    seatCount = rsSeatTypes.getInt("seat_count");
                    System.out.printf("%s : %2d    ", "SILVER  ",mysqlDB.getRemSeatCount(showId,"SILVER"));
                }
            }
            System.out.println();
        }
        if(flag){
            System.out.println("\n-----------------NO-SHOWS-AVAILABLE-----------------");
            return false;
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

    public final void addScreen() throws Exception{

        System.out.print("\nEnter Screen Name : ");
        String screenName = scan.nextLine();

        int movieCount = 4;
        String[] showTime = {"05:50","12:30","06:30","10:30"};

        for (int i=1;i<=movieCount;i++){
            System.out.print("Enter Movie "+i+" Name : ");
            String movieName = scan.nextLine();
//            utils.addScreen(screenName,movieName,showTime[i-1]);
            mysqlDB.setShows(movieName,showTime[i-1],screenName);
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
        do{
            System.out.print("Enter the Serial Number : ");
            String strSerialNumber = scan.nextLine();
            if(validation.getSerialNumber(strSerialNumber)){
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

    final public void showSeats(int showId) throws Exception{
        HashMap<String,Integer[][]> map = new HashMap();

        map.put("PLATINUM", mysqlDB.getSeatArray(showId,"PLATINUM"));
        map.put("GOLD", mysqlDB.getSeatArray(showId,"GOLD"));
        map.put("SILVER", mysqlDB.getSeatArray(showId,"SILVER"));

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
    final public void reportOptions(){
        System.out.println("\n1.Report By Seat Type" +
                "\n2.Report By Screen" +
                "\n3.Report By Movie Name" +
                "\n4.Net Report" );
    }
    final public int listScreens() throws Exception{
        System.out.println();
        LinkedList<String> screens = mysqlDB.getScreens();
        for (int i=0;i<screens.size();i++){
            System.out.println((i+1)+". "+screens.get(i));
        }
        return screens.size();
    }
    final public int listMovies()throws Exception{
        LinkedList<String> movies = mysqlDB.getMovies();
        System.out.println();
        for (int i=0;i<movies.size();i++){
            System.out.println((i+1)+". "+movies.get(i));
        }
        return movies.size();
    }
    final public void bookedTickets() throws Exception{
        LinkedList<Ticket> tickets = mysqlDB.getBookedTickets();
        if(tickets.size()==0){
            System.out.println("\n-----------------No tickets Booked !-----------------");
            return;
        }
        for (int i=0;i<tickets.size();i++){
            System.out.println(tickets.get(i));
        }
    }

    final public int getTicketId(){

        do{
            System.out.print("\nEnter Ticket id to cancel : ");
            String ticketId = scan.nextLine();
            if(validation.getAudienceCount(ticketId)){
                return Integer.valueOf(ticketId);
            }else {
                System.out.println("--------------Enter a valid Value !--------------");
            }
        }while (true);

    }

}
