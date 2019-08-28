package no.akademiet.app.ws.mobileappws.XmlConverter;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class RoomList {
    private int id;
    private List<Room> rooms;
    public RoomList() {}
    public RoomList(int id, List<Room> rooms) {
        super();
        this.id = id;
        this.rooms = rooms;
    }

    @XmlElement
    public List<Room> getRooms() {
        return rooms;
    }
    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }
}