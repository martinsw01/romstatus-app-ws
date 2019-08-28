package no.akademiet.app.ws.mobileappws;

import no.akademiet.app.ws.mobileappws.XmlConverter.Room;
import no.akademiet.app.ws.mobileappws.XmlConverter.Rooms;
import no.akademiet.app.ws.mobileappws.XmlConverter.XmlToObject;
import no.akademiet.app.ws.mobileappws.tests.FileTest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FileUploadController {

    @GetMapping(path = "/object")
    public Rooms getRooms() {
        return new Rooms(XmlToObject.convertXml());
    }

}


