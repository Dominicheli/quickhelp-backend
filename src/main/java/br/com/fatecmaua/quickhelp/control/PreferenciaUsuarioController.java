package br.com.fatecmaua.quickhelp.control;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.com.fatecmaua.quickhelp.model.PreferenciaUsuario;
import br.com.fatecmaua.quickhelp.model.TemaEnum;
import br.com.fatecmaua.quickhelp.model.Usuario;
import br.com.fatecmaua.quickhelp.repository.PreferenciaUsuarioRepository;
import br.com.fatecmaua.quickhelp.repository.UsuarioRepository;
import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/preferencias")
public class PreferenciaUsuarioController {

	@Autowired
	private PreferenciaUsuarioRepository repP;

	@Autowired
	private UsuarioRepository repU;

	@Operation(description = "Este é um endpoint que "
			+ "retorna todas as preferências",
			tags = "Retorno de informações")
	@GetMapping("/todas")
	public List<PreferenciaUsuario> retornarTodas() {
		return repP.findAll();
	}

	@Operation(description = "Este é um endpoint que "
			+ "retorna uma preferência por ID",
			tags = "Retorno de informações")
	@GetMapping("/{id}")
	public PreferenciaUsuario retornarPorId(@PathVariable Long id) {

		Optional<PreferenciaUsuario> op = repP.findById(id);

		if (op.isPresent()) {
			return op.get();
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}

	}

	@Operation(description = "Este é um endpoint que "
			+ "retorna a preferência de uma usuária",
			tags = "Retorno de informações")
	@GetMapping("/usuario/{usuarioId}")
	public PreferenciaUsuario retornarPorUsuario(@PathVariable Long usuarioId) {
		carregarUsuario(usuarioId);
		Optional<PreferenciaUsuario> op = repP.findByUsuarioId(usuarioId);
		if (op.isPresent()) {
			return op.get();
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
	}

	@Operation(description = "Este é um endpoint que "
			+ "insere as preferências de uma usuária",
			tags = "Inserção de informações")
	@PostMapping("/nova")
	public PreferenciaUsuario inserirPreferencia(@RequestBody PreferenciaUsuario preferencia) {
		Usuario usuario = carregarUsuario(preferencia.getUsuario());
		if (repP.findByUsuarioId(usuario.getId()).isPresent()) {
			throw new ResponseStatusException(HttpStatus.CONFLICT);
		}
		if (preferencia.getTema() == null) {
			preferencia.setTema(TemaEnum.CLARO);
		}
		if (preferencia.getModoDisfarcadoAtivo() == null) {
			preferencia.setModoDisfarcadoAtivo(false);
		}
		if (preferencia.getPermiteLocalizacao() == null) {
			preferencia.setPermiteLocalizacao(false);
		}
		preferencia.setId(null);
		preferencia.setUsuario(usuario);
		repP.save(preferencia);
		return preferencia;
	}

	@Operation(description = "Este é um endpoint que "
			+ "atualiza o tema de uma preferência",
			tags = "Atualização parcial de informações")
	@PatchMapping("/{id}/tema")
	public PreferenciaUsuario atualizarTema(
			@PathVariable Long id,
			@RequestParam(name = "tema") TemaEnum tema) {

		Optional<PreferenciaUsuario> op = repP.findById(id);

		if (op.isPresent()) {
			PreferenciaUsuario preferencia = op.get();
			preferencia.setTema(tema);
			repP.save(preferencia);
			return preferencia;
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}

	}

	@Operation(description = "Este é um endpoint que "
			+ "atualiza as preferências de uma usuária",
			tags = "Atualização de informações")
	@PutMapping("/{id}/atualizar")
	public PreferenciaUsuario atualizarPreferencia(
			@PathVariable Long id,
			@RequestBody PreferenciaUsuario preferenciaAtualizada) {

		Optional<PreferenciaUsuario> op = repP.findById(id);

		if (op.isPresent()) {
			Usuario usuario = carregarUsuario(preferenciaAtualizada.getUsuario());
			Optional<PreferenciaUsuario> existente = repP.findByUsuarioId(usuario.getId());
			if (existente.isPresent() && !existente.get().getId().equals(id)) {
				throw new ResponseStatusException(HttpStatus.CONFLICT);
			}
			PreferenciaUsuario preferencia = op.get();
			preferencia.setUsuario(usuario);
			preferencia.setTema(preferenciaAtualizada.getTema());
			preferencia.setModoDisfarcadoAtivo(preferenciaAtualizada.getModoDisfarcadoAtivo());
			preferencia.setPermiteLocalizacao(preferenciaAtualizada.getPermiteLocalizacao());
			repP.save(preferencia);
			return preferencia;
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}

	}

	@Operation(description = "Este é um endpoint que "
			+ "remove as preferências de uma usuária",
			tags = "Remoção de informações")
	@DeleteMapping("/{id}/remover")
	public PreferenciaUsuario removerPreferencia(@PathVariable Long id) {

		Optional<PreferenciaUsuario> op = repP.findById(id);

		if (op.isPresent()) {
			PreferenciaUsuario preferencia = op.get();
			repP.deleteById(id);
			return preferencia;
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}

	}

	private Usuario carregarUsuario(Usuario usuario) {
		if (usuario == null || usuario.getId() == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		}
		return carregarUsuario(usuario.getId());
	}

	private Usuario carregarUsuario(Long id) {
		Optional<Usuario> op = repU.findById(id);
		if (op.isPresent()) {
			return op.get();
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
	}

}
