package es.mdef.gestionpreguntas;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class GestionpreguntasApplication {

	public static final Logger log = LoggerFactory.getLogger(GestionpreguntasApplication.class);

	public static void main(String[] args) {
		System.err.println(new BCryptPasswordEncoder().encode("password"));
		SpringApplication.run(GestionpreguntasApplication.class, args);
	}
}
