package school.sptech.RabbitMq;

import org.springframework.boot.SpringApplication;

public class TestRabbitMqApplication {

	public static void main(String[] args) {
		SpringApplication.from(RabbitMqApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
