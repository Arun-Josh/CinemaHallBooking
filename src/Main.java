import java.util.HashMap;
import java.util.LinkedList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        int choice = 0;
        Scanner scan = new Scanner(System.in);
        Display display = new Display();
        Utils utils = new Utils();
//        LinkedList<Shows> shows = new LinkedList<>();
        Manipulate manipulate = new Manipulate();

//        //Fare
//        HashMap<String, Double> showFare = new HashMap<>();
//        showFare.put("PLATINUM",300d);
//        showFare.put("GOLD",200d);
//        showFare.put("SILVER",100d);

//        int SCREEN_NO;
//
//        SCREEN_NO = 1;
//        HashMap<String, Integer[][]> screen = new HashMap();
//        screen.putAll(manipulate.addScreen(SCREEN_NO));
//        shows.add(new Shows(100,SCREEN_NO, screen,"Titanic","06:10","2:50",showFare));
//        SCREEN_NO = 2;
//        screen = new HashMap();
//        screen.putAll(manipulate.addScreen(SCREEN_NO));
//        shows.add(new Shows(101,SCREEN_NO, screen,"Titanic","06:10","2:50",showFare));
//        SCREEN_NO = 1;
//        screen = new HashMap();
//        screen.putAll(manipulate.addScreen(SCREEN_NO));
//        shows.add(new Shows(102,SCREEN_NO, screen,"Edge Of Tomorrow","14:10","2:25",showFare));
//        SCREEN_NO = 2;
//        screen = new HashMap();
//        screen.putAll(manipulate.addScreen(SCREEN_NO));
//        shows.add(new Shows(103,SCREEN_NO, screen,"Elysium","20:10","3:15",showFare));
//        SCREEN_NO = 3;
//        screen = new HashMap();
//        screen.putAll(manipulate.addScreen(SCREEN_NO));
//        shows.add(new Shows(104,SCREEN_NO, screen,"Battle Ship","08:10","1:35",showFare));
        while(choice!=-1){
            display.listOptions();
            choice = display.getChoice();
            switch (choice){
                case 0: choice = -1;
                break;
                case 1: {
                        if(!display.listShows(Utils.shows)){
                            break;
                        }
                        int showChosen = display.getSerialNumber();
                        showChosen--;
                        if(!utils.validateShow(Utils.shows.get(showChosen))){
                            System.out.println("\n-----------------Sorry Show Not Available-----------------");
                            break;
                        }
                        String seatType   = display.getSeatType();
//                    int showChosen = 1;
//                    String seatType = "GOLD";
                        int passengerCount = display.getAudienceCount();
                        if(utils.assignSeats(Utils.shows.get(showChosen),seatType,passengerCount)){
                            //seat assigned
                        }
                        else {
                            display.reBook();
                        }
                }
                break;
                case 2: {
                        display.listShows(Utils.shows);
                        int showChosen = display.getChoice();
//                        String seatType = display.getSeatType();
//                    int showChosen = 1;
//                    String seatType = "GOLD";
                          display.showSeats(Utils.shows.get(showChosen - 1).getScreen());
                }
                break;
                case 3:{
                        display.bookedTickets();
                }
                break;
                case 4:{
                        //Generate Report
                        Report.generateReport();
                }
                break;
                case 5:{
                        int ticketId = display.getTicketId();
                        utils.cancelTicket(ticketId,Utils.shows);
                }
                break;
                case 6:{
                        int screenCount = display.getNumberOfScreens();
//                        System.out.println("screens : "+screenCount);
                        for(int i=0;i<screenCount;i++){
                            display.addScreen();
                        }
                }
                break;
                default:
                    System.out.println("Enter a valid choice");

            }
        }

        System.out.println("*-*-*-*-*-*-*-* Thank you for using our Service :) *-*-*-*-*-*-*-*");


    }
}
