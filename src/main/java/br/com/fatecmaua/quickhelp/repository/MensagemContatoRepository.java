package br.com.fatecmaua.quickhelp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fatecmaua.quickhelp.model.MensagemContato;

public interface MensagemContatoRepository extends JpaRepository<MensagemContato, Long> {

}
