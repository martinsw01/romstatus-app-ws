package no.akademiet.app.ws.mobileappws.api;

import no.akademiet.app.ws.mobileappws.model.Room;
import no.akademiet.app.ws.mobileappws.service.RoomsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RoomStatusController {
    private static final String CURRENT_VERSION = "preRelease";

    private final RoomsService roomsService;

    @Autowired
    public RoomStatusController(RoomsService roomsService) {
        this.roomsService = roomsService;
    }

    @Deprecated
    @GetMapping("/object")
    public List<Room> getAllRooms() {
        try {
            return roomsService.getAllRooms();
        }
        catch (Exception e) {
            return null;
        }
    }

    @GetMapping("/roomList")
    public List<Room> getRoomList() {
        try {
            return roomsService.getAllRooms();
        }
        catch (Exception e) {
            e.printStackTrace();
            // TODO: 20/03/2020 Notify client there as an error
            return null;
        }
    }

    @GetMapping("/version/{name}")
    public boolean shouldUpdate(@PathVariable String name) {
        return !CURRENT_VERSION.equals(name);
    }

}
