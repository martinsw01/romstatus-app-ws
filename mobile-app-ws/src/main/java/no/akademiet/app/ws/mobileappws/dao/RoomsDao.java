package no.akademiet.app.ws.mobileappws.dao;

import no.akademiet.app.ws.mobileappws.model.Room;

import java.util.List;

public interface RoomsDao {
    List<Room> getAllRooms() throws Exception;
}
