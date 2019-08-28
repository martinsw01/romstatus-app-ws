package no.akademiet.app.ws.mobileappws.XmlConverter;

import java.util.List;

public class Rooms {
    List<Room> rooms;

    public Rooms(List<Room> rooms) {
        this.rooms = rooms;
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }
}
