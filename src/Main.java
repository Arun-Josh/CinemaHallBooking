import java.util.HashMap;
import java.util.LinkedList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        int choice = 0;
        Scanner scan = new Scanner(System.in);
        Display display = new Display();
        Utils utils = new Utils();
        LinkedList<Shows> shows = new LinkedList<>();
        Manipulate manipulate = new Manipulate();

//        HashMap<Integer,HashMap> screens = new HashMap<>();
//
//        Integer[][] seatArray = null;
//
//        HashMap<String,Integer[][]> seats = new HashMap();
//
//
//        //Screen 1
//        seatArray = new Integer[3][8];
//        utils.populateTwoDArray(seatArray);
//        seats.put("PLATINUM",seatArray);
//        seatArray = new Integer[3][8];
//        utils.populateTwoDArray(seatArray);
//        seats.put("GOLD",seatArray);
//        seatArray = new Integer[3][8];
//        utils.populateTwoDArray(seatArray);
//        seats.put("SILVER",seatArray);

//        screens.put(1,seats);
//
//        seats = new HashMap<>();
//        utils.populateTwoDArray(seatArray);
//
//        //Screen 2
//        seatArray = new Integer[2][8];
//        utils.populateTwoDArray(seatArray);
//        seats.put("PLATINUM",seatArray);
//        seatArray = new Integer[2][8];
//        utils.populateTwoDArray(seatArray);
//        seats.put("GOLD",seatArray);
//        seatArray = new Integer[2][8];
//        utils.populateTwoDArray(seatArray);
//        seats.put("SILVER",seatArray);

//        screens.put(2,seats);

        //Fare
        HashMap<String, Double> showFare = new HashMap<>();
        showFare.put("PLATINUM",300d);
        showFare.put("GOLD",200d);
        showFare.put("SILVER",100d);

        int SCREEN_NO;

        SCREEN_NO = 1;
        HashMap<String, Integer[][]> screen = new HashMap();
        screen.putAll(manipulate.addScreen(SCREEN_NO));
        shows.add(new Shows(100,SCREEN_NO, screen,"Titanic","06:10","2:50",showFare));
//        System.out.println("++"+shows.get(0).getScreen().get("PLATINUM"));
        SCREEN_NO = 2;
        screen = new HashMap();
        screen.putAll(manipulate.addScreen(SCREEN_NO));
        shows.add(new Shows(101,SCREEN_NO, screen,"Titanic","06:10","2:50",showFare));
//        System.out.println(shows.get(1));
        SCREEN_NO = 1;
        screen = new HashMap();
        screen.putAll(manipulate.addScreen(SCREEN_NO));
        shows.add(new Shows(102,SCREEN_NO, screen,"Edge Of Tomorrow","14:10","2:25",showFare));
        SCREEN_NO = 2;
        screen = new HashMap();
        screen.putAll(manipulate.addScreen(SCREEN_NO));
        shows.add(new Shows(103,SCREEN_NO, screen,"Elysium","20:10","3:15",showFare));
        SCREEN_NO = 3;
        screen = new HashMap();
        screen.putAll(manipulate.addScreen(SCREEN_NO));
        shows.add(new Shows(104,SCREEN_NO, screen,"Battle Ship","08:10","1:35",showFare));
        while(choice!=-1){
            display.listOptions();
            choice = display.getChoice();
            switch (choice){
                case 0: choice = -1;
                break;
                case 1: {
                        display.listShows(shows);
                        int showChosen = display.getChoice();
                        showChosen--;
                        String seatType   = display.getSeatType();
//                    int showChosen = 1;
//                    String seatType = "GOLD";
                        int passengerCount = display.getAudienceCount();
                        if(utils.assignSeats(shows.get(showChosen),seatType,passengerCount)){
                            //seat assigned
                        }
                        else {
                            display.reBook();
                        }
                }
                break;
                case 2: {
                        display.listShows(shows);
                        int showChosen = display.getChoice();
//                        String seatType = display.getSeatType();
//                    int showChosen = 1;
//                    String seatType = "GOLD";
                          display.showSeats(shows.get(showChosen - 1).getScreen());
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
                        utils.cancelTicket(ticketId,shows);
                }
                break;
                default:
                    System.out.println("Enter a valid choice");

            }
        }

        System.out.println("*-*-*-*-*-*-*-* Thank you for using our Service :) *-*-*-*-*-*-*-*");


    }
}
