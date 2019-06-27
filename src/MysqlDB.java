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

    final boolean assignSeat(){
        return false;
    }

    final Integer[][] getSeatArray(int showId,String seatType) throws Exception{
        Integer[][] seatArray = new Integer[3][8];
        Utils.populateTwoDArray(seatArray);
//        System.out.println(showId+seatType);
        ps = con.prepareStatement("SELECT BOOKED_SEATS.SEAT_NO " +
                "FROM BOOKINGS, BOOKED_SEATS " +
                "WHERE BOOKINGS.SHOW_ID = ? " +
                "AND BOOKED_SEATS.SEAT_TYPE = ? AND BOOKED_SEATS.SEAT_STATUS = 'RESERVED'");
        ps.setInt(1,showId);
        ps.setString(2,seatType);
        ResultSet rs = ps.executeQuery();

        while (rs.next()){
            int seatNo = rs.getInt("seat_no");
            int col = (seatNo % 8) - 1;
            int row = seatNo / 8;
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
        return remSeats;
    }

}
