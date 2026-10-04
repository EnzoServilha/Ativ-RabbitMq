package school.sptech.RabbitMq;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Import(TestcontainersConfiguration.class)
@SpringBootTest(properties = "spring.datasource.url=jdbc:h2:mem:oficina_mecanica_test;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE")
class RabbitMqApplicationTests {

	@Test
	void contextLoads() {
	}

}
