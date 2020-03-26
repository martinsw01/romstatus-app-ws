package no.akademiet.app.ws.mobileappws.dao;

import no.akademiet.app.ws.mobileappws.model.Room;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Repository("test-dao")
public class TestDataAccessService implements RoomsDao {
    private List<Room> generatedRooms = new ArrayList<>();

    public TestDataAccessService() {
        Logger.getLogger(TestDataAccessService.class.getName())
            .log(Level.INFO, "Creating fake dao");
    }

    @Override
    public List<Room> getAllRooms() {
        return generateRooms();
    }

    @Override
    public void notifyActiveClientsChanged(int activeClients) throws Exception {}

    @Override
    public void addRoomListChangeListener(RoomListChangeListener listener) {}

    private List<Room> generateRooms() {
        if (!generatedRooms.isEmpty()) {
            return generatedRooms;
        }
        for (int x = 111; x <= 115; x++) {
            generatedRooms.add(new Room(x%3 + 1, x, "test" + x%110, 1 == x%4, 1));
        }
        for (int y = 209; y <= 217; y++) {
            generatedRooms.add(new Room((2 * y + 1)%3 + 1, y, "test" + y%208, 1 == (3 * y -1)%4, 2));
        }
        return generatedRooms;
    }
}
