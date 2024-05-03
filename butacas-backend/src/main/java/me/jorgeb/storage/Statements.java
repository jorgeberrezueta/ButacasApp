package me.jorgeb.storage;

public class Statements {

    public static final String ENTITY_BILLBOARD = "billboard";
    public static final String ENTITY_BOOKING = "booking";
    public static final String ENTITY_CUSTOMER = "customer";
    public static final String ENTITY_MOVIE = "movie";
    public static final String ENTITY_ROOM = "room";
    public static final String ENTITY_SEAT = "seat";

    // 2.A. query para obtener las reservas de películas cuyo genero sea terror y con un rango de fechas
    public static final String QUERY_2A =
            "SELECT b.* FROM booking b " +
            "JOIN billboard bb ON b.billboard_id = bb.id " +
            "JOIN movie m ON bb.movie_id = m.id " +
            "WHERE m.genre = 'HORROR' " +
            "AND b.date BETWEEN :from AND :to";

    // 2.B. query para obtener el numero de butacas disponibles y ocupadas por sala en la cartelera del día actual.
    public static final String QUERY_2B =
            "SELECT s.room_id, r.name, COUNT(*) total_seats, occupied_seats " +
            "FROM seat s " +
            "JOIN ( " +
            "	SELECT s.room_id, COUNT(*) occupied_seats " +
            "	FROM booking b " +
            "	JOIN seat s ON s.id = b.seat_id " +
            "	JOIN billboard bb ON bb.id = b.billboard_id " +
            // El valor de CURRENT_DATE es la fecha actual en formato YYYY-MM-DD, con una diferencia de 5 horas. Se ajusta la fecha para que coincida con la fecha de la base de datos.
            //"	WHERE bb.date = strftime(\"%s\", CURRENT_DATE) * 1000 + 18000000 " +
            "	GROUP BY s.room_id " +
            ") q ON q.room_id = s.room_id " +
            "JOIN room r ON r.id = s.room_id " +
            "GROUP BY s.room_id;";

}
