package no.akademiet.app.ws.mobileappws.model;

import java.util.List;

public class RoomData {
    private final String header = "ROOMS";
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
