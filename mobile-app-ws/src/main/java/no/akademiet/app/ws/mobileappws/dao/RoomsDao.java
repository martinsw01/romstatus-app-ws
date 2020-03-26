package no.akademiet.app.ws.mobileappws.dao;

import no.akademiet.app.ws.mobileappws.model.Room;

import java.util.List;

public interface RoomsDao {
    List<Room> getAllRooms() throws Exception;
    void notifyActiveClientsChanged(int activeClients) throws Exception;

    void addRoomListChangeListener(RoomListChangeListener listener);
    interface RoomListChangeListener {
        void onRoomListChanged(List<Room> roomList);
    }
    // TODO: 23/03/2020 Should exceptions be handled by the dao or the api?
}
