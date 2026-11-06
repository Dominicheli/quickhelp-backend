package br.com.fatecmaua.quickhelp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories
@ComponentScan
@EntityScan
@SpringBootApplication
public class QuickhelpApplication {

	public static void main(String[] args) {
		SpringApplication.run(QuickhelpApplication.class, args);
	}

}
