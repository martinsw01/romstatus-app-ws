package no.akademiet.app.ws.mobileappws.model;

@SuppressWarnings("unused")
public class Room {
    private int roomAirQuality;
    private int roomNumber;
    private String roomName;
    private boolean roomAvailable;
    private int floor;

    public Room() {}

    public boolean isRoomAvailable() {
        return roomAvailable;
    }

    public void setRoomAvailable(boolean roomAvailable) {
        this.roomAvailable = roomAvailable;
    }

    public Room(int roomAirQuality, int roomNumber, String roomName, boolean roomAvailable, int floor) {
        super();
        this.roomAirQuality = roomAirQuality;
        this.roomNumber = roomNumber;
        this.roomName = roomName;
        this.roomAvailable = roomAvailable;
        this.floor = floor;
    }
    public int getRoomAirQuality() {
        return roomAirQuality;
    }
    public void setRoomAirQuality(int roomAirQuality) {
        this.roomAirQuality = roomAirQuality;
    }
    public int getRoomNumber() {
        return roomNumber;
    }
    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }
    public String getRoomName() {
        return roomName;
    }
    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }
    public int getFloor() {
        return floor;
    }
    public void setFloor(int floor) {
        this.floor = floor;
    }
}