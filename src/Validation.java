import java.util.Scanner;

public class Validation {

    Scanner scan = new Scanner(System.in);

    final boolean getChoice(int from, int to, String strChoice){
        try{
            int c = Integer.valueOf(strChoice);
            if(c >= from && c<=to){
                return true;
            }
        }
        catch (Exception E){
            return false;
        }
        return false;
    }
    final boolean getAudienceCount(String strAudienceCount){
        try{
            int c = Integer.valueOf(strAudienceCount);
            if(c>0){
                return true;
            }
        }catch (Exception E){

        }
        return false;
    }
    final boolean getSerialNumber(String strSerialNumber,int from, int to){
        try{
            int c = Integer.valueOf(strSerialNumber);
            if(c>0 && c>=from && c<=to){
                return true;
            }
        }catch (Exception E){

        }
        return false;
    }
    final boolean getNumberOfScreens(String strNumberOfScreens){
        try{
            int c = Integer.valueOf(strNumberOfScreens);
            if(c>0){
                return true;
            }
        }catch (Exception E){

        }
        return false;
    }
    final boolean getSeatType(String seatType){
        if(seatType.equalsIgnoreCase("gold")
        || seatType.equalsIgnoreCase("silver")
        || seatType.equalsIgnoreCase("platinum")){
            return true;
        }
        return false;
    }
}
