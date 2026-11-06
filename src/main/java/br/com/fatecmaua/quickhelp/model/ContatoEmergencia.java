package br.com.fatecmaua.quickhelp.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Schema(description = "Esta é uma entidade que representa "
		+ "um familiar ou amigo que recebe alertas de emergência")
@Entity
@Table(name = "contato_emergencia")
public class ContatoEmergencia {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Schema(description = "Usuária que cadastrou o contato. Informe o id.", example = "{\"id\": 1}")
	@ManyToOne
	@JoinColumn(name = "fk_usuario")
	private Usuario usuario;
	@Schema(example = "Joana Souza")
	private String nome;
	@Schema(example = "11999990000")
	private String telefone;

	public ContatoEmergencia() {

	}

	public ContatoEmergencia(Long id, Usuario usuario, String nome, String telefone) {
		super();
		this.id = id;
		this.usuario = usuario;
		this.nome = nome;
		this.telefone = telefone;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

}
