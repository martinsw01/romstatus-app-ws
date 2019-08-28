package no.akademiet.app.ws.mobileappws.tests;

public class Room {
    private String roomName;
    private int roomNumber;
    private String roomAirQuality;
    private boolean roomStatus;

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    public String getRoomAirQuality() {
        return roomAirQuality;
    }

    public void setRoomAirQuality(String roomAirQuality) {
        this.roomAirQuality = roomAirQuality;
    }

    public boolean isRoomStatus() {
        return roomStatus;
    }

    public void setRoomStatus(boolean roomStatus) {
        this.roomStatus = roomStatus;
    }

    public Room(String roomName, int roomNumber, String roomAirQuality, boolean roomStatus) {
        this.roomName = roomName;
        this.roomNumber = roomNumber;
        this.roomAirQuality = roomAirQuality;
        this.roomStatus = roomStatus;
    }

    @Override
    public String toString() {
        return "Room [roomNumber" + roomNumber +
                ", roomName=" + roomName +
                ", roomStatus=" + roomStatus +
                ", roomAirQuality" + roomAirQuality + "]";
    }
}
