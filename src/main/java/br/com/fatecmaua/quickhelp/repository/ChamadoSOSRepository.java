package br.com.fatecmaua.quickhelp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.fatecmaua.quickhelp.model.ChamadoSOS;
import br.com.fatecmaua.quickhelp.model.StatusChamadoEnum;
import br.com.fatecmaua.quickhelp.projection.ChamadoProjection;

public interface ChamadoSOSRepository extends JpaRepository<ChamadoSOS, Long> {

	List<ChamadoSOS> findByUsuarioId(Long usuarioId);

	@Query("from ChamadoSOS ch where ch.status = :status")
	List<ChamadoSOS> retornarPorStatusOtimizada1(StatusChamadoEnum status);

	@Query(nativeQuery = true, value = "select ch.id, usu.nome usuaria, "
			+ "ch.status, ch.criado_em criadoEm from chamado_sos ch "
			+ "inner join usuario usu on (ch.fk_usuario = usu.id) "
			+ "where ch.status = :status")
	List<ChamadoProjection> retornarPorStatusOtimizada2(String status);

}
