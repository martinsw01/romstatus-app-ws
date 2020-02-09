package no.akademiet.app.ws.mobileappws;

import no.akademiet.app.ws.mobileappws.XmlConverter.Room;
import no.akademiet.app.ws.mobileappws.XmlConverter.Rooms;
import no.akademiet.app.ws.mobileappws.XmlConverter.XmlToObject;
import no.akademiet.app.ws.mobileappws.tests.FileTest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

@RestController
public class FileUploadController {

    private Path webPagePath;

    private final String support = "Martin Saue Winther, Ferdinand Oppsal Schnell";
    private final String appVersion = "pre-release";


    public FileUploadController() {
        String currentDir = Paths.get("").toAbsolutePath().toString();
        String pathToIndex = "/src/main/resources/static/index.html";
        webPagePath = Paths.get(currentDir, pathToIndex);

    }

    @GetMapping(path = "/object")
    public Rooms getRooms() {
        return new Rooms(XmlToObject.convertXml());
    }

    @GetMapping(path = "/romstatus")
    public String getHtml() {
        try {
            return new String(Files.readAllBytes(webPagePath));
        }
        catch (Exception e) {
            e.printStackTrace();
            return "This web page could not load. Please contact " + support +  "or your school's IT support";
        }
    }

    @GetMapping(path = "/getLatestVersion/{version}")
    public String getLatestVersion(@PathVariable("version") String version) {
        return String.valueOf(appVersion.equals(version)); // TODO: 2019-09-11 set correct version
    }

}


