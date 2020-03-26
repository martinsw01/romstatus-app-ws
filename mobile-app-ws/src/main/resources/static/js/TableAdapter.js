class TableAdapter {
    static adapt(roomList) {
        var rowTemplateElement = document.getElementsByName("tableRowTemplate")[0];
        var parentElement = document.getElementById("tableBody");
        var rowElement;

        TableAdapter.removeChildButTemplate(parentElement);

        rowTemplateElement.style.visibility = "visible";

        var index = 0;
        var room;
        while (room = roomList[index++]) {
            rowElement = rowTemplateElement.cloneNode(true);

            rowElement.getElementsByClassName("floor")[0].innerHTML = room.floor;
            rowElement.getElementsByClassName("number")[0].innerHTML = room.roomNumber;
            rowElement.getElementsByClassName("name")[0].innerHTML = room.roomName;
            rowElement.getElementsByClassName("status")[0].innerHTML = TableAdapter.getStatus(room.roomAvailable);
            rowElement.getElementsByClassName("quality")[0].innerHTML = TableAdapter.getQuality(room.roomAirQuality);

            parentElement.appendChild(rowElement);
        }

        rowTemplateElement.style.visibility = "collapse";
    }

    static removeChildButTemplate(parentElement) {
        var childElement;
        while (childElement = parentElement.getElementsByTagName("tr")[1]) {
            parentElement.removeChild(childElement);
        }
    }

    static getStatus(available) {
        if (available) {
            return "Ledig";
        }
        else {
            return "Opptatt";
        }
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