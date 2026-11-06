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

import br.com.fatecmaua.quickhelp.model.ChamadoSOS;
import br.com.fatecmaua.quickhelp.model.LocalizacaoChamado;
import br.com.fatecmaua.quickhelp.repository.ChamadoSOSRepository;
import br.com.fatecmaua.quickhelp.repository.LocalizacaoChamadoRepository;
import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/localizacoes")
public class LocalizacaoChamadoController {

	@Autowired
	private LocalizacaoChamadoRepository repL;

	@Autowired
	private ChamadoSOSRepository repCh;

	@Operation(description = "Este é um endpoint que "
			+ "retorna todas as localizações",
			tags = "Retorno de informações")
	@GetMapping("/todas")
	public List<LocalizacaoChamado> retornarTodas() {
		return repL.findAll();
	}

	@Operation(description = "Este é um endpoint que "
			+ "retorna uma localização por ID",
			tags = "Retorno de informações")
	@GetMapping("/{id}")
	public LocalizacaoChamado retornarPorId(@PathVariable Long id) {

		Optional<LocalizacaoChamado> op = repL.findById(id);

		if (op.isPresent()) {
			return op.get();
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}

	}

	@Operation(description = "Este é um endpoint que "
			+ "retorna as localizações de um chamado, da mais recente para a mais antiga",
			tags = "Retorno de informações")
	@GetMapping("/chamado/{chamadoId}")
	public List<LocalizacaoChamado> retornarPorChamado(@PathVariable Long chamadoId) {
		carregarChamado(chamadoId);
		return repL.retornarPorChamado(chamadoId);
	}

	@Operation(description = "Este é um endpoint que "
			+ "retorna a última localização registrada de um chamado",
			tags = "Retorno de informações")
	@GetMapping("/chamado/{chamadoId}/ultima")
	public LocalizacaoChamado retornarUltima(@PathVariable Long chamadoId) {
		carregarChamado(chamadoId);
		List<LocalizacaoChamado> localizacoes = repL.retornarPorChamado(chamadoId);
		if (localizacoes.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
		return localizacoes.get(0);
	}

	@Operation(description = "Este é um endpoint que "
			+ "insere uma nova localização de chamado",
			tags = "Inserção de informações")
	@PostMapping("/nova")
	public LocalizacaoChamado inserirLocalizacao(@RequestBody LocalizacaoChamado localizacao) {
		if (localizacao.getLatitude() == null || localizacao.getLongitude() == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		}
		localizacao.setId(null);
		localizacao.setChamado(carregarChamado(localizacao.getChamado()));
		if (localizacao.getRegistradaEm() == null) {
			localizacao.setRegistradaEm(Instant.now());
		}
		repL.save(localizacao);
		return localizacao;
	}

	@Operation(description = "Este é um endpoint que "
			+ "atualiza uma localização de chamado",
			tags = "Atualização de informações")
	@PutMapping("/{id}/atualizar")
	public LocalizacaoChamado atualizarLocalizacao(
			@PathVariable Long id,
			@RequestBody LocalizacaoChamado localizacaoAtualizada) {

		Optional<LocalizacaoChamado> op = repL.findById(id);

		if (op.isPresent()) {
			if (localizacaoAtualizada.getLatitude() == null || localizacaoAtualizada.getLongitude() == null) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
			}
			LocalizacaoChamado localizacao = op.get();
			localizacao.setChamado(carregarChamado(localizacaoAtualizada.getChamado()));
			localizacao.setLatitude(localizacaoAtualizada.getLatitude());
			localizacao.setLongitude(localizacaoAtualizada.getLongitude());
			localizacao.setPrecisaoMetros(localizacaoAtualizada.getPrecisaoMetros());
			if (localizacaoAtualizada.getRegistradaEm() != null) {
				localizacao.setRegistradaEm(localizacaoAtualizada.getRegistradaEm());
			}
			repL.save(localizacao);
			return localizacao;
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}

	}

	@Operation(description = "Este é um endpoint que "
			+ "remove uma localização de chamado",
			tags = "Remoção de informações")
	@DeleteMapping("/{id}/remover")
	public LocalizacaoChamado removerLocalizacao(@PathVariable Long id) {

		Optional<LocalizacaoChamado> op = repL.findById(id);

		if (op.isPresent()) {
			LocalizacaoChamado localizacao = op.get();
			repL.deleteById(id);
			return localizacao;
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}

	}

	private ChamadoSOS carregarChamado(ChamadoSOS chamado) {
		if (chamado == null || chamado.getId() == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		}
		return carregarChamado(chamado.getId());
	}

	private ChamadoSOS carregarChamado(Long id) {
		Optional<ChamadoSOS> op = repCh.findById(id);
		if (op.isPresent()) {
			return op.get();
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
	}

}
