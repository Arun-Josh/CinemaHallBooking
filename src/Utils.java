import java.util.*;

public class Utils {
    final void populateTwoDArray(Integer[][] seats){
        for (int i=0;i<seats.length;i++){
            for (int j=0;j<seats[0].length;j++){
                seats[i][j] = 0;
            }
        }
    }

    final int availableSeats(Integer[][] seatsArray){
        int availableSeats = 0;
        for(int i=0;i<seatsArray.length;i++){
            for(int j=0;j<seatsArray[0].length;j++){
                if(seatsArray[i][j]==0){
                    availableSeats++;
                }
            }
        }
        return availableSeats;
    }

    final boolean assignSeats(Shows show,String seatType, int passengerCount){
        Integer[][] seatsArray = (Integer[][]) show.getScreen().get(seatType);
        int showId = show.getShowId();
//        System.out.println(show);
        int availableSeats = availableSeats((Integer[][]) show.getScreen().get(seatType));
        if (availableSeats < passengerCount ){
//            System.out.println(availableSeats);
            System.out.println("---------------------Seats not Available---------------------");
            return false;
        }
        boolean flagSeatsAvailable = false;
//        boolean noSequentialSeat = false;
        int seatsNeeded = passengerCount;
        int ticketId = BookedTickets.getTicketId();
        Ticket ticket;
        int screenNumber = show.getScreenNo();
        HashMap<Seat,String> seats = new HashMap<>();
        String showTime = show.getShowTime().toString();
        Double ticketPrice = 0D;
        ArrayList<ArrayList> unOccupied = new ArrayList();
        ArrayList<Seat> maxSeqUnoccupied = new ArrayList<>();

        for(int i=0;i<seatsArray.length && seatsNeeded > 0;i++){
            ArrayList<Seat> maxSeqUnoccupiedBuffer = new ArrayList<>();
            for(int j=0;j<seatsArray[0].length && seatsNeeded > 0;j++){

                if(maxSeqUnoccupied.size() == (passengerCount)){
                    Seat seat;
                    for(int p=0;p<passengerCount && seatsNeeded > 0 ; p++,seatsNeeded--){
                        seat = maxSeqUnoccupied.get(p);
                        String status = "BOOKED";
                        seats.put(seat,status);
                        int row = maxSeqUnoccupied.get(p).getRow();
                        int col = maxSeqUnoccupied.get(p).getCol();
                        seatsArray[row][col] = 1;
                        ticketPrice += show.getShowFare().get(seatType);
                        flagSeatsAvailable = true;
                    }
                    maxSeqUnoccupied.clear();
                }

                if( seatsArray[i][j]==1){
//                    nearFlag = false;
                    maxSeqUnoccupiedBuffer.clear();
                }
                else if(((seatsArray[0].length/2))==j){
                    maxSeqUnoccupiedBuffer.clear();
                    maxSeqUnoccupiedBuffer.add(new Seat(i,j));
                }
                else {
                    maxSeqUnoccupiedBuffer.add(new Seat(i,j));
                }
                /*Maximum unallocated sequential seats*/
                if(maxSeqUnoccupiedBuffer.size() > maxSeqUnoccupied.size()){
//                    unOccupied.add(maxSeqUnoccupiedBuffer);
                    maxSeqUnoccupied.clear();
                    maxSeqUnoccupied.addAll(maxSeqUnoccupiedBuffer);
                }
//                System.out.print("MU"+maxSeqUnoccupied);
//                System.out.print("    MUB"+maxSeqUnoccupiedBuffer);
//                System.out.println(" "+maxSeqUnoccupiedBuffer.size());
                if(maxSeqUnoccupiedBuffer.size()!=0){
                    if(maxSeqUnoccupiedBuffer.size()>1){
                        unOccupied.remove(unOccupied.size()-1);
                    }
                    ArrayList<Seat> temp = new ArrayList<>();
                    temp.addAll(maxSeqUnoccupiedBuffer);
                    unOccupied.add(temp);
                }
            }
        }

        System.out.println("unOccupied : "+unOccupied);

        //Fragmented Seats
        if(!flagSeatsAvailable){

            //Sort by Seats count
            for(int i=0;i<unOccupied.size();i++){
                ArrayList tempMax = new ArrayList();
                tempMax.addAll(unOccupied.get(i));
                int pos = 0;
                boolean flag = false;
                for (int j=i+1;j<unOccupied.size();j++){
                        if(unOccupied.get(j).size() > tempMax.size()){
                            tempMax.clear();
                            tempMax.addAll(unOccupied.get(j));
                            pos = j;
                            flag = true;
                        }
                }
                if(flag){
                    unOccupied.set(pos,unOccupied.get(i));
                    unOccupied.set(i,tempMax);
                }
            }
//            SortSeats sortSeats = new SortSeats();
//            Collections.sort(unOccupied,sortSeats);

            System.out.println("seats needed : "+seatsNeeded + " unOccupied : "+unOccupied);

            //Allocating Fragmented Seats
            for(int i=0;i<unOccupied.size() && seatsNeeded > 0 ;i++){
                ArrayList<Seat> s = unOccupied.get(i);
                for(int j=0;j<unOccupied.get(i).size() && seatsNeeded > 0;j++,seatsNeeded--){
                    Seat seat = s.get(j);
                    int col = s.get(j).getCol();
                    int row = s.get(j).getRow();
                    String status = "BOOKED";
                    seats.put(seat,status);
                    seatsArray[row][col] = 1;
                    ticketPrice += show.getShowFare().get(seatType);
                    System.out.println("Seat alloted : "+seat.getRow()+" "+seat.getCol());
                }
            }
        }

        ticket = new Ticket(ticketId,showId,screenNumber,seats,seatType,showTime,ticketPrice);
        BookedTickets.addTicket(ticket);

        System.out.println("\n---------------------Ticket Booked Successfully---------------------");
        System.out.println("\n-------------------------Your Ticket Info---------------------------\n");
        System.out.println(BookedTickets.getBookedTickets().get(BookedTickets.getBookedTickets().size()-1));
        System.out.println("\n--------------------------------------------------------------------\n");
        return true;
    }

    final public boolean cancelTicket(int ticketId,LinkedList<Shows> shows){
        LinkedList<Ticket> bookedTickets = BookedTickets.getBookedTickets();
        for(int i=0;i<bookedTickets.size();i++){
            if((bookedTickets.get(i).getTicketId() == ticketId) && ( bookedTickets.get(i).getTicketStatus().equals("PAID"))){
                int showId = bookedTickets.get(i).getShowId();
                Shows show = null;
                for(int j=0;j<shows.size();j++){
                    if(shows.get(j).getShowId() == showId){
                        show = shows.get(j);
                    }
                }

                //To Re-enable Booked Tickets
                enableSeats(bookedTickets.get(i),show);

                double ticketPrice = bookedTickets.get(i).getTicketPrice();
                bookedTickets.get(i).setTicketStatus("REFUNDED");
                bookedTickets.get(i).setRefund(ticketPrice/2) ;
                System.out.println("\nTicket cancelled Successfully !");
                System.out.println("Refund Amount : Rs."+(ticketPrice/2));
                return true;
            }
        }
        System.out.println("\nTicket Not Available to cancel !");
        return false;
    }

    final public void enableSeats(Ticket ticket, Shows show){
//        System.out.println("Seats not yet enabled");
        String seatType = ticket.getSeatType();
        Integer[][] seatArray = (Integer[][]) show.getScreen().get(seatType);
        HashMap seats = ticket.getSeats();
        Set<Seat> seatsArray = seats.keySet();

        for(Seat seat : seatsArray){
            int col = seat.getCol();
            int row = seat.getRow();
            seatArray[row][col] = 0;
        }
    }

//    class SortSeats implements Comparator<ArrayList<Seat>>{
//
//        @Override
//        public int compare(ArrayList<Seat> o1, ArrayList<Seat> o2) {
//            return o2.size() - o1.size();
//        }
//    }

}
