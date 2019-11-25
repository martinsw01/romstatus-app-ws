package no.akademiet.app.ws.mobileappws.WebSocket;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import no.akademiet.app.ws.mobileappws.XmlConverter.Room;
import no.akademiet.app.ws.mobileappws.XmlConverter.Rooms;
import no.akademiet.app.ws.mobileappws.XmlConverter.XmlToObject;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;

public class CustomWebSocket extends WebSocketServer {

    final static private Logger LOGGER = new Logger();

    private FileChangeListener fileChangeListener = new FileChangeListener() {
        @Override
        void notifyFileChanged() {
            sendRoomData(new RoomData(XmlToObject.convertXml()));
        }
    };

    public CustomWebSocket() {
        super(new InetSocketAddress("localhost", 8082));
    }

    @Override
    public void onOpen(WebSocket webSocket, ClientHandshake clientHandshake) {
        LOGGER.info("Socket opened");
    }

    @Override
    public void onClose(WebSocket webSocket, int i, String s, boolean b) {
        LOGGER.info("Socket closed");
    }

    @Override
    public void onMessage(WebSocket webSocket, String s) {
        LOGGER.info("Message received: " + s);
        handleEvent(s);
    }

    @Override
    public void onError(WebSocket webSocket, Exception e) {
        LOGGER.error(e);
    }

    @Override
    public void onStart() {
        LOGGER.info("Socket started");

        new FileChangeListenerThread("File Change Listener").start();
    }

    private void handleEvent(String event) {

        if ("STOP".equals(event)) {
            try {
                fileChangeListener.stopListening();
                stop(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        else {
            List<Room> rooms = new ArrayList<>();
            for (int x = 111; x <= 112; x++) {
                rooms.add(new Room(23, 433, "Kafka", true, 1));
            }

            RoomData data = new RoomData(rooms);

            sendRoomData(data);
        }
    }

    private void sendRoomData(RoomData roomData) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            String roomDataString = mapper.writeValueAsString(roomData);
            LOGGER.info("Sending new room data:" + roomDataString);
            broadcast(roomDataString);
        }
        catch (JsonProcessingException jpe) {
            LOGGER.error(jpe);
        }
    }

    class FileChangeListenerThread extends Thread {
        public FileChangeListenerThread(String name) {
            super(name);
        }

        @Override
        public void run() {
            fileChangeListener.listen();
        }
    }

    static class Logger {
        void info(String info) {
            System.out.println("INFO:  " + info);
        }
        void error(Exception e) {
            System.out.println("ERROR:  " + e.getMessage());
            e.printStackTrace();
        }
    }

    class RoomData extends Rooms {
        String header = "ROOMS";

        public String getHeader() {
            return header;
        }

        public RoomData(List<Room> rooms) {
            super(rooms);
        }
    }
}