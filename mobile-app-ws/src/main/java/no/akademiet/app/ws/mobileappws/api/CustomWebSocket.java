package no.akademiet.app.ws.mobileappws.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import no.akademiet.app.ws.mobileappws.dao.RoomsDao;
import no.akademiet.app.ws.mobileappws.model.ExceptionData;
import no.akademiet.app.ws.mobileappws.model.Room;
import no.akademiet.app.ws.mobileappws.model.RoomData;
import no.akademiet.app.ws.mobileappws.service.RoomsService;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Web socket server communicating with clients.
 * Implements 'CommandLineRunner' and is annotated with @Component so that it runs with the rest of the application
 */
@Component
public class CustomWebSocket extends WebSocketServer implements CommandLineRunner, RoomsDao.RoomListChangeListener {

    final static private Logger LOGGER = Logger.getLogger(CustomWebSocket.class.getName());
    private RoomsService roomsService;

    private static final int port = 8082;

    private List<String> clients = new ArrayList<>();

    /**
     * @param roomsService is the service providing room lists, and notifies changes to the list
     */
    @Autowired
    public CustomWebSocket(RoomsService roomsService) {
        super(new InetSocketAddress("10.0.0.135", port));
        setReuseAddr(true); //Socket might be in 'TIME_WAIT', preventing application to listen to port. See https://stackoverflow.com/questions/31864369/java-net-bindexception-address-already-in-use-jvm-bind

        this.roomsService = roomsService;
        roomsService.addRoomChangeListener(this);
    }

    @Override
    public void onOpen(WebSocket webSocket, ClientHandshake clientHandshake) {
        String client = webSocket.getRemoteSocketAddress().toString();
        clients.add(client);
        notifyActiveClientsChanged();

        LOGGER.log(Level.INFO, "Socket opened: " + client);
        LOGGER.log(Level.INFO, clients.size() + " clients currently connected");
    }

    @Override
    public void onClose(WebSocket webSocket, int i, String s, boolean b) {
        String client = webSocket.getRemoteSocketAddress().toString();
        clients.remove(client);
        notifyActiveClientsChanged();

        LOGGER.log(Level.INFO, "Socket closed: " + client);
        LOGGER.log(Level.INFO, clients.size() + " clients currently connected");

    }

    @Override
    public void onMessage(WebSocket webSocket, String s) {
        LOGGER.log(Level.INFO, "Message received: '" + s + "', from " + webSocket.getRemoteSocketAddress().toString());
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

    private void notifyActiveClientsChanged() {
        try {
            roomsService.notifyActiveClientsChanged(clients.size());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handleEvent(String event) {
        switch (event) {
            case "ROOM REQUEST":
                broadcastRoomData();
        }
    }

    private void broadcastRoomData() {
        try {
            RoomData roomData = new RoomData(roomsService.getAllRooms());
            broadcastData(roomData);
        }
        catch (Exception e) {
            broadcastData(new ExceptionData(e));
        }
    }

    private void broadcastData(Object data) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            String dataString = mapper.writeValueAsString(data);
            broadcast(dataString);
        }
        catch (JsonProcessingException jpe) {
            LOGGER.log(Level.WARNING, "Could not parse to Json object", jpe);
            // TODO: 21/03/2020 handle exception
        }
    }

    @Override
    public void onRoomListChanged(List<Room> roomList) {
        broadcastData(new RoomData(roomList));
    }

}