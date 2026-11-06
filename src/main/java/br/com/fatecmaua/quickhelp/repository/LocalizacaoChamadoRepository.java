package br.com.fatecmaua.quickhelp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.fatecmaua.quickhelp.model.LocalizacaoChamado;

public interface LocalizacaoChamadoRepository extends JpaRepository<LocalizacaoChamado, Long> {

	@Query("from LocalizacaoChamado loc where loc.chamado.id = :chamadoId "
			+ "order by loc.registradaEm desc")
	List<LocalizacaoChamado> retornarPorChamado(Long chamadoId);

}
