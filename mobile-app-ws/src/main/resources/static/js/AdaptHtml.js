function adapt(roomList) {
    var rowTemplateElement = document.getElementsByName("tableRowTemplate")[0];
    var parentElement = document.getElementById("tableBody");
    var rowElement;

    removeChildButTemplate(parentElement);

    rowTemplateElement.style.visibility = "visible"

    var index = 0;
    while (room = roomList[index++]) {
        console.log(room)
        rowElement = rowTemplateElement.cloneNode(true);

        rowElement.getElementsByClassName("floor")[0].innerHTML = room.floor;
        rowElement.getElementsByClassName("number")[0].innerHTML = room.roomNumber;
        rowElement.getElementsByClassName("name")[0].innerHTML = room.roomName;
        rowElement.getElementsByClassName("status")[0].innerHTML = getStatus(room.roomAvailable);
        rowElement.getElementsByClassName("quality")[0].innerHTML = getQuality(room.roomAirQuality);

        parentElement.appendChild(rowElement);
    }

    rowTemplateElement.style.visibility = "collapse"
}

function removeChildButTemplate(parentElement) {
    while (childElement = parentElement.getElementsByTagName("tr")[1]) {
        parentElement.removeChild(childElement);
    }
}

function getStatus(available) {
    if (available) {
        return "Ledig";
    }
    else {
        return "Opptatt";
    }
}

function getQuality(quality) {
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

function requestRooms() {
    url = document.location.href + "/object"

    console.log("ksdl");
    xmlhttp = new XMLHttpRequest();
    xmlhttp.onreadystatechange = function() {
      if (this.readyState == 4 && this.status == 200) {
        roomList = JSON.parse(this.responseText);
        adapt(roomList);
        console.log(roomList);
      }
    };
    xmlhttp.open("GET", url, true);
    xmlhttp.send();
}