--Don't run the below queries first time (They are mean'y only for testing)

SELECT BOOKED_SEATS.SEAT_TYPE, BOOKINGS.TICKET_ID, BOOKED_SEATS.SEAT_NO FROM BOOKINGS, BOOKED_SEATS WHERE BOOKINGS.SHOW_ID = 1 AND SEAT_TYPE = 'GOLD' AND SEAT_STATUS = 'RESERVED';

SELECT * FROM SHOWS WHERE SHOW_ID = 1;

SELECT SEAT_INFO.SEAT_COUNT AS TOTAL_SEATS_COUNT , COUNT(BOOKED_SEATS.SEAT_NO) AS BOOKED_SEATS_COUNT FROM SEAT_INFO, BOOKED_SEATS,BOOKINGS WHERE SEAT_INFO.SEAT_TYPE = 'SILVER' AND BOOKED_SEATS.SEAT_TYPE ='SILVER'
        AND BOOKINGS.SHOW_ID = 1 AND SEAT_INFO.SHOW_ID = 1
        AND BOOKINGS.TICKET_ID = BOOKED_SEATS.TICKET_ID
        AND BOOKED_SEATS.SEAT_STATUS = 'RESERVED';

SELECT BOOKED_SEATS.SEAT_NO
                FROM BOOKINGS, BOOKED_SEATS
                WHERE BOOKINGS.SHOW_ID = 1
                AND BOOKED_SEATS.SEAT_TYPE = 'gold' AND BOOKED_SEATS.SEAT_STATUS = 'RESERVED'

SELECT BOOKED_SEATS.SEAT_NO
                FROM BOOKINGS
                INNER JOIN BOOKED_SEATS ON BOOKINGS.TICKET_ID = BOOKED_SEATS.TICKET_ID
                AND BOOKINGS.SHOW_ID = 1 AND BOOKED_SEATS.SEAT_TYPE = 'SILVER' AND BOOKED_SEATS.SEAT_STATUS = 'RESERVED'

    SELECT * FROM
    BOOKED_SEATS INNER JOIN
    BOOKINGS ON BOOKINGS.TICKET_ID = BOOKED_SEATS.TICKET_ID
    WHERE SEAT_TYPE = 'silver' AND SEAT_NO = 1 AND BOOKINGS.SHOW_ID = 1 AND SEAT_STATUS = 'RESERVED';

UPDATE BOOKED_SEATS SET SEAT_STATUS = 'CANCELLED' WHERE TICKET_ID =  ;

SELECT *
FROM BOOKINGS
INNER JOIN
BOOKED_SEATS ON BOOKINGS.TICKET_ID = BOOKED_SEATS.TICKET_ID
INNER JOIN
SHOWS ON SHOWS.SHOW_ID = BOOKINGS.SHOW_ID;

SELECT DISTINCT MOVIE_NAME FROM SHOWS ORDER BY MOVIE_NAME ASC;

SELECT * from ( SELECT DISTINCT SCREEN_NAME FROM SHOWS ORDER BY SCREEN_NAME DESC) as t1 LIMIT 1;

 SELECT * from ( SELECT DISTINCT SCREEN_NAME FROM SHOWS ORDER BY SCREEN_NAME ASC LIMIT 2) AS T1 ORDER BY T1.SCREEN_NAME DESC LIMIT 1;

SELECT DISTINCT SCREEN_NAME FROM SHOWS ORDER BY SCREEN_NAME

SELECT * FROM BOOKINGS
INNER JOIN BOOKED_SEATS
ON BOOKINGS.TICKET_ID = BOOKED_SEATS.TICKET_ID WHERE  SEAT_TYPE = 'SILVER' AND TICKET_STATUS='PAID';

    SELECT COUNT(DISTINCT BOOKINGS.TICKET_ID) AS TICKETS, COUNT(*) AS SEATS , SUM(TICKET_PRICE) AS TICKET_PRICE , SUM(REFUNDED_PRICE) AS REFUNDED_PRICE FROM
    BOOKINGS
    INNER JOIN BOOKED_SEATS
    ON BOOKINGS.TICKET_ID = BOOKED_SEATS.TICKET_ID
    INNER JOIN SHOWS
    on shows.SHOW_ID = BOOKINGS.SHOW_ID

    SELECT COUNT(DISTINCT BOOKINGS.TICKET_ID) AS TICKETS, COUNT(*) AS SEATS , SUM(TICKET_PRICE) AS TICKET_PRICE , SUM(REFUNDED_PRICE) AS REFUNDED_PRICE FROM
    BOOKINGS
    INNER JOIN BOOKED_SEATS
    ON BOOKINGS.TICKET_ID = BOOKED_SEATS.TICKET_ID
    INNER JOIN SHOWS
    on shows.SHOW_ID = BOOKINGS.SHOW_ID
    where shows.screen_name = 'A' AND BOOKED_sEATS.SEAT_TYPE = 'SILVER';