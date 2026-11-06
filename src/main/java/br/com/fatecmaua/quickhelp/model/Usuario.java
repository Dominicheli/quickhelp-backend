package br.com.fatecmaua.quickhelp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Schema(description = "Esta é uma entidade que representa "
		+ "a conta de uma usuária ou de uma pessoa do backoffice")
@Entity
@Table(name = "usuario")
public class Usuario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Schema(description = "Este atributo representa o nome da pessoa", example = "Maria Souza")
	private String nome;
	@Schema(description = "Este atributo representa o e-mail de acesso", example = "maria.souza@email.com")
	@Column(unique = true)
	private String email;
	@JsonIgnore
	@Column(name = "senha_hash")
	private String senhaHash;
	@Transient
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	@Schema(description = "Senha em texto. A API grava somente o hash e não devolve este campo.", example = "123456", accessMode = Schema.AccessMode.WRITE_ONLY)
	private String senha;
	@Enumerated(EnumType.STRING)
	@Schema(description = "Este atributo representa o perfil de acesso", example = "USUARIA")
	private PerfilUsuarioEnum perfil;

	public Usuario() {

	}

	public Usuario(Long id, String nome, String email, String senhaHash, PerfilUsuarioEnum perfil) {
		super();
		this.id = id;
		this.nome = nome;
		this.email = email;
		this.senhaHash = senhaHash;
		this.perfil = perfil;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenhaHash() {
		return senhaHash;
	}

	public void setSenhaHash(String senhaHash) {
		this.senhaHash = senhaHash;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public PerfilUsuarioEnum getPerfil() {
		return perfil;
	}

	public void setPerfil(PerfilUsuarioEnum perfil) {
		this.perfil = perfil;
	}

}
