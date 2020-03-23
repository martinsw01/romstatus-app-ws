var socket;
var reconnect;

function connect(url) {
    console.log("Connecting to server...");

    //Following code might not be needed.
    /*url = document.location.href.replace(/(^\w+:|^)\/\//, '');
    url = url.replace(/(\d{4,}.*$)/, '');
    url = "ws://" + url + "8082";*/
    //url = "ws://10.0.0.135:8082"; //TODO: set correct ip-address
    socket = new WebSocket(url);

    socket.onopen = function () {
        console.log("Connected to server!");
        clearTimeout(reconnect);
    };

    socket.onmessage = function (ev) {
        handleData(JSON.parse(ev.data));
    };

    socket.onclose = function () {
        console.log("Connection error!");
        reconnect = setTimeout(connect(), 5000);
    };


}

function handleData(data) {

    console.log("Message received: '" + data.header + "'");

    if (data.header === "ROOMS") {
        TableAdapter.adapt(data.roomList);
    }
}

function updateTable(rooms) {
    var statusElement;
    var qualityElement;

    for (var x = 0; x < rooms.length; x++) {
        statusElement = document.getElementById(rooms[x].roomNumber + "status");
        qualityElement = document.getElementById(rooms[x].roomNumber + "luft");

        statusElement.innerText = rooms[x].roomOccupied ? "Opptatt" : "Ledig";
        qualityElement.title = rooms[x].roomAirQuality;
        qualityElement.name = rooms[x].roomAirQuality;

        if (rooms[x].roomAirQuality < 700) {
            qualityElement.innerText = "Høy";
        }
        else if (rooms[x].roomAirQuality < 900) {
            qualityElement.innerText = "Middels";
        }
        else if (rooms[x].roomAirQuality < 2000) {
            qualityElement.innerText = "Lav";
        }
        else {
            qualityElement.innerText = "Out of range exception";
        }
    }
}

function requestNewData() {
    socket.send("Need data");
}

function stopServer() {
    socket.send("STOP");
}

class TableAdapter {
    static adapt(roomList) {
        var rowTemplateElement = document.getElementsByName("tableRowTemplate")[0];
        var parentElement = document.getElementById("tableBody");
        var rowElement;

        this.removeChildButTemplate(parentElement);

        rowTemplateElement.style.visibility = "visible"

        var index = 0;
        var room;
        while (room = roomList[index++]) {
            rowElement = rowTemplateElement.cloneNode(true);

            rowElement.getElementsByClassName("floor")[0].innerHTML = room.floor;
            rowElement.getElementsByClassName("number")[0].innerHTML = room.roomNumber;
            rowElement.getElementsByClassName("name")[0].innerHTML = room.roomName;
            rowElement.getElementsByClassName("status")[0].innerHTML = this.getStatus(room.roomAvailable);
            rowElement.getElementsByClassName("quality")[0].innerHTML = this.getQuality(room.roomAirQuality);

            parentElement.appendChild(rowElement);
        }

        rowTemplateElement.style.visibility = "collapse"
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