import java.util.HashMap;

public class Manipulate {

    final HashMap<String, Integer[][]> addScreen(int screenNumber){
        Utils utils = new Utils();
//        HashMap<Integer, HashMap> screen = new HashMap<>();
        Integer[][] seatArray = null;
        HashMap<String,Integer[][]> seats = new HashMap();
        //Screen
        seatArray = new Integer[3][8];
        utils.populateTwoDArray(seatArray);
        seats.put("PLATINUM",seatArray);
        seatArray = new Integer[3][8];
        utils.populateTwoDArray(seatArray);
        seats.put("GOLD",seatArray);
        seatArray = new Integer[3][8];
        utils.populateTwoDArray(seatArray);
        seats.put("SILVER",seatArray);
//        screen.put(screenNumber,seats);
//        System.out.println("man : "+screen.get(1).get("PLATINUM"));
        return seats;
    }

}
