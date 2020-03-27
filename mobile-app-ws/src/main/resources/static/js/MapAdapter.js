class MapAdapter {
    static adaptRoom(roomElement, room) {
        var parentElement = document.getElementById('floor' + room.floor);

        roomElement.style.display = "";
        roomElement.className = "room | " + MapAdapter.getClass(room);
        roomElement.getElementsByClassName("roomNumber")[0].innerHTML = room.roomNumber;
        roomElement.getElementsByClassName("statusDetails")[0].innerHTML = MapAdapter.getStatus(room.roomAvailable);
        roomElement.getElementsByClassName("qualityDetails")[0].innerHTML = MapAdapter.getQuality(room.roomAirQuality);
        parentElement.appendChild(roomElement);
    }
    
    static adaptMap(roomList) {
        var index, room, templateElement, roomElement;
        var index = 0;

        MapAdapter.clearMap();

        templateElement = document.getElementsByName("roomTemplate")[0];
        while (room = roomList[index++]) {
            roomElement = templateElement.cloneNode(true);
            MapAdapter.adaptRoom(roomElement, room);
        }
    }

    static clearMap() {
        document.getElementById('floor1').innerHTML = "";
        document.getElementById('floor2').innerHTML = "";
    }

    static getStatus(available) {
        if (available) {
            return "Ledig";
        }
        return "Opptatt";
    }
    
    static getQuality(quality) {
        switch (quality) {
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

    static getClass(room) {
        if (!room.roomAvailable) {
            return "unavailable";
        }
        switch (room.roomAirQuality) {
            case 1:
                return "qualityHigh";
            case 2:
                return "qualityMedium";
            case 3:
                return "qualityPoor";
            default:
                return "unavailable";
        }
    }
}

