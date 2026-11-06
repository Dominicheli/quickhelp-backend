package br.com.fatecmaua.quickhelp.control;

import java.time.Instant;
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

import br.com.fatecmaua.quickhelp.model.MensagemContato;
import br.com.fatecmaua.quickhelp.repository.MensagemContatoRepository;
import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/mensagens")
public class MensagemContatoController {

	@Autowired
	private MensagemContatoRepository repM;

	@Operation(description = "Este é um endpoint que "
			+ "retorna todas as mensagens de contato",
			tags = "Retorno de informações")
	@GetMapping("/todas")
	public List<MensagemContato> retornarTodas() {
		return repM.findAll();
	}

	@Operation(description = "Este é um endpoint que "
			+ "retorna uma mensagem de contato por ID",
			tags = "Retorno de informações")
	@GetMapping("/{id}")
	public MensagemContato retornarPorId(@PathVariable Long id) {

		Optional<MensagemContato> op = repM.findById(id);

		if (op.isPresent()) {
			return op.get();
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}

	}

	@Operation(description = "Este é um endpoint que "
			+ "insere uma nova mensagem de contato",
			tags = "Inserção de informações")
	@PostMapping("/nova")
	public MensagemContato inserirMensagem(@RequestBody MensagemContato mensagem) {
		validar(mensagem);
		mensagem.setId(null);
		if (mensagem.getEnviadaEm() == null) {
			mensagem.setEnviadaEm(Instant.now());
		}
		repM.save(mensagem);
		return mensagem;
	}

	@Operation(description = "Este é um endpoint que "
			+ "atualiza uma mensagem de contato",
			tags = "Atualização de informações")
	@PutMapping("/{id}/atualizar")
	public MensagemContato atualizarMensagem(
			@PathVariable Long id,
			@RequestBody MensagemContato mensagemAtualizada) {

		Optional<MensagemContato> op = repM.findById(id);

		if (op.isPresent()) {
			validar(mensagemAtualizada);
			MensagemContato mensagem = op.get();
			mensagem.setNomeRemetente(mensagemAtualizada.getNomeRemetente());
			mensagem.setEmailRemetente(mensagemAtualizada.getEmailRemetente());
			mensagem.setTexto(mensagemAtualizada.getTexto());
			if (mensagemAtualizada.getEnviadaEm() != null) {
				mensagem.setEnviadaEm(mensagemAtualizada.getEnviadaEm());
			}
			repM.save(mensagem);
			return mensagem;
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}

	}

	@Operation(description = "Este é um endpoint que "
			+ "remove uma mensagem de contato",
			tags = "Remoção de informações")
	@DeleteMapping("/{id}/remover")
	public MensagemContato removerMensagem(@PathVariable Long id) {

		Optional<MensagemContato> op = repM.findById(id);

		if (op.isPresent()) {
			MensagemContato mensagem = op.get();
			repM.deleteById(id);
			return mensagem;
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}

	}

	private void validar(MensagemContato mensagem) {
		if (mensagem.getNomeRemetente() == null || mensagem.getNomeRemetente().isBlank()
				|| mensagem.getEmailRemetente() == null || mensagem.getEmailRemetente().isBlank()
				|| mensagem.getTexto() == null || mensagem.getTexto().isBlank()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		}
	}

}
