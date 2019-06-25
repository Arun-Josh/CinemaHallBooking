import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

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
        Integer[][] seatsArray = (Integer[][]) show.getScreenInfo().get(seatType);
        if (availableSeats((Integer[][]) show.getScreenInfo().get(seatType)) < passengerCount ){
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
                System.out.print("MU"+maxSeqUnoccupied);
                System.out.print("    MUB"+maxSeqUnoccupiedBuffer);
                System.out.println(" "+maxSeqUnoccupiedBuffer.size());
                if(maxSeqUnoccupiedBuffer.size()!=0){
//                    System.out.println("    MUB"+maxSeqUnoccupiedBuffer);
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
                tempMax = unOccupied.get(i);
                int pos = 0;
                for (int j=i+1;j<unOccupied.size();j++){
                        if(unOccupied.get(j).size() > tempMax.size()){
                            tempMax = unOccupied.get(j);
                            pos = j;
                        }
                }
                ArrayList temp = unOccupied.get(i);
                unOccupied.set(i,tempMax);
                unOccupied.set(pos,temp);
            }

            System.out.println("unOccupied : "+unOccupied);

//            for(int i=0;i<unOccupied.size();i++){
//                for(int j=0;j<unOccupied.get(i).size();i++){
//
//                }
//            }
        }

        ticket = new Ticket(ticketId,screenNumber,seats,seatType,showTime,ticketPrice);
        BookedTickets.addTicket(ticket);

        System.out.println("\n---------------------Ticket Booked Successfully---------------------");
        System.out.println("                           Ticket Id : " + ticketId);
        return true;
    }
}
