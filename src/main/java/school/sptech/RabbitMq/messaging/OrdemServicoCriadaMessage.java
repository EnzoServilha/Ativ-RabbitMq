package school.sptech.RabbitMq.messaging;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import school.sptech.RabbitMq.domain.OrdemServico;
import school.sptech.RabbitMq.domain.StatusOrdemServico;

public record OrdemServicoCriadaMessage(
		String evento,
		Long ordemServicoId,
		String nomeCliente,
		String modeloCarro,
		String placa,
		String descricaoProblema,
		String mecanicoResponsavel,
		BigDecimal valorEstimado,
		StatusOrdemServico status,
		LocalDateTime criadaEm
) {

	public static OrdemServicoCriadaMessage from(OrdemServico ordemServico) {
		return new OrdemServicoCriadaMessage(
				"ORDEM_SERVICO_CRIADA",
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
