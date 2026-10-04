package school.sptech.RabbitMq.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;

public record OrdemServicoRequest(
		@NotBlank
		@Size(max = 80)
		String nomeCliente,

		@NotBlank
		@Size(max = 80)
		String modeloCarro,

		@NotBlank
		@Size(max = 8)
		String placa,

		@NotBlank
		@Size(max = 300)
		String descricaoProblema,

		@NotBlank
		@Size(max = 80)
		String mecanicoResponsavel,

		@PositiveOrZero
		BigDecimal valorEstimado
) {
}
