package no.akademiet.app.ws.mobileappws.XmlConverter;

public class Room {
    private int roomAirQuality;
    private int roomNumber;
    private String roomName;
    private boolean roomOccupied;
    private int floor;

    public Room() {}

    public boolean isRoomOccupied() {
        return roomOccupied;
    }

    public void setRoomOccupied(boolean roomOccupied) {
        this.roomOccupied = roomOccupied;
    }

    public Room(int roomAirQuality, int roomNumber, String roomName, boolean roomOccupied, int floor) {
        super();
        this.roomAirQuality = roomAirQuality;
        this.roomNumber = roomNumber;
        this.roomName = roomName;
        this.roomOccupied = roomOccupied;
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