package no.akademiet.app.ws.mobileappws;

import no.akademiet.app.ws.mobileappws.WebSocket.CustomWebSocket;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class MobileAppWsApplication {
	public static void main(String[] args) {
		SpringApplication.run(MobileAppWsApplication.class, args);
		new CustomWebSocket().run();
	}

}
