package no.akademiet.app.ws.mobileappws.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import no.akademiet.app.ws.mobileappws.model.Room;
import no.akademiet.app.ws.mobileappws.service.RoomsService;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;

import java.net.InetSocketAddress;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Web socket server communicating with clients.
 * Implements 'CommandLineRunner' and is annotated with @Component so that it runs with the rest of the application
 */
@Component
public class CustomWebSocket extends WebSocketServer implements CommandLineRunner {

    final static private Logger LOGGER = Logger.getLogger(CustomWebSocket.class.getName());
    private RoomsService roomsService;

    private static final int port = 8082;

    /**
     * @param roomsService is the service providing room lists, and notifies changes to the list
     */
    @Autowired
    public CustomWebSocket(RoomsService roomsService) {
        super(new InetSocketAddress("localhost", port));
        this.roomsService = roomsService;
    }

    @Override
    public void onOpen(WebSocket webSocket, ClientHandshake clientHandshake) {
        LOGGER.log(Level.INFO, "Socket opened");
    }

    @Override
    public void onClose(WebSocket webSocket, int i, String s, boolean b) {
        LOGGER.log(Level.INFO, "Socket closed");
    }

    @Override
    public void onMessage(WebSocket webSocket, String s) {
        LOGGER.log(Level.INFO, "Message received: " + s);
        handleEvent(s);
    }

    @Override
    public void onError(WebSocket webSocket, Exception e) {
        LOGGER.log(Level.WARNING, "Error occurred", e);
    }

    @Override
    public void onStart() {
        LOGGER.log(Level.INFO, "Starting socket on port " + port);
    }

    /**
     * Called by Spring when application starts
     */
    @Override
    public void run(String... args) throws Exception {
        run();
    }

    private void handleEvent(String event) {
        switch (event) {
            case "GET ROOMS":
                try {
                    RoomData roomData = new RoomData(roomsService.getAllRooms());
                    broadcastData(roomData);
                }
                catch (Exception e) {
                    broadcastData(new ExceptionData(e));
                }
        }
    }

    private void broadcastData(Object data) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            String dataString = mapper.writeValueAsString(data);
            broadcast(dataString);
        }
        catch (JsonProcessingException jpe) {
            // TODO: 21/03/2020 handle exception
        }
    }

    private class RoomData {
        private String header = "ROOMS";
        private List<Room> roomList;

        public RoomData(List<Room> roomList) {
            this.roomList = roomList;
        }

        public String getHeader() {
            return header;
        }

        public List<Room> getRoomList() {
            return roomList;
        }
    }

    private class ExceptionData {
        private String header = "EXCEPTION";
        private Exception exception;

        public ExceptionData(Exception exception) {
            this.exception = exception;
        }

        public String getHeader() {
            return header;
        }

        public Exception getException() {
            return exception;
        }
    }
}