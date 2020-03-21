package no.akademiet.app.ws.mobileappws.dao;

import no.akademiet.app.ws.mobileappws.model.Room;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;


@Repository("database-dao")
public class DBAccessService implements RoomsDao {
    private static Logger LOGGER = Logger.getLogger(DBAccessService.class.getName());

    private List<Room> cache = new ArrayList<>();

    private Connection dbConnection;

    private static final String DB_URL = "jdbc:postgresql://localhost:5432/";
    private static final String DATABASE = "postgres";
    private static final String TABLE = "romstatusTest";
    private static final String USER = "martinsw";
    private static final String PASSWORD = "";

    private final int minutes = 3;

    private static String[] ROOM_NUMBERS = {"111", "112", "113", "114", "115", "209", "210", "211", "212", "213", "214", "215", "216", "217"};

    public DBAccessService() {
        try {
            LOGGER.log(Level.INFO, "Creating connection with database. URL: " + DB_URL + DATABASE);
            Class.forName("org.postgresql.Driver");
            dbConnection = DriverManager.getConnection(DB_URL + DATABASE, USER, PASSWORD);

            LOGGER.log(Level.INFO, "Connection created with database: " + DATABASE);

        }
        catch (SQLException | ClassNotFoundException e) {
            if (e instanceof SQLException) {
                LOGGER.log(Level.SEVERE, "Could not create connection with database. URL: " + DB_URL + DATABASE);
            }
            else if (e instanceof ClassNotFoundException) {
                LOGGER.log(Level.SEVERE, "Could not find jdbc!");
            }
            e.printStackTrace();
            LOGGER.log(Level.WARNING, "Could not connect to database. Shutting down...");
            System.exit(1);
            // TODO: 20/03/2020 Handle exception
        }

        regularlyRequestRooms();
    }

    @Override
    public List<Room> getAllRooms() throws SQLException {
        if (cache.isEmpty()) {
            LOGGER.log(Level.INFO, "Cache is empty. Searching for rooms in database."); //Should not happen
            getRoomsFromDataBase();
        }
        return cache;
    }

    private void getRoomsFromDataBase() throws SQLException {
        cache.clear();

        LOGGER.log(Level.INFO, "Searching for rooms in database.");
        Statement statement = dbConnection.createStatement();
        for (String roomNumber : ROOM_NUMBERS) {
            cache.add(readResult(selectRoom(roomNumber, statement)));
        }
        LOGGER.log(Level.INFO, "Rooms successfully found.");
        statement.close();
    }

    private Room readResult(ResultSet rs) throws SQLException  {
        while (rs.next()) {
            int floor = rs.getInt("floor");
            int number = rs.getInt("number");
            String name = rs.getString("name");
            boolean available = rs.getBoolean("available");
            int quality = rs.getInt("quality");

            return new Room(quality, number, name, available, floor);
        }
        throw new SQLException("Could not find room!");
    }

    private ResultSet selectRoom(String roomNumber, Statement stmt) throws SQLException {
        return stmt.executeQuery("SELECT * FROM " + TABLE + " WHERE number = " + roomNumber + " FETCH FIRST ROWS ONLY;");
    }

    private void regularlyRequestRooms() {
        new Timer("DB-Requester").scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                LOGGER.log(Level.INFO, "The regular room list check:");
                try {
                    getRoomsFromDataBase();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }, 0, minutes * 60000);
    }

}
