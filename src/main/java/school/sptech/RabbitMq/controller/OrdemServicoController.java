package school.sptech.RabbitMq.controller;

import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import school.sptech.RabbitMq.dto.OrdemServicoRequest;
import school.sptech.RabbitMq.dto.OrdemServicoResponse;
import school.sptech.RabbitMq.service.OrdemServicoService;

@RestController
@RequestMapping("/ordens-servico")
public class OrdemServicoController {

	private final OrdemServicoService ordemServicoService;

	public OrdemServicoController(OrdemServicoService ordemServicoService) {
		this.ordemServicoService = ordemServicoService;
	}

	@PostMapping
	public ResponseEntity<OrdemServicoResponse> criar(@Valid @RequestBody OrdemServicoRequest request) {
		OrdemServicoResponse response = ordemServicoService.criar(request);
		return ResponseEntity.created(URI.create("/ordens-servico/" + response.id())).body(response);
	}

	@GetMapping
	public List<OrdemServicoResponse> listar() {
		return ordemServicoService.listar();
	}

	@GetMapping("/{id}")
	public OrdemServicoResponse buscarPorId(@PathVariable Long id) {
		return ordemServicoService.buscarPorId(id);
	}
}
