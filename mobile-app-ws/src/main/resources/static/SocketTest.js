var socket;
var reconnect;

function connect() {
    console.log("Connecting to server...");

    socket = new WebSocket("ws://localhost:8082");  //TODO: set correct ip-address

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
        updateTable(data.rooms);
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