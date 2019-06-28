import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;

public class MysqlDB {
    static Connection con = null;
    PreparedStatement ps = null;
    static{
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost/theatres","root","root");
        }catch (Exception E){
            E.printStackTrace();
        }
    }

    final ResultSet getShows() throws Exception{
        ps = con.prepareStatement("SELECT * FROM SHOWS");
        return ps.executeQuery();
    }

    final ResultSet getSeats(int showId) throws Exception{
        ps = con.prepareStatement("SELECT * FROM SEAT_INFO WHERE SHOW_ID = ?");
        ps.setInt(1,showId);
        return ps.executeQuery();
    }

    final boolean checkShow(int showId)throws Exception{
        ps = con.prepareStatement("SELECT * FROM SHOWS WHERE SHOW_ID = ?");
        ps.setInt(1,showId);
        ResultSet rs = ps.executeQuery();
        if(!rs.next()){
            return false;
        }
        if (rs.getString("movie_name").equalsIgnoreCase("na")){
            return false;
        }
        return true;
    }

    final void setShows(String movieName,String time, String screenName) throws Exception{
        ps = con.prepareStatement("INSERT INTO SHOWS(movie_name,time,screen_name) VALUES(?,?,?)");
        ps.setString(1,movieName);
        ps.setString(2,time);
        ps.setString(3,screenName);

        ps.executeUpdate();

        ps = con.prepareStatement("SELECT LAST_INSERT_ID() AS ID FROM SHOWS LIMIT 1");

        ResultSet rs = ps.executeQuery();
        int showId = 0;
        if (rs.next()){
            showId = rs.getInt("id");
        }

        ps = con.prepareStatement("INSERT INTO SEAT_INFO(SHOW_ID,SEAT_TYPE,PRICE,seat_count) VALUES(?,\"PLATINUM\",300,16)");
        ps.setInt(1,showId);
        ps.executeUpdate();

        ps = con.prepareStatement("INSERT INTO SEAT_INFO(SHOW_ID,SEAT_TYPE,PRICE,seat_count) VALUES(?,\"GOLD\",300,16)");
        ps.setInt(1,showId);
        ps.executeUpdate();

        ps = con.prepareStatement("INSERT INTO SEAT_INFO(SHOW_ID,SEAT_TYPE,PRICE,seat_count) VALUES(?,\"SILVER\",300,16)");
        ps.setInt(1,showId);
        ps.executeUpdate();

    }

    final void bookTicket(int showId, Double ticketPrice, Integer[][] seatsArray, String seatType) throws Exception{
//        System.out.println(showId+" "+ticketPrice+" "+seatsArray+" "+seatType);
        ps = con.prepareStatement("INSERT INTO BOOKINGS(SHOW_ID,TICKET_PRICE) VALUES(?,?)");
        ps.setInt(1,showId);
        ps.setDouble(2,ticketPrice);
        ps.executeUpdate();

        ps = con.prepareStatement("SELECT MAX(TICKET_ID) AS TICKET_ID FROM BOOKINGS");
        ResultSet rs = ps.executeQuery();
        int ticket_id = 0;
        if(rs.next())
            ticket_id = rs.getInt("TICKET_ID");

        assignSeat(seatsArray,seatType,ticket_id, showId);
    }

    final boolean assignSeat(Integer[][] seatsArray,String seatType,int ticket_id, int show_id) throws Exception{

        for(int i=0;i<seatsArray.length;i++){
            for(int j=0;j<seatsArray[0].length;j++){
                if(seatsArray[i][j]==1){
                    if(this.isUnoccupied(i,j,seatType,show_id))
                        System.out.println(i+" "+j);
                    allocateOneSeat(ticket_id,seatType,i,j);
                }
            }
        }
        return false;
    }

    final public boolean isUnoccupied(int row , int col, String seatType, int show_id)throws Exception{
        int seatNumber = (row * 8) + (col + 1) ;
        System.out.println(seatNumber);
        ps = con.prepareStatement("    SELECT * FROM\n" +
                "    BOOKINGS INNER JOIN\n" +
                "    BOOKED_SEATS ON BOOKINGS.TICKET_ID = BOOKED_SEATS.TICKET_ID \n" +
                "    WHERE SEAT_TYPE = ? AND SEAT_NO = ? AND BOOKINGS.SHOW_ID = ?");
        ps.setString(1,seatType);
        ps.setInt(2,seatNumber);
        ps.setInt(3,show_id);
        if(ps.executeQuery().next()){
            return false;
        }
        return true;
    }

    final void allocateOneSeat(int ticketId, String seatType, int row, int col)throws Exception{
        int seatNumber ;
        seatNumber = (row * 8) + (col + 1) ;
        System.out.println(ticketId+seatType+col+row+seatNumber);
        ps = con.prepareStatement("INSERT INTO BOOKED_SEATS(TICKET_ID,SEAT_TYPE,SEAT_NO) VALUES(?,?,?)");
        ps.setInt(1,ticketId);
        ps.setString(2,seatType);
        ps.setInt(3,seatNumber);
        ps.executeUpdate();
    }

    final Integer[][] getSeatArray(int showId,String seatType) throws Exception{
        Integer[][] seatArray = new Integer[3][8];
        Utils.populateTwoDArray(seatArray);
//        System.out.println(showId+seatType);
        ps = con.prepareStatement("SELECT BOOKED_SEATS.SEAT_NO \n" +
                "                FROM BOOKINGS\n" +
                "                INNER JOIN BOOKED_SEATS ON BOOKINGS.TICKET_ID = BOOKED_SEATS.TICKET_ID\n" +
                "                AND BOOKINGS.SHOW_ID = ? AND BOOKED_SEATS.SEAT_TYPE = ? AND BOOKED_SEATS.SEAT_STATUS = 'RESERVED'\n");
        ps.setInt(1,showId);
        ps.setString(2,seatType);
        ResultSet rs = ps.executeQuery();

        while (rs.next()){
            int seatNo = rs.getInt("seat_no");
            int col = (seatNo % 8) - 1;
            if(col==-1){
                col = 7;
            }
            int row = seatNo / 8;
            if((seatNo%8) == 0){
                row--;
            }
//            System.out.println(seatNo+" "+row+" "+col+" "+seatType);
            seatArray[row][col] = 1;
        }
        return seatArray;
    }

    final Shows getShowInfo(int showId) throws Exception{
        String screenName ="";
        HashMap<String,Integer[][]> screen = new HashMap<>();
        String movieName ="";
        String showTime = "";
        HashMap<String,Double> showFare = new HashMap<>();

        ps = con.prepareStatement("SELECT * FROM SHOWS WHERE SHOW_ID = ?");
        ps.setInt(1,showId);
        ResultSet rs = ps.executeQuery();

        if(rs.next()){
            screenName = rs.getString("screen_name");
            movieName = rs.getString("movie_name");
            showTime = rs.getString("time");
        }

        ps = con.prepareStatement("SELECT * FROM SEAT_INFO WHERE SHOW_ID = ?");
        ps.setInt(1,showId);
        rs = ps.executeQuery();

        while(rs.next()){
            String seatType = rs.getString("SEAT_TYPE");
            screen.put(seatType,getSeatArray(showId,seatType));
            double price = rs.getDouble("price");
            showFare.put(seatType,price);
        }

        return new Shows(showId,screenName,screen,movieName,showTime,"02:00",showFare);
    }

    final int getRemSeatCount(int showId, String seatType) throws Exception{
        int remSeats = 0;
        ps = con.prepareStatement("SELECT SEAT_INFO.SEAT_COUNT AS TOTAL_SEATS_COUNT , COUNT(BOOKED_SEATS.SEAT_NO) AS BOOKED_SEATS_COUNT " +
                "FROM SEAT_INFO, BOOKED_SEATS,BOOKINGS " +
                "WHERE SEAT_INFO.SEAT_TYPE = ? " +
                "        AND BOOKED_SEATS.SEAT_TYPE =?\n" +
                "        AND BOOKINGS.SHOW_ID = ? AND SEAT_INFO.SHOW_ID = ?  \n" +
                "        AND BOOKINGS.TICKET_ID = BOOKED_SEATS.TICKET_ID\n" +
                "        AND BOOKED_SEATS.SEAT_STATUS = 'RESERVED'\n");
        ps.setString(1,seatType);
        ps.setString(2,seatType);
        ps.setInt(3,showId);
        ps.setInt(4,showId);
        ResultSet rs = ps.executeQuery();
        if(rs.next())
        remSeats = rs.getInt("TOTAL_SEATS_COUNT") - rs.getInt("BOOKED_SEATS_COUNT");
//        System.out.println(remSeats);
        return remSeats;
    }

}
