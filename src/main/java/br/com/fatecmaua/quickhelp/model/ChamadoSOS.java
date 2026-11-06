package br.com.fatecmaua.quickhelp.model;

import java.time.Instant;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Schema(description = "Esta é uma entidade que representa "
		+ "um pedido de socorro e o acompanhamento do atendimento")
@Entity
@Table(name = "chamado_sos")
public class ChamadoSOS {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Schema(description = "Usuária que acionou o SOS. Informe o id.", example = "{\"id\": 1}")
	@ManyToOne
	@JoinColumn(name = "fk_usuario")
	private Usuario usuario;
	@Schema(description = "Data e hora do acionamento. Se vazio, a API usa o momento atual.", example = "2026-03-10T14:30:00Z")
	private Instant criadoEm;
	@Enumerated(EnumType.STRING)
	@Schema(example = "ABERTO")
	private StatusChamadoEnum status;
	@Enumerated(EnumType.STRING)
	@Schema(example = "BOTAO_SOS")
	private AcionamentoEnum modoAcionamento;
	@Schema(description = "Pessoa do backoffice responsável. Informe o id.", example = "{\"id\": 2}")
	@ManyToOne
	@JoinColumn(name = "fk_responsavel")
	private Usuario responsavel;
	@Schema(example = "2026-03-10T15:00:00Z")
	private Instant encerradoEm;

	public ChamadoSOS() {

	}

	public ChamadoSOS(Long id, Usuario usuario, Instant criadoEm, StatusChamadoEnum status,
			AcionamentoEnum modoAcionamento, Usuario responsavel, Instant encerradoEm) {
		super();
		this.id = id;
		this.usuario = usuario;
		this.criadoEm = criadoEm;
		this.status = status;
		this.modoAcionamento = modoAcionamento;
		this.responsavel = responsavel;
		this.encerradoEm = encerradoEm;
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

	public Instant getCriadoEm() {
		return criadoEm;
	}

	public void setCriadoEm(Instant criadoEm) {
		this.criadoEm = criadoEm;
	}

	public StatusChamadoEnum getStatus() {
		return status;
	}

	public void setStatus(StatusChamadoEnum status) {
		this.status = status;
	}

	public AcionamentoEnum getModoAcionamento() {
		return modoAcionamento;
	}

	public void setModoAcionamento(AcionamentoEnum modoAcionamento) {
		this.modoAcionamento = modoAcionamento;
	}

	public Usuario getResponsavel() {
		return responsavel;
	}

	public void setResponsavel(Usuario responsavel) {
		this.responsavel = responsavel;
	}

	public Instant getEncerradoEm() {
		return encerradoEm;
	}

	public void setEncerradoEm(Instant encerradoEm) {
		this.encerradoEm = encerradoEm;
	}

}
