package br.com.fatecmaua.quickhelp.projection;

import java.time.OffsetDateTime;

public interface ChamadoProjection {

	public Long getId();

	public String getUsuaria();

	public String getStatus();

	public OffsetDateTime getCriadoEm();

}
