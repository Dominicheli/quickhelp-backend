package br.com.fatecmaua.quickhelp.control;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.com.fatecmaua.quickhelp.model.Usuario;
import br.com.fatecmaua.quickhelp.repository.UsuarioRepository;
import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

	@Autowired
	private UsuarioRepository repU;

	private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

	@Operation(description = "Este é um endpoint que "
			+ "retorna todas as usuárias",
			tags = "Retorno de informações")
	@GetMapping("/todas")
	public List<Usuario> retornarTodas() {
		return repU.findAll();
	}

	@Operation(description = "Este é um endpoint que "
			+ "retorna uma usuária por ID",
			tags = "Retorno de informações")
	@GetMapping("/{id}")
	public Usuario retornarPorId(@PathVariable Long id) {

		Optional<Usuario> op = repU.findById(id);

		if (op.isPresent()) {
			return op.get();
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}

	}

	@Operation(description = "Este é um endpoint que "
			+ "autentica pelo e-mail e senha e retorna o perfil",
			tags = "Autenticação")
	@PostMapping("/login")
	public Usuario login(@RequestBody Usuario credencial) {

		if (credencial.getEmail() == null || credencial.getSenha() == null
				|| credencial.getSenha().isBlank()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		}

		Optional<Usuario> op = repU.findByEmail(credencial.getEmail().trim().toLowerCase());

		if (op.isPresent() && encoder.matches(credencial.getSenha(), op.get().getSenhaHash())) {
			return op.get();
		} else {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
		}

	}

	@Operation(description = "Este é um endpoint que "
			+ "insere uma nova usuária e grava a senha como hash",
			tags = "Inserção de informações")
	@PostMapping("/nova")
	public Usuario inserirUsuario(@RequestBody Usuario usuario) {

		validarCadastro(usuario);
		String email = usuario.getEmail().trim().toLowerCase();

		if (repU.findByEmail(email).isPresent()) {
			throw new ResponseStatusException(HttpStatus.CONFLICT);
		}

		usuario.setId(null);
		usuario.setEmail(email);
		usuario.setSenhaHash(encoder.encode(usuario.getSenha()));
		usuario.setSenha(null);
		repU.save(usuario);
		return usuario;

	}

	@Operation(description = "Este é um endpoint que "
			+ "atualiza uma usuária. A senha só muda se for enviada.",
			tags = "Atualização de informações")
	@PutMapping("/{id}/atualizar")
	public Usuario atualizarUsuario(
			@PathVariable Long id,
			@RequestBody Usuario usuarioAtualizada) {

		Optional<Usuario> op = repU.findById(id);

		if (op.isPresent()) {
			if (usuarioAtualizada.getNome() == null || usuarioAtualizada.getNome().isBlank()
					|| usuarioAtualizada.getEmail() == null || usuarioAtualizada.getEmail().isBlank()
					|| usuarioAtualizada.getPerfil() == null) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
			}

			String email = usuarioAtualizada.getEmail().trim().toLowerCase();
			Optional<Usuario> emailExistente = repU.findByEmail(email);
			if (emailExistente.isPresent() && !emailExistente.get().getId().equals(id)) {
				throw new ResponseStatusException(HttpStatus.CONFLICT);
			}

			Usuario usuario = op.get();
			usuario.setNome(usuarioAtualizada.getNome());
			usuario.setEmail(email);
			usuario.setPerfil(usuarioAtualizada.getPerfil());

			if (usuarioAtualizada.getSenha() != null && !usuarioAtualizada.getSenha().isBlank()) {
				usuario.setSenhaHash(encoder.encode(usuarioAtualizada.getSenha()));
			}

			repU.save(usuario);
			return usuario;
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}

	}

	@Operation(description = "Este é um endpoint que "
			+ "remove uma usuária",
			tags = "Remoção de informações")
	@DeleteMapping("/{id}/remover")
	public Usuario removerUsuario(@PathVariable Long id) {

		Optional<Usuario> op = repU.findById(id);

		if (op.isPresent()) {
			Usuario usuario = op.get();
			try {
				repU.deleteById(id);
			} catch (DataIntegrityViolationException ex) {
				throw new ResponseStatusException(HttpStatus.CONFLICT);
			}
			return usuario;
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}

	}

	private void validarCadastro(Usuario usuario) {
		if (usuario.getNome() == null || usuario.getNome().isBlank()
				|| usuario.getEmail() == null || usuario.getEmail().isBlank()
				|| usuario.getSenha() == null || usuario.getSenha().isBlank()
				|| usuario.getPerfil() == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		}
	}

}
