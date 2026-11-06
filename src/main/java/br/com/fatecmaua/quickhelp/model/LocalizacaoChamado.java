package br.com.fatecmaua.quickhelp.model;

import java.math.BigDecimal;
import java.time.Instant;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Schema(description = "Esta é uma entidade que representa "
		+ "uma atualização de localização durante um chamado SOS")
@Entity
@Table(name = "localizacao_chamado")
public class LocalizacaoChamado {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Schema(description = "Chamado ao qual esta posição pertence. Informe o id.", example = "{\"id\": 1}")
	@ManyToOne
	@JoinColumn(name = "fk_chamado")
	private ChamadoSOS chamado;
	@Schema(example = "-23.550520")
	@Column(precision = 9, scale = 6)
	private BigDecimal latitude;
	@Schema(example = "-46.633308")
	@Column(precision = 9, scale = 6)
	private BigDecimal longitude;
	@Schema(example = "12.5")
	private Double precisaoMetros;
	@Schema(description = "Se vazio, a API usa o momento do registro.", example = "2026-03-10T14:31:00Z")
	private Instant registradaEm;

	public LocalizacaoChamado() {

	}

	public LocalizacaoChamado(Long id, ChamadoSOS chamado, BigDecimal latitude, BigDecimal longitude,
			Double precisaoMetros, Instant registradaEm) {
		super();
		this.id = id;
		this.chamado = chamado;
		this.latitude = latitude;
		this.longitude = longitude;
		this.precisaoMetros = precisaoMetros;
		this.registradaEm = registradaEm;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ChamadoSOS getChamado() {
		return chamado;
	}

	public void setChamado(ChamadoSOS chamado) {
		this.chamado = chamado;
	}

	public BigDecimal getLatitude() {
		return latitude;
	}

	public void setLatitude(BigDecimal latitude) {
		this.latitude = latitude;
	}

	public BigDecimal getLongitude() {
		return longitude;
	}

	public void setLongitude(BigDecimal longitude) {
		this.longitude = longitude;
	}

	public Double getPrecisaoMetros() {
		return precisaoMetros;
	}

	public void setPrecisaoMetros(Double precisaoMetros) {
		this.precisaoMetros = precisaoMetros;
	}

	public Instant getRegistradaEm() {
		return registradaEm;
	}

	public void setRegistradaEm(Instant registradaEm) {
		this.registradaEm = registradaEm;
	}

}
