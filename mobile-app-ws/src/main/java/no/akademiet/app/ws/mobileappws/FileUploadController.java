package no.akademiet.app.ws.mobileappws;

import no.akademiet.app.ws.mobileappws.XmlConverter.Room;
import no.akademiet.app.ws.mobileappws.XmlConverter.Rooms;
import no.akademiet.app.ws.mobileappws.XmlConverter.XmlToObject;
import no.akademiet.app.ws.mobileappws.tests.FileTest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.file.Files;
import java.nio.file.Paths;

@RestController
public class FileUploadController {

    @GetMapping(path = "/object")
    public Rooms getRooms() {
        return new Rooms(XmlToObject.convertXml());
    }

    @GetMapping(path = "/romstatus")
    public String getHtml() {
        try {
            String html = new String(Files.readAllBytes(Paths.get("/home/pi/romstatus-WS/romstatus-app-ws/mobile-app-ws/src/main/resources/static/index.html")));
            System.out.println(html);
            return html;
        }
        catch (Exception e) {
            return "";
        }
    }

}


