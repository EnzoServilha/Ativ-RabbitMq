package school.sptech.RabbitMq.messaging;

import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import school.sptech.RabbitMq.domain.OrdemServico;
import tools.jackson.core.JacksonException;
import tools.jackson.databind.ObjectMapper;

@Component
public class OrdemServicoPublisher {

	private final RabbitTemplate rabbitTemplate;
	private final ObjectMapper objectMapper;
	private final MensagemPythonValidator mensagemPythonValidator;
	private final String exchange;
	private final String routingKey;

	public OrdemServicoPublisher(
			RabbitTemplate rabbitTemplate,
			ObjectMapper objectMapper,
			MensagemPythonValidator mensagemPythonValidator,
			@Value("${app.rabbitmq.exchange}") String exchange,
			@Value("${app.rabbitmq.routing-key}") String routingKey
	) {
		this.rabbitTemplate = rabbitTemplate;
		this.objectMapper = objectMapper;
		this.mensagemPythonValidator = mensagemPythonValidator;
		this.exchange = exchange;
		this.routingKey = routingKey;
	}

	public void publicarCriacao(OrdemServico ordemServico) {
		try {
			String payload = objectMapper.writeValueAsString(OrdemServicoCriadaMessage.from(ordemServico));
			mensagemPythonValidator.validar(payload);
			rabbitTemplate.convertAndSend(exchange, routingKey, payload, message -> {
				message.getMessageProperties().setContentType(MessageProperties.CONTENT_TYPE_JSON);
				return message;
			});
		} catch (JacksonException ex) {
			throw new IllegalStateException("Nao foi possivel serializar a ordem de servico para o RabbitMQ.", ex);
		}
	}
}
