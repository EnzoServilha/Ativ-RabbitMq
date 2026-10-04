package school.sptech.RabbitMq.service;

import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import school.sptech.RabbitMq.domain.OrdemServico;
import school.sptech.RabbitMq.dto.OrdemServicoRequest;
import school.sptech.RabbitMq.dto.OrdemServicoResponse;
import school.sptech.RabbitMq.messaging.OrdemServicoPublisher;
import school.sptech.RabbitMq.repository.OrdemServicoRepository;

@Service
public class OrdemServicoService {

	private final OrdemServicoRepository ordemServicoRepository;
	private final OrdemServicoPublisher ordemServicoPublisher;

	public OrdemServicoService(
			OrdemServicoRepository ordemServicoRepository,
			OrdemServicoPublisher ordemServicoPublisher
	) {
		this.ordemServicoRepository = ordemServicoRepository;
		this.ordemServicoPublisher = ordemServicoPublisher;
	}

	@Transactional
	public OrdemServicoResponse criar(OrdemServicoRequest request) {
		OrdemServico ordemServico = new OrdemServico(
				request.nomeCliente(),
				request.modeloCarro(),
				request.placa(),
				request.descricaoProblema(),
				request.mecanicoResponsavel(),
				request.valorEstimado()
		);

		OrdemServico ordemServicoSalva = ordemServicoRepository.save(ordemServico);
		ordemServicoPublisher.publicarCriacao(ordemServicoSalva);

		return OrdemServicoResponse.from(ordemServicoSalva);
	}

	@Transactional(readOnly = true)
	public List<OrdemServicoResponse> listar() {
		return ordemServicoRepository.findAll(Sort.by(Sort.Direction.DESC, "criadaEm"))
				.stream()
				.map(OrdemServicoResponse::from)
				.toList();
	}

	@Transactional(readOnly = true)
	public OrdemServicoResponse buscarPorId(Long id) {
		return ordemServicoRepository.findById(id)
				.map(OrdemServicoResponse::from)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Ordem de servico nao encontrada."));
	}
}
