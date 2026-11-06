package br.com.fatecmaua.quickhelp.model;

import java.time.Instant;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Schema(description = "Esta é uma entidade que representa "
		+ "uma dúvida ou sugestão enviada pelo formulário do site")
@Entity
@Table(name = "mensagem_contato")
public class MensagemContato {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Schema(example = "Carla Lima")
	private String nomeRemetente;
	@Schema(example = "carla.lima@email.com")
	private String emailRemetente;
	@Schema(example = "Gostaria de saber como cadastrar um contato de emergência.")
	@Column(length = 4000)
	private String texto;
	@Schema(description = "Se vazio, a API usa o momento do envio.", example = "2026-03-11T09:00:00Z")
	private Instant enviadaEm;

	public MensagemContato() {

	}

	public MensagemContato(Long id, String nomeRemetente, String emailRemetente, String texto, Instant enviadaEm) {
		super();
		this.id = id;
		this.nomeRemetente = nomeRemetente;
		this.emailRemetente = emailRemetente;
		this.texto = texto;
		this.enviadaEm = enviadaEm;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNomeRemetente() {
		return nomeRemetente;
	}

	public void setNomeRemetente(String nomeRemetente) {
		this.nomeRemetente = nomeRemetente;
	}

	public String getEmailRemetente() {
		return emailRemetente;
	}

	public void setEmailRemetente(String emailRemetente) {
		this.emailRemetente = emailRemetente;
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	public Instant getEnviadaEm() {
		return enviadaEm;
	}

	public void setEnviadaEm(Instant enviadaEm) {
		this.enviadaEm = enviadaEm;
	}

}
