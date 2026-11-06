package br.com.fatecmaua.quickhelp.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfig {

	@Bean
	OpenAPI configurarSwagger() {

		return new OpenAPI().info(
				new Info()
						.title("QuickHelp")
						.description("Este é um projeto que realiza "
								+ "o gerenciamento de usuárias, endereços, "
								+ "contatos de emergência, chamados SOS, "
								+ "mensagens, localização e preferências.")
						.summary("API de apoio e acionamento de emergência")
						.version("1.0"));

	}

}
