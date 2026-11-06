package br.com.fatecmaua.quickhelp.control;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.com.fatecmaua.quickhelp.model.ContatoEmergencia;
import br.com.fatecmaua.quickhelp.model.Usuario;
import br.com.fatecmaua.quickhelp.repository.ContatoEmergenciaRepository;
import br.com.fatecmaua.quickhelp.repository.UsuarioRepository;
import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/contatos")
public class ContatoEmergenciaController {

	@Autowired
	private ContatoEmergenciaRepository repC;

	@Autowired
	private UsuarioRepository repU;

	@Operation(description = "Este é um endpoint que "
			+ "retorna todos os contatos de emergência",
			tags = "Retorno de informações")
	@GetMapping("/todas")
	public List<ContatoEmergencia> retornarTodas() {
		return repC.findAll();
	}

	@Operation(description = "Este é um endpoint que "
			+ "retorna um contato de emergência por ID",
			tags = "Retorno de informações")
	@GetMapping("/{id}")
	public ContatoEmergencia retornarPorId(@PathVariable Long id) {

		Optional<ContatoEmergencia> op = repC.findById(id);

		if (op.isPresent()) {
			return op.get();
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}

	}

	@Operation(description = "Este é um endpoint que "
			+ "retorna os contatos de emergência de uma usuária",
			tags = "Retorno de informações")
	@GetMapping("/usuario/{usuarioId}")
	public List<ContatoEmergencia> retornarPorUsuario(@PathVariable Long usuarioId) {
		carregarUsuario(usuarioId);
		return repC.findByUsuarioId(usuarioId);
	}

	@Operation(description = "Este é um endpoint que "
			+ "insere um novo contato de emergência",
			tags = "Inserção de informações")
	@PostMapping("/nova")
	public ContatoEmergencia inserirContato(@RequestBody ContatoEmergencia contato) {
		if (contato.getNome() == null || contato.getNome().isBlank()
				|| contato.getTelefone() == null || contato.getTelefone().isBlank()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		}
		contato.setId(null);
		contato.setUsuario(carregarUsuario(contato.getUsuario()));
		repC.save(contato);
		return contato;
	}

	@Operation(description = "Este é um endpoint que "
			+ "atualiza um contato de emergência",
			tags = "Atualização de informações")
	@PutMapping("/{id}/atualizar")
	public ContatoEmergencia atualizarContato(
			@PathVariable Long id,
			@RequestBody ContatoEmergencia contatoAtualizado) {

		Optional<ContatoEmergencia> op = repC.findById(id);

		if (op.isPresent()) {
			if (contatoAtualizado.getNome() == null || contatoAtualizado.getNome().isBlank()
					|| contatoAtualizado.getTelefone() == null || contatoAtualizado.getTelefone().isBlank()) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
			}
			ContatoEmergencia contato = op.get();
			contato.setUsuario(carregarUsuario(contatoAtualizado.getUsuario()));
			contato.setNome(contatoAtualizado.getNome());
			contato.setTelefone(contatoAtualizado.getTelefone());
			repC.save(contato);
			return contato;
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}

	}

	@Operation(description = "Este é um endpoint que "
			+ "remove um contato de emergência",
			tags = "Remoção de informações")
	@DeleteMapping("/{id}/remover")
	public ContatoEmergencia removerContato(@PathVariable Long id) {

		Optional<ContatoEmergencia> op = repC.findById(id);

		if (op.isPresent()) {
			ContatoEmergencia contato = op.get();
			repC.deleteById(id);
			return contato;
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
