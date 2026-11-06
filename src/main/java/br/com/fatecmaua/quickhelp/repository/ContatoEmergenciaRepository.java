package br.com.fatecmaua.quickhelp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fatecmaua.quickhelp.model.ContatoEmergencia;

public interface ContatoEmergenciaRepository extends JpaRepository<ContatoEmergencia, Long> {

	List<ContatoEmergencia> findByUsuarioId(Long usuarioId);

}
