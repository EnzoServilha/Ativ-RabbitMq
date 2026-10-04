package school.sptech.RabbitMq.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import school.sptech.RabbitMq.domain.OrdemServico;
import school.sptech.RabbitMq.domain.StatusOrdemServico;

public record OrdemServicoResponse(
		Long id,
		String nomeCliente,
		String modeloCarro,
		String placa,
		String descricaoProblema,
		String mecanicoResponsavel,
		BigDecimal valorEstimado,
		StatusOrdemServico status,
		LocalDateTime criadaEm
) {

	public static OrdemServicoResponse from(OrdemServico ordemServico) {
		return new OrdemServicoResponse(
				ordemServico.getId(),
				ordemServico.getNomeCliente(),
				ordemServico.getModeloCarro(),
				ordemServico.getPlaca(),
				ordemServico.getDescricaoProblema(),
				ordemServico.getMecanicoResponsavel(),
				ordemServico.getValorEstimado(),
				ordemServico.getStatus(),
				ordemServico.getCriadaEm()
		);
	}
}
