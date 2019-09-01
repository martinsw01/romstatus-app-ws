package no.akademiet.app.ws.mobileappws.XmlConverter;

import no.akademiet.app.ws.mobileappws.tests.FileTest;
import org.springframework.http.HttpEntity;
import org.springframework.web.client.RestTemplate;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.*;
import java.util.List;

public class XmlToObject {

    public static List<Room> convertXml() {
        try {
            String pathName = "/home/pi/romstatus-WS/romstatus-app-ws/mobile-app-ws/src/main/java/no/akademiet/app/ws/mobileappws/XmlConverter/rooms.xml";
            File xmlFile = new File(pathName);
            JAXBContext jaxbContext = JAXBContext.newInstance(RoomList.class);

            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            RoomList que = (RoomList) jaxbUnmarshaller.unmarshal(xmlFile);

            System.out.println("XML successfully converted");

            return que.getRooms();

        } catch (JAXBException e) {
            e.printStackTrace();
            return null;
        }
    }
}
