package no.akademiet.app.ws.mobileappws.service;

import no.akademiet.app.ws.mobileappws.dao.RoomsDao;
import no.akademiet.app.ws.mobileappws.model.Room;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomsService {

    private final RoomsDao roomsDao;

    @Autowired
    public RoomsService(@Qualifier("database-dao") RoomsDao roomsDao) {
        this.roomsDao = roomsDao;
    }

    public List<Room> getAllRooms() throws Exception {
        return roomsDao.getAllRooms();
    }
}
