
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

        System.out.println("\n*-*-*-*-*-*-*-* Thank you for using our Service :) *-*-*-*-*-*-*-*\n");


    }
}
