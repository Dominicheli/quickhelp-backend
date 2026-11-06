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

import br.com.fatecmaua.quickhelp.model.Endereco;
import br.com.fatecmaua.quickhelp.model.Usuario;
import br.com.fatecmaua.quickhelp.repository.EnderecoRepository;
import br.com.fatecmaua.quickhelp.repository.UsuarioRepository;
import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/enderecos")
public class EnderecoController {

	@Autowired
	private EnderecoRepository repE;

	@Autowired
	private UsuarioRepository repU;

	@Operation(description = "Este é um endpoint que "
			+ "retorna todos os endereços",
			tags = "Retorno de informações")
	@GetMapping("/todas")
	public List<Endereco> retornarTodas() {
		return repE.findAll();
	}

	@Operation(description = "Este é um endpoint que "
			+ "retorna um endereço por ID",
			tags = "Retorno de informações")
	@GetMapping("/{id}")
	public Endereco retornarPorId(@PathVariable Long id) {

		Optional<Endereco> op = repE.findById(id);

		if (op.isPresent()) {
			return op.get();
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}

	}

	@Operation(description = "Este é um endpoint que "
			+ "retorna os endereços de uma usuária",
			tags = "Retorno de informações")
	@GetMapping("/usuario/{usuarioId}")
	public List<Endereco> retornarPorUsuario(@PathVariable Long usuarioId) {
		carregarUsuario(usuarioId);
		return repE.findByUsuarioId(usuarioId);
	}

	@Operation(description = "Este é um endpoint que "
			+ "insere um novo endereço",
			tags = "Inserção de informações")
	@PostMapping("/nova")
	public Endereco inserirEndereco(@RequestBody Endereco endereco) {
		endereco.setId(null);
		endereco.setUsuario(carregarUsuario(endereco.getUsuario()));
		repE.save(endereco);
		return endereco;
	}

	@Operation(description = "Este é um endpoint que "
			+ "atualiza um endereço",
			tags = "Atualização de informações")
	@PutMapping("/{id}/atualizar")
	public Endereco atualizarEndereco(
			@PathVariable Long id,
			@RequestBody Endereco enderecoAtualizado) {

		Optional<Endereco> op = repE.findById(id);

		if (op.isPresent()) {
			Endereco endereco = op.get();
			endereco.setUsuario(carregarUsuario(enderecoAtualizado.getUsuario()));
			endereco.setEstado(enderecoAtualizado.getEstado());
			endereco.setCidade(enderecoAtualizado.getCidade());
			endereco.setBairro(enderecoAtualizado.getBairro());
			endereco.setLogradouro(enderecoAtualizado.getLogradouro());
			endereco.setNumero(enderecoAtualizado.getNumero());
			endereco.setComplemento(enderecoAtualizado.getComplemento());
			repE.save(endereco);
			return endereco;
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}

	}

	@Operation(description = "Este é um endpoint que "
			+ "remove um endereço",
			tags = "Remoção de informações")
	@DeleteMapping("/{id}/remover")
	public Endereco removerEndereco(@PathVariable Long id) {

		Optional<Endereco> op = repE.findById(id);

		if (op.isPresent()) {
			Endereco endereco = op.get();
			repE.deleteById(id);
			return endereco;
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
