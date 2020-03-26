class MapAdapter {
    static adaptRoom(room) {
        var roomElement = document.getElementById(room.roomNumber + "K");
        roomElement.getElementsByClassName("status")[0].innerHTML = MapAdapter.getStatus(room.roomAvailable);
        roomElement.getElementsByClassName("quality")[0].innerHTML = MapAdapter.getQuality(room.roomAirQuality);
    }
    
    static adaptMap(roomList) {
        var index = 0;
        var room;
        while (room = roomList[index++]) {
            MapAdapter.adaptRoom(room);
        }
    }
    
    static getStatus(available) {
        if (available) {
            return "Ledig";
        }
        return "Opptatt";
    }
    
    static getQuality(quality) {
        switch(quality) {
            case 1:
                return "Høy";
            case 2:
                return "Medium";
            case 3:
                return "Lav";
            default:
                return "N/A";
        }
    }
}

