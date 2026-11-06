package br.com.fatecmaua.quickhelp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fatecmaua.quickhelp.model.Endereco;

public interface EnderecoRepository extends JpaRepository<Endereco, Long> {

	List<Endereco> findByUsuarioId(Long usuarioId);

}
