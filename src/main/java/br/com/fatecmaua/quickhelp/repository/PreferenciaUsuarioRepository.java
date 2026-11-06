package br.com.fatecmaua.quickhelp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fatecmaua.quickhelp.model.PreferenciaUsuario;

public interface PreferenciaUsuarioRepository extends JpaRepository<PreferenciaUsuario, Long> {

	Optional<PreferenciaUsuario> findByUsuarioId(Long usuarioId);

}
