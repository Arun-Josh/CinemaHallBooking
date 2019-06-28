
public class Main {

    public static void main(String[] args) throws Exception{
        int choice = 0;

        Display display = new Display();
        Utils utils = new Utils();
        MysqlDB mysqlDB = new MysqlDB();

        while(choice!=-1){
            display.listOptions();
            choice = display.getChoice(0,6);
            switch (choice){
                case 0: {
                    choice = -1;
                }
                break;
                case 1: {
                        if(!display.listShows()){
                            break;
                        }
                        int showChosen = display.getSerialNumber();
//                        showChosen--;
                        if(!mysqlDB.checkShow(showChosen)){
                            System.out.println("\n-----------------Sorry Show Not Available-----------------");
                            break;
                        }
//                        do{
                            String seatType   = display.getSeatType();
//                    int showChosen = 1;
//                    String seatType = "GOLD";
                            int passengerCount = display.getAudienceCount();
                            Shows show = mysqlDB.getShowInfo(showChosen);
                            if(utils.assignSeats(show,seatType,passengerCount)){
//                                int c = 0;
////                                c = display.reBook();
//                                if(c==0){
                                    break;
//                                }
//                                else if(c==1){
//                                    continue;
//                                }
                            }
//                        }while (true);
                }
                break;
                case 2: {
                    if(!display.listShows()){
                        break;
                    }
                        int showChosen = display.getSerialNumber();
//                    String seatType = display.getSeatType();
//                    int showChosen = 1;
//                    String seatType = "GOLD";
//                          display.showSeats(Utils.shows.get(showChosen).getScreen());
                    display.showSeats(showChosen);
                }
                break;
                case 3:{
                        display.bookedTickets();
                }
                break;
                case 4:{
                        //Generate Report
                        display.reportOptions();
                        int reportChoice = display.getChoice(1,4);
                        //By Seat Type
                        if(reportChoice==1){
                            System.out.println("\n1.PLATINUM" +
                                    "\n2.GOLD" +
                                    "\n3.SILVER");
                            int seatChosen = display.getChoice(1,3);
                            String[] seats = new String[]{"PLATINUM","GOLD","SILVER"};
                            //TO DO
                            Report.generateReportBySeatType(seats[seatChosen - 1]);
                        }
                        //By screen type
                        else if (reportChoice==2){
                            int screenCount = display.listScreens();
                            int screenChosen = display.getChoice(1,screenCount);
                            System.out.println("\n1.PLATINUM" +
                                    "\n2.GOLD" +
                                    "\n3.SILVER" +
                                    "\n4.ALL SEATS");
                            int seatOptionChosen = display.getChoice(1,4);
                            String screenName = mysqlDB.getScreenName(screenChosen);
                            if(seatOptionChosen < 4){
                                String[] seats = new String[]{"PLATINUM","GOLD","SILVER"};

                                String seatName = seats[seatOptionChosen-1];
                                Report.generatetSeatReportByScreen(screenName,seatName);
                            }
                            else if (seatOptionChosen==4){
                                Report.generateReportByScreen(screenName);
                            }
                        }
                        //By Movie Name
                        else if (reportChoice==3){
                            int movies_count = display.listMovies();
                            int movieChosen = display.getChoice(1,movies_count);
                            String movieName = mysqlDB.getMovieName(movieChosen);
                            Report.generateReportByMovieName(movieName);
                        }
                        //Net Report
                        else if(reportChoice==4){
                            Report.generateNetReport();
                        }
//                        Report.generateReport();
                }
                break;
                case 5:{
                        int ticketId = display.getTicketId();
                        utils.cancelTicket(ticketId);
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

        System.out.println("\n*-*-*-*-*-*-*-* Thank you for using our Service :) *-*-*-*-*-*-*-*\n");


    }
}
