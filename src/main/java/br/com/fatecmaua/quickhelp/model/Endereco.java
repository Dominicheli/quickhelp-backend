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
		+ "um endereço vinculado à conta da usuária")
@Entity
@Table(name = "endereco")
public class Endereco {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Schema(description = "Usuária dona deste endereço. Informe o id.", example = "{\"id\": 1}")
	@ManyToOne
	@JoinColumn(name = "fk_usuario")
	private Usuario usuario;
	@Schema(example = "SP")
	private String estado;
	@Schema(example = "São Paulo")
	private String cidade;
	@Schema(example = "Centro")
	private String bairro;
	@Schema(example = "Rua das Flores")
	private String logradouro;
	@Schema(example = "100")
	private String numero;
	@Schema(example = "Apto 12")
	private String complemento;

	public Endereco() {

	}

	public Endereco(Long id, Usuario usuario, String estado, String cidade, String bairro, String logradouro,
			String numero, String complemento) {
		super();
		this.id = id;
		this.usuario = usuario;
		this.estado = estado;
		this.cidade = cidade;
		this.bairro = bairro;
		this.logradouro = logradouro;
		this.numero = numero;
		this.complemento = complemento;
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

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

}
