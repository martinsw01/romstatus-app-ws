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

    private List<RoomListChangeListener> roomListChangeListeners = new ArrayList<>();
    private List<Room> cache = new ArrayList<>();

    private Connection dbConnection;
    private static final int CONNECTION_ATTEMPTS = 5;
    private int connectionAttempts = CONNECTION_ATTEMPTS;
    private int pause = 5000;

    private static final String DB_URL = "jdbc:postgresql://localhost:5432/";
    private static final String DATABASE = "postgres";
    private static final String TABLE = "romstatusTest";
    private static final String USER = "martinsw";
    private static final String PASSWORD = "";

    private int id = 1;

    private final int minutes = 3;

    private static String[] ROOM_NUMBERS = {"111", "112", "113", "114", "115", "209", "210", "211", "212", "213", "214", "215", "216", "217"};

    public DBAccessService() {
        connectToDatabase();

        regularlyRequestRooms();
    }

    private void connectToDatabase() {
        try {
            LOGGER.log(Level.INFO, "Creating connection with database. URL: " + DB_URL + DATABASE);
            Class.forName("org.postgresql.Driver");
            dbConnection = DriverManager.getConnection(DB_URL + DATABASE, USER, PASSWORD);

            clearTable();

            LOGGER.log(Level.INFO, "Connection created with database: " + DATABASE);

        }
        catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Could not create connection with database. URL: " + DB_URL + DATABASE, e);
            retry();
        }
        catch (ClassNotFoundException e) {
            LOGGER.log(Level.SEVERE, "Could not find jdbc!", e);
            retry();
        }
    }

    private void retry() {
        if (connectionAttempts-- == 1) {
            LOGGER.log(Level.SEVERE, "Could not connect to database after " + CONNECTION_ATTEMPTS + " attempts. Shutting down...");
            System.exit(1);
        }
        else {
            LOGGER.log(Level.WARNING, "Could not connect to database. Retrying in " + pause + " ms");
            try {
                Thread.sleep(pause);
                connectToDatabase();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public List<Room> getAllRooms() throws SQLException {
        if (cache.isEmpty()) {
            LOGGER.log(Level.INFO, "Cache is empty. Searching for rooms in database"); //Should not happen
            getRoomsFromDataBase();
        }
        return cache;
    }

    @Override
    public void notifyActiveClientsChanged(int activeUsers) throws SQLException {
        Statement statement = dbConnection.createStatement();

        String query = "INSERT INTO activeClientsTest (id, clients, timestamp) VALUES (" + id++ + ", " + activeUsers + ", current_timestamp);";
        statement.executeUpdate(query);

        statement.close();
    }

    private void clearTable() throws SQLException {
        Statement statement = dbConnection.createStatement();

        String query = "DELETE FROM activeClientsTest *;";
        statement.executeUpdate(query);

        statement.close();
    }

    private void getRoomsFromDataBase() throws SQLException {

        LOGGER.log(Level.INFO, "Searching for rooms in database");
        Statement statement = dbConnection.createStatement();

        List<Room> newList = new ArrayList<>();
        for (String roomNumber : ROOM_NUMBERS) {
            newList.add(readResult(selectRoom(roomNumber, statement)));
        }
        notifyRoomListChanged(newList);

        cache.clear();
        cache.addAll(newList);

        LOGGER.log(Level.INFO, "Rooms successfully found");
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
        String query = "SELECT floor, number, name, available, quality FROM " + TABLE + " WHERE number = " + roomNumber + "ORDER BY timestamp DESC LIMIT 1;";
        return stmt.executeQuery(query);
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

    @Override
    public void addRoomListChangeListener(RoomListChangeListener listener) {
        roomListChangeListeners.add(listener);
    }

    private void notifyRoomListChanged(List<Room> roomList) {
        if (roomList == cache) {
            return;
        }
        for (RoomListChangeListener listener : roomListChangeListeners) {
            listener.onRoomListChanged(roomList);
        }
    }
}
