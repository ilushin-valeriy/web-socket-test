package ru.ilushin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;


/**
 * it's just  copy-paste of https://docs.spring.io/spring/docs/current/spring-framework-reference/html/websocket.html
 * plus some fantasy
 * :)
 *
 */

@SpringBootApplication
public class WebSocketTestApplication  {

	public static void main(String[] args) {
		SpringApplication.run(WebSocketTestApplication.class, args);
	}
}
