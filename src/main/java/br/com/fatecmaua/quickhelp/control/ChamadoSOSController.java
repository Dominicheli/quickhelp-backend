package br.com.fatecmaua.quickhelp.control;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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

import br.com.fatecmaua.quickhelp.model.AcionamentoEnum;
import br.com.fatecmaua.quickhelp.model.ChamadoSOS;
import br.com.fatecmaua.quickhelp.model.StatusChamadoEnum;
import br.com.fatecmaua.quickhelp.model.Usuario;
import br.com.fatecmaua.quickhelp.projection.ChamadoProjection;
import br.com.fatecmaua.quickhelp.repository.ChamadoSOSRepository;
import br.com.fatecmaua.quickhelp.repository.UsuarioRepository;
import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/chamados")
public class ChamadoSOSController {

	@Autowired
	private ChamadoSOSRepository repCh;

	@Autowired
	private UsuarioRepository repU;

	@Operation(description = "Este é um endpoint que "
			+ "retorna todos os chamados SOS",
			tags = "Retorno de informações")
	@GetMapping("/todas")
	public List<ChamadoSOS> retornarTodas() {
		return repCh.findAll();
	}

	@Operation(description = "Este é um endpoint que "
			+ "retorna um chamado SOS por ID",
			tags = "Retorno de informações")
	@GetMapping("/{id}")
	public ChamadoSOS retornarPorId(@PathVariable Long id) {

		Optional<ChamadoSOS> op = repCh.findById(id);

		if (op.isPresent()) {
			return op.get();
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}

	}

	@Operation(description = "Este é um endpoint que "
			+ "retorna os chamados SOS de uma usuária",
			tags = "Retorno de informações")
	@GetMapping("/usuario/{usuarioId}")
	public List<ChamadoSOS> retornarPorUsuario(@PathVariable Long usuarioId) {
		carregarUsuario(usuarioId);
		return repCh.findByUsuarioId(usuarioId);
	}

	@Operation(description = "Este é um endpoint que "
			+ "insere um novo chamado SOS",
			tags = "Inserção de informações")
	@PostMapping("/nova")
	public ChamadoSOS inserirChamado(@RequestBody ChamadoSOS chamado) {
		chamado.setId(null);
		chamado.setUsuario(carregarUsuario(chamado.getUsuario()));
		chamado.setResponsavel(carregarResponsavel(chamado.getResponsavel()));
		if (chamado.getCriadoEm() == null) {
			chamado.setCriadoEm(Instant.now());
		}
		if (chamado.getStatus() == null) {
			chamado.setStatus(StatusChamadoEnum.ABERTO);
		}
		if (chamado.getModoAcionamento() == null) {
			chamado.setModoAcionamento(AcionamentoEnum.BOTAO_SOS);
		}
		aplicarEncerramento(chamado);
		repCh.save(chamado);
		return chamado;
	}

	@Operation(description = "Este é um endpoint que "
			+ "atualiza o status de um chamado SOS",
			tags = "Atualização parcial de informações")
	@PatchMapping("/{id}/status")
	public ChamadoSOS atualizarStatus(
			@PathVariable Long id,
			@RequestParam(name = "status") StatusChamadoEnum status) {

		Optional<ChamadoSOS> op = repCh.findById(id);

		if (op.isPresent()) {
			ChamadoSOS chamado = op.get();
			chamado.setStatus(status);
			aplicarEncerramento(chamado);
			repCh.save(chamado);
			return chamado;
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}

	}

	@Operation(description = "Este é um endpoint que "
			+ "atualiza um chamado SOS",
			tags = "Atualização de informações")
	@PutMapping("/{id}/atualizar")
	public ChamadoSOS atualizarChamado(
			@PathVariable Long id,
			@RequestBody ChamadoSOS chamadoAtualizado) {

		Optional<ChamadoSOS> op = repCh.findById(id);

		if (op.isPresent()) {
			ChamadoSOS chamado = op.get();
			chamado.setUsuario(carregarUsuario(chamadoAtualizado.getUsuario()));
			chamado.setResponsavel(carregarResponsavel(chamadoAtualizado.getResponsavel()));
			chamado.setCriadoEm(chamadoAtualizado.getCriadoEm() != null
					? chamadoAtualizado.getCriadoEm()
					: chamado.getCriadoEm());
			chamado.setStatus(chamadoAtualizado.getStatus() != null
					? chamadoAtualizado.getStatus()
					: chamado.getStatus());
			chamado.setModoAcionamento(chamadoAtualizado.getModoAcionamento() != null
					? chamadoAtualizado.getModoAcionamento()
					: chamado.getModoAcionamento());
			chamado.setEncerradoEm(chamadoAtualizado.getEncerradoEm());
			aplicarEncerramento(chamado);
			repCh.save(chamado);
			return chamado;
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}

	}

	@Operation(description = "Este é um endpoint que "
			+ "remove um chamado SOS",
			tags = "Remoção de informações")
	@DeleteMapping("/{id}/remover")
	public ChamadoSOS removerChamado(@PathVariable Long id) {

		Optional<ChamadoSOS> op = repCh.findById(id);

		if (op.isPresent()) {
			ChamadoSOS chamado = op.get();
			try {
				repCh.deleteById(id);
			} catch (DataIntegrityViolationException ex) {
				throw new ResponseStatusException(HttpStatus.CONFLICT);
			}
			return chamado;
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}

	}

	@Operation(description = "Este é um endpoint que "
			+ "realiza uma busca não-otimizada por status",
			tags = "Retorno de informações")
	@GetMapping("/status-nao-otimizada")
	public List<ChamadoSOS> buscaNaoOtimizadaPorStatus(
			@RequestParam(name = "status") StatusChamadoEnum status) {

		List<ChamadoSOS> todas = repCh.findAll();
		List<ChamadoSOS> retorno = new ArrayList<ChamadoSOS>();

		for (ChamadoSOS chamado : todas) {
			if (chamado.getStatus() == status) {
				retorno.add(chamado);
			}
		}

		return retorno;

	}

	@Operation(description = "Este é um endpoint que "
			+ "realiza uma busca otimizada (via HQL) "
			+ "por status",
			tags = "Retorno de informações")
	@GetMapping("/status-otimizada-1")
	public List<ChamadoSOS> buscaOtimizadaPorStatus1(
			@RequestParam(name = "status") StatusChamadoEnum status) {
		return repCh.retornarPorStatusOtimizada1(status);
	}

	@Operation(description = "Este é um endpoint que "
			+ "realiza uma busca otimizada (via SQL) "
			+ "por status",
			tags = "Retorno de informações")
	@GetMapping("/status-otimizada-2")
	public List<ChamadoProjection> buscaOtimizadaPorStatus2(
			@RequestParam(name = "status") StatusChamadoEnum status) {
		return repCh.retornarPorStatusOtimizada2(status.name());
	}

	private void aplicarEncerramento(ChamadoSOS chamado) {
		if (chamado.getStatus() == StatusChamadoEnum.ENCERRADO
				|| chamado.getStatus() == StatusChamadoEnum.CANCELADO) {
			if (chamado.getEncerradoEm() == null) {
				chamado.setEncerradoEm(Instant.now());
			}
		} else {
			chamado.setEncerradoEm(null);
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

	private Usuario carregarResponsavel(Usuario responsavel) {
		if (responsavel == null || responsavel.getId() == null) {
			return null;
		}
		return carregarUsuario(responsavel.getId());
	}

}
