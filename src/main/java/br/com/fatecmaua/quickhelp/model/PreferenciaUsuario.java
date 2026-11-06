package br.com.fatecmaua.quickhelp.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Schema(description = "Esta é uma entidade que representa "
		+ "as preferências de tema, interface disfarçada e localização da usuária")
@Entity
@Table(name = "preferencia_usuario")
public class PreferenciaUsuario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Schema(description = "Conta dona destas preferências. Informe o id.", example = "{\"id\": 1}")
	@OneToOne
	@JoinColumn(name = "fk_usuario", unique = true)
	private Usuario usuario;
	@Enumerated(EnumType.STRING)
	@Schema(example = "ESCURO")
	private TemaEnum tema;
	@Schema(example = "false")
	private Boolean modoDisfarcadoAtivo;
	@Schema(example = "true")
	private Boolean permiteLocalizacao;

	public PreferenciaUsuario() {

	}

	public PreferenciaUsuario(Long id, Usuario usuario, TemaEnum tema, Boolean modoDisfarcadoAtivo,
			Boolean permiteLocalizacao) {
		super();
		this.id = id;
		this.usuario = usuario;
		this.tema = tema;
		this.modoDisfarcadoAtivo = modoDisfarcadoAtivo;
		this.permiteLocalizacao = permiteLocalizacao;
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

	public TemaEnum getTema() {
		return tema;
	}

	public void setTema(TemaEnum tema) {
		this.tema = tema;
	}

	public Boolean getModoDisfarcadoAtivo() {
		return modoDisfarcadoAtivo;
	}

	public void setModoDisfarcadoAtivo(Boolean modoDisfarcadoAtivo) {
		this.modoDisfarcadoAtivo = modoDisfarcadoAtivo;
	}

	public Boolean getPermiteLocalizacao() {
		return permiteLocalizacao;
	}

	public void setPermiteLocalizacao(Boolean permiteLocalizacao) {
		this.permiteLocalizacao = permiteLocalizacao;
	}

}
