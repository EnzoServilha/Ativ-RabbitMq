package school.sptech.RabbitMq.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {

	@Bean
	public Queue ordemServicoCriadaQueue(@Value("${app.rabbitmq.queue}") String queue) {
		return QueueBuilder.durable(queue).build();
	}

	@Bean
	public DirectExchange oficinaExchange(@Value("${app.rabbitmq.exchange}") String exchange) {
		return new DirectExchange(exchange);
	}

	@Bean
	public Binding ordemServicoCriadaBinding(
			Queue ordemServicoCriadaQueue,
			DirectExchange oficinaExchange,
			@Value("${app.rabbitmq.routing-key}") String routingKey
	) {
		return BindingBuilder.bind(ordemServicoCriadaQueue).to(oficinaExchange).with(routingKey);
	}
}
