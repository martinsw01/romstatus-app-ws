package no.akademiet.app.ws.mobileappws.tests;


import java.io.*;

public class FileTest {
    public static String getXML(String pathName) {
        File file = new File(pathName);
        try {
            Reader reader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(reader);
            StringBuilder builder = new StringBuilder();
            String line = bufferedReader.readLine();
            while (line != null) {
                builder.append(line).append("  ");
                line = bufferedReader.readLine();
            }
            String xmlToString = builder.toString();
            bufferedReader.close();
            return xmlToString;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "e.getMessage()";
        }
    }

    public static void main(String[] args) {
        /*try {
            String stringUrl = "http://10.0.0.93:8080";
            String prefix = "/object";
            RestTemplate template = new RestTemplate();
            HttpEntity<RoomList> entity = template.getForEntity(stringUrl + prefix, RoomList.class);//template.getForEntity("http://10.0.0.135:8080/object", String.class);

            System.out.println(entity.getBody());
        }
        catch (Exception e) { e.printStackTrace(); }
        System.out.println("Done");
    }
        /*String firstXml = FileTest.getXML("/Users/martinsw/RestProjects/mobile-app-ws/src/main/java/no/akademiet/app/ws/mobileappws/XmlConverter/rooms.xml");
        char[] xml = new char[firstXml.length()];
        char a;
        for (int x = 0; x < firstXml.length(); ++x) {
            a = firstXml.charAt(x);
            xml[x] = a;
        }
        System.out.println(xml);
        System.out.println("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n<roomList id=\"1\">\n    <rooms>\n        <roomNumber>111</roomNumber>\n        <roomName>navn1</roomName>\n        <roomStatus>0</roomStatus>\n        <roomAirQuality>1256</roomAirQuality>\n    </rooms>\n    <rooms>\n        <roomNumber>112</roomNumber>\n        <roomName>navn2</roomName>\n        <roomStatus>1</roomStatus>\n        <roomAirQuality>100</roomAirQuality>\n    </rooms>\n</roomList>\n");
        System.out.println("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n<roomList id=\"1\">\n    <rooms>\n        <roomNumber>111</roomNumber>\n        <roomName>navn1</roomName>\n        <roomStatus>0</roomStatus>\n        <roomAirQuality>1256</roomAirQuality>\n    </rooms>\n    <rooms>\n        <roomNumber>112</roomNumber>\n        <roomName>navn2</roomName>\n        <roomStatus>1</roomStatus>\n        <roomAirQuality>100</roomAirQuality>\n    </rooms>\n</roomList>\n".equals(String.valueOf(xml)));
*/
    }
}
