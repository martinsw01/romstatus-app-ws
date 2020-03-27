class Socket {

    constructor(handleRoomData, url) {
        this.webSocket = new WebSocket(url);
        this.handleRoomData = handleRoomData;
    }

    connect() {
        console.log("Connecting to server...");
        var reconnectDelay, handleRoomData, reconnect;

        handleRoomData = this.handleRoomData;
        reconnect = this.connect;

        /*
        Should above code even be necessary? Does not work without, though...
        When calling this inside a method belonging to 'Socket' from another object, this refers to the other object, for som weird reason
        */

        //Following code might not be needed.
        /*url = document.location.href.replace(/(^\w+:|^)\/\//, '');
        url = url.replace(/(\d{4,}.*$)/, '');
        url = "ws://" + url + "8082";*/
        //url = "ws://10.0.0.135:8082"; //TODO: set correct ip-address

        this.webSocket.onopen = function () {
            console.log("Connected to server!");
            clearTimeout(reconnectDelay);
            //console.log(this);
            this.send("ROOM REQUEST");
        };

        this.webSocket.onmessage = function (ev) {
            var data = JSON.parse(ev.data);
            if (data.header === "ROOMS") {
                handleRoomData(data.roomList);
                console.log("Received room list: " + data.roomList);
            }
            else {
                console.log("Received unrecognised message: " + data);
            }
        };

        /*
        The context of 'this' changes all the time. When 'reconnect()' (connect()) is called, it is in the context of
        'websocket'. Therefore 'this.webSocket' actually means 'webSocket.webSocket', which does not exist.
        */
        this.webSocket.onclose = function () {
            console.log("Connection error!");
            reconnectDelay = setTimeout(reconnect(), 5000);
        };
    }
}