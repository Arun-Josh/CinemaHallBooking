import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.LinkedList;

public class MysqlDB {
    static Connection con = null;
    PreparedStatement ps = null;
    static{
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost/THEATRES","root","");
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
        if (rs.getString("MOVIE_NAME").equalsIgnoreCase("na")){
            return false;
        }
        return true;
    }

    final void setShows(String MOVIE_NAME,String time, String screenName) throws Exception{
        ps = con.prepareStatement("INSERT INTO SHOWS(movie_name,time,SCREEN_NAME) VALUES(?,?,?)");
        ps.setString(1,MOVIE_NAME);
        ps.setString(2,time);
        ps.setString(3,screenName);

        ps.executeUpdate();

        ps = con.prepareStatement("SELECT LAST_INSERT_ID() AS ID FROM SHOWS LIMIT 1");

        ResultSet rs = ps.executeQuery();
        int showId = 0;
        if (rs.next()){
            showId = rs.getInt("id");
        }

        ps = con.prepareStatement("INSERT INTO SEAT_INFO(SHOW_ID,SEAT_TYPE,PRICE,SEAT_COUNT) VALUES(?,\"PLATINUM\",300,24)");
        ps.setInt(1,showId);
        ps.executeUpdate();

        ps = con.prepareStatement("INSERT INTO SEAT_INFO(SHOW_ID,SEAT_TYPE,PRICE,SEAT_COUNT) VALUES(?,\"GOLD\",300,24)");
        ps.setInt(1,showId);
        ps.executeUpdate();

        ps = con.prepareStatement("INSERT INTO SEAT_INFO(SHOW_ID,SEAT_TYPE,PRICE,SEAT_COUNT) VALUES(?,\"SILVER\",300,24)");
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
                    if(this.isUnoccupied(i,j,seatType,show_id)){
//                        System.out.println(i+" "+j);
                        allocateOneSeat(ticket_id,seatType,i,j);
                    }
                }
            }
        }
        return false;
    }

    final int getTicketId() throws Exception{
        ps = con.prepareStatement("select MAX(TICKET_ID) AS TICKET_ID FROM BOOKINGS");
        ResultSet rs = ps.executeQuery();
        int ticketId = 0;
        if (rs.next()){
            ticketId = rs.getInt("TICKET_ID");
        }
        return ticketId;
    }

    final public boolean isUnoccupied(int row , int col, String seatType, int show_id)throws Exception{
        int seatNumber = (row * 8) + (col + 1) ;
//        System.out.println(seatNumber);
        ps = con.prepareStatement("    SELECT * FROM\n" +
                "    BOOKED_SEATS INNER JOIN\n" +
                "    BOOKINGS ON BOOKINGS.TICKET_ID = BOOKED_SEATS.TICKET_ID \n" +
                "    WHERE SEAT_TYPE = ? AND SEAT_NO = ? AND BOOKINGS.SHOW_ID = ? AND SEAT_STATUS = 'RESERVED'");
        ps.setString(1,seatType);
        ps.setInt(2,seatNumber);
        ps.setInt(3,show_id);
        ResultSet rs = ps.executeQuery();
        if(rs.next()){
            return false;
        }
        return true;
    }

    final LinkedList<Ticket> getBookedTickets()throws Exception{
        LinkedList<Ticket> tickets= new LinkedList();

        ps = con.prepareStatement("SELECT * \n" +
                "FROM BOOKINGS \n" +
                "INNER JOIN\n" +
                "BOOKED_SEATS ON BOOKINGS.TICKET_ID = BOOKED_SEATS.TICKET_ID\n" +
                "INNER JOIN \n" +
                "SHOWS ON SHOWS.SHOW_ID = BOOKINGS.SHOW_ID");
        ResultSet rs = ps.executeQuery();
        int thisTicketId = 0;
        boolean flag = false;
                if (rs.next()) {
                    while (true){
                        if (flag){
                            break;
                        }
                        int showId = rs.getInt("SHOW_ID");
                        String ticketStatus = rs.getString("TICKET_STATUS");
                        double ticketPrice = rs.getDouble("TICKET_PRICE");
                        double refundedPrice = rs.getDouble("REFUNDED_PRICE");
                        String seatType = rs.getString("SEAT_TYPE");
                        String MOVIE_NAME = rs.getString("MOVIE_NAME");
                        String screenName = rs.getString("SCREEN_NAME");
                        String time = rs.getString("TIME");
                        LinkedList<Integer> seatnos = new LinkedList();
                        int ticketId = rs.getInt("TICKET_ID");
                        int seatNo = rs.getInt("SEAT_NO");
                        seatnos.add(seatNo);
                        do{
                            if(rs.next()){
                                seatNo = rs.getInt("SEAT_NO");
                                thisTicketId = rs.getInt("TICKET_ID");
                                if (ticketId != thisTicketId) {
                                    Ticket ticket = new Ticket(ticketId, showId, MOVIE_NAME,screenName,new HashMap(),seatType,time,ticketPrice);
                                    ticket.setRefund(refundedPrice);
                                    ticket.setTicketStatus(ticketStatus);
                                    ticket.setSeatNos(seatnos);
                                    tickets.add(ticket);
                                    break;
                                }
                                seatnos.add(seatNo);
                            }
                            else {
                                Ticket ticket = new Ticket(ticketId, showId, MOVIE_NAME,screenName,new HashMap(),seatType,time,ticketPrice);
                                ticket.setRefund(refundedPrice);
                                ticket.setTicketStatus(ticketStatus);
                                ticket.setSeatNos(seatnos);
                                tickets.add(ticket);
                                flag = true;
                                break;
                            }
                        }while (true);
                    }

                }

            return tickets;
    }

    final LinkedList<String> getMovies()throws Exception{
        LinkedList<String> movies = new LinkedList();
        ps = con.prepareStatement("SELECT DISTINCT MOVIE_NAME FROM SHOWS ORDER BY MOVIE_NAME ASC");
        ResultSet rs = ps.executeQuery();
        while (rs.next()){
            movies.add(rs.getString("MOVIE_NAME"));
        }
        return movies;
    }

    final boolean checkTicket(int ticketId)throws Exception{
        ps = con.prepareStatement("SELECT * FROM BOOKINGS WHERE TICKET_ID = ? AND TICKET_STATUS = 'PAID' ");
        ps.setInt(1,ticketId);
        ResultSet rs = ps.executeQuery();
        if(rs.next()){
            return true;
        }
        return false;
    }

    final LinkedList<String> getScreens()throws Exception{
        LinkedList<String> screens = new LinkedList();
        ps = con.prepareStatement("SELECT DISTINCT SCREEN_NAME FROM SHOWS ORDER BY SCREEN_NAME");
        ResultSet rs = ps.executeQuery();
        while (rs.next()){
            screens.add(rs.getString("SCREEN_NAME"));
        }
        return screens;
    }

    final void cancelTicket(int ticketId)throws Exception{
        ps = con.prepareStatement("update BOOKINGS SET TICKET_STATUS = 'CANCELLED' where TICKET_ID = ?");
        ps.setInt(1,ticketId);
        ps.executeUpdate();

        ps = con.prepareStatement("UPDATE BOOKED_SEATS SET SEAT_STATUS = 'CANCELLED' WHERE TICKET_ID = ?");
        ps.setInt(1,ticketId);
        ps.executeUpdate();
    }

    final void updateRefund(int ticketId, double refundAmount)throws Exception{
        ps = con.prepareStatement("update BOOKINGS SET REFUNDED_PRICE = ? WHERE TICKET_ID = ?");
        ps.setDouble(1,refundAmount);
        ps.setInt(2,ticketId);
        ps.executeUpdate();
    }

    final double getTicketPrice(int ticketId)throws Exception{
        ps = con.prepareStatement("SELECT TICKET_PRICE FROM BOOKINGS where TICKET_ID = ?");
        ps.setInt(1,ticketId);
        ResultSet rs = ps.executeQuery();
        if (rs.next()){
            return rs.getDouble("TICKET_PRICE");
        }
        return -1d;
    }

    final void allocateOneSeat(int ticketId, String seatType, int row, int col)throws Exception{
        int seatNumber ;
        seatNumber = (row * 8) + (col + 1) ;
//        System.out.println(ticketId+seatType+col+row+seatNumber);
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
            int seatNo = rs.getInt("SEAT_NO");
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
        HashMap<String,Integer[][]> screen = new HashMap();
        String MOVIE_NAME ="";
        String showTime = "";
        HashMap<String,Double> showFare = new HashMap();

        ps = con.prepareStatement("SELECT * FROM SHOWS WHERE SHOW_ID = ?");
        ps.setInt(1,showId);
        ResultSet rs = ps.executeQuery();

        if(rs.next()){
            screenName = rs.getString("SCREEN_NAME");
            MOVIE_NAME = rs.getString("MOVIE_NAME");
            showTime = rs.getString("TIME");
        }

        ps = con.prepareStatement("SELECT * FROM SEAT_INFO WHERE SHOW_ID = ?");
        ps.setInt(1,showId);
        rs = ps.executeQuery();

        while(rs.next()){
            String seatType = rs.getString("SEAT_TYPE");
            screen.put(seatType,getSeatArray(showId,seatType));
            double price = rs.getDouble("PRICE");
            showFare.put(seatType,price);
        }

        return new Shows(showId,screenName,screen,MOVIE_NAME,showTime,"02:00",showFare);
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

    final HashMap getSeatReport(String seatType) throws Exception{
        HashMap report = new HashMap();
        ps = con.prepareStatement("SELECT COUNT(DISTINCT BOOKINGS.TICKET_ID) AS TICKETS, COUNT(*) AS SEATS , SUM(TICKET_PRICE) AS TICKET_PRICE , SUM(REFUNDED_PRICE) AS REFUNDED_PRICE FROM BOOKINGS \n" +
                "INNER JOIN BOOKED_SEATS \n" +
                "ON BOOKINGS.TICKET_ID = BOOKED_SEATS.TICKET_ID WHERE  SEAT_TYPE = ?");
        ps.setString(1,seatType);
        ResultSet rs = ps.executeQuery();
        if (rs.next()){
            if(rs.getInt("TICKETS")==0){
                return report;
            }
            report.put("ticketsCount",rs.getInt("TICKETS"));
            report.put("seatsCount",rs.getInt("SEATS"));
            report.put("ticketPrice",rs.getDouble("TICKET_PRICE"));
            report.put("refundPrice",rs.getDouble("REFUNDED_PRICE"));
            report.put("profit",Double.valueOf(rs.getString("TICKET_PRICE")) - Double.valueOf(rs.getString("REFUNDED_PRICE")));
        }
        return report;
    }

    final HashMap getMovieReport(String MOVIE_NAME) throws Exception{
        HashMap report = new HashMap();
        ps = con.prepareStatement("    SELECT COUNT(DISTINCT BOOKINGS.TICKET_ID) AS TICKETS, COUNT(*) AS SEATS , SUM(TICKET_PRICE) AS TICKET_PRICE , SUM(REFUNDED_PRICE) AS REFUNDED_PRICE FROM \n" +
                "    BOOKINGS \n" +
                "    INNER JOIN BOOKED_SEATS \n" +
                "    ON BOOKINGS.TICKET_ID = BOOKED_SEATS.TICKET_ID \n" +
                "    INNER JOIN SHOWS\n" +
                "    on shows.SHOW_ID = BOOKINGS.SHOW_ID\n" +
                "    WHERE SHOWS.MOVIE_NAME = ?");
        ps.setString(1,MOVIE_NAME);
        ResultSet rs = ps.executeQuery();
        if (rs.next()){
            if(rs.getInt("TICKETS")==0){
                return report;
            }
            report.put("ticketsCount",rs.getInt("TICKETS"));
            report.put("seatsCount",rs.getInt("SEATS"));
            report.put("ticketPrice",rs.getDouble("TICKET_PRICE"));
            report.put("refundPrice",rs.getDouble("REFUNDED_PRICE"));
            report.put("profit",Double.valueOf(rs.getString("TICKET_PRICE")) - Double.valueOf(rs.getString("REFUNDED_PRICE")));
        }
        return report;
    }

    final HashMap getNetReport() throws Exception{
        HashMap report = new HashMap();
        ps = con.prepareStatement("SELECT COUNT(DISTINCT BOOKINGS.TICKET_ID) AS TICKETS, COUNT(*) AS SEATS , SUM(TICKET_PRICE) AS TICKET_PRICE , SUM(REFUNDED_PRICE) AS REFUNDED_PRICE FROM \n" +
                "    BOOKINGS \n" +
                "    INNER JOIN BOOKED_SEATS \n" +
                "    ON BOOKINGS.TICKET_ID = BOOKED_SEATS.TICKET_ID \n" +
                "    INNER JOIN SHOWS\n" +
                "    on shows.SHOW_ID = BOOKINGS.SHOW_ID;");
        ResultSet rs = ps.executeQuery();
        if (rs.next()){
            if(rs.getInt("TICKETS")==0){
                return report;
            }
            report.put("ticketsCount",rs.getInt("TICKETS"));
            report.put("seatsCount",rs.getInt("SEATS"));
            report.put("ticketPrice",rs.getDouble("TICKET_PRICE"));
            report.put("refundPrice",rs.getDouble("REFUNDED_PRICE"));
            report.put("profit",Double.valueOf(rs.getString("TICKET_PRICE")) - Double.valueOf(rs.getString("REFUNDED_PRICE")));
        }
        return report;
    }

    public final String getMovieName(int choice) throws Exception{
        String MOVIE_NAME = "";
        ps = con.prepareStatement("SELECT * from ( SELECT DISTINCT MOVIE_NAME FROM " +
                "SHOWS ORDER BY MOVIE_NAME ASC LIMIT ?) AS T1 ORDER BY T1.MOVIE_NAME DESC LIMIT 1");
        ps.setInt(1,choice);
        ResultSet rs = ps.executeQuery();
        if (rs.next()){
            MOVIE_NAME = rs.getString("MOVIE_NAME");
        }
        return MOVIE_NAME;
    }

    public final String getScreenName(int choice)throws Exception{
        String screename = "";
        ps = con.prepareStatement(" SELECT * from ( SELECT DISTINCT SCREEN_NAME FROM " +
                "SHOWS ORDER BY SCREEN_NAME ASC LIMIT ?) AS T1 ORDER BY T1.SCREEN_NAME DESC LIMIT 1");
        ps.setInt(1,choice);
        ResultSet rs = ps.executeQuery();
        if (rs.next()){
            screename = rs.getString("SCREEN_NAME");
        }
        return screename;
    }

    final HashMap getSeatReportByScreen(String screenName, String seatType) throws Exception{
        HashMap report = new HashMap();
        ps = con.prepareStatement("    SELECT COUNT(DISTINCT BOOKINGS.TICKET_ID) AS TICKETS, COUNT(*) AS SEATS , SUM(TICKET_PRICE) AS TICKET_PRICE , SUM(REFUNDED_PRICE) AS REFUNDED_PRICE FROM \n" +
                "    BOOKINGS \n" +
                "    INNER JOIN BOOKED_SEATS \n" +
                "    ON BOOKINGS.TICKET_ID = BOOKED_SEATS.TICKET_ID \n" +
                "    INNER JOIN SHOWS\n" +
                "    on shows.SHOW_ID = BOOKINGS.SHOW_ID\n" +
                "    where shows.SCREEN_NAME = ? AND BOOKED_sEATS.SEAT_TYPE = ?");
        ps.setString(1,screenName);
        ps.setString(2,seatType);
        ResultSet rs = ps.executeQuery();
        if (rs.next()){
            if(rs.getInt("TICKETS")==0){
                return report;
            }
            report.put("ticketsCount",rs.getInt("TICKETS"));
            report.put("seatsCount",rs.getInt("SEATS"));
            report.put("ticketPrice",rs.getDouble("TICKET_PRICE"));
            report.put("refundPrice",rs.getDouble("REFUNDED_PRICE"));
            report.put("profit",Double.valueOf(rs.getString("TICKET_PRICE")) - Double.valueOf(rs.getString("REFUNDED_PRICE")));
        }
        return report;
    }

    final HashMap getReportByScreen(String screenName) throws Exception{
        HashMap report = new HashMap();
        ps = con.prepareStatement("    SELECT COUNT(DISTINCT BOOKINGS.TICKET_ID) AS TICKETS, COUNT(*) AS SEATS , SUM(TICKET_PRICE) AS TICKET_PRICE , SUM(REFUNDED_PRICE) AS REFUNDED_PRICE FROM \n" +
                "    BOOKINGS \n" +
                "    INNER JOIN BOOKED_SEATS \n" +
                "    ON BOOKINGS.TICKET_ID = BOOKED_SEATS.TICKET_ID \n" +
                "    INNER JOIN SHOWS\n" +
                "    on shows.SHOW_ID = BOOKINGS.SHOW_ID\n" +
                "    where shows.SCREEN_NAME = ? ");
        ps.setString(1,screenName);
        ResultSet rs = ps.executeQuery();
        if (rs.next()){
            if(rs.getInt("TICKETS")==0){
                return report;
            }
            report.put("ticketsCount",rs.getInt("TICKETS"));
            report.put("seatsCount",rs.getInt("SEATS"));
            report.put("ticketPrice",rs.getDouble("TICKET_PRICE"));
            report.put("refundPrice",rs.getDouble("REFUNDED_PRICE"));
            report.put("profit",Double.valueOf(rs.getString("TICKET_PRICE")) - Double.valueOf(rs.getString("REFUNDED_PRICE")));
        }
        return report;
    }

}
