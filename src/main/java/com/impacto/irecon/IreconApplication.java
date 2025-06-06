package com.impacto.irecon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@Slf4j
public class IreconApplication implements ApplicationListener<ContextRefreshedEvent> {

	@Autowired
	private Environment environment;

	public static void main(String[] args) {
		String port = System.getenv("PORT");
		if (port == null) {
			port = "8080";
		}
		log.info("Setting server port to: {}", port);
		System.setProperty("server.port", port);
		SpringApplication app = new SpringApplication(IreconApplication.class);
		app.run(args);
	}

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		String port = environment.getProperty("server.port");
		String profile = environment.getProperty("spring.profiles.active");
		log.info("Application started with profile: {} on port: {}", profile, port);
		log.info("Active profiles: {}", String.join(", ", environment.getActiveProfiles()));
		log.info("Server address: {}", environment.getProperty("server.address"));
		log.info("Environment PORT: {}", System.getenv("PORT"));
		log.info("Environment Variables: {}", System.getenv());
	}

}


