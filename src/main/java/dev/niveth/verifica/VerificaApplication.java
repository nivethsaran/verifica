package dev.niveth.verifica;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class VerificaApplication {

	public static void main(String[] args) {
		SpringApplication application = new SpringApplication(VerificaApplication.class);
		System.out.println(System.getProperty("verifica.life"));
		application.run(args);
	}

}
