package school.sptech.RabbitMq;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import school.sptech.RabbitMq.domain.OrdemServico;
import school.sptech.RabbitMq.messaging.OrdemServicoPublisher;
import school.sptech.RabbitMq.repository.OrdemServicoRepository;

@SpringBootTest(properties = "spring.datasource.url=jdbc:h2:mem:oficina_mecanica_test;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE")
@AutoConfigureMockMvc
class OrdemServicoControllerTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private OrdemServicoRepository ordemServicoRepository;

	@MockitoBean
	private OrdemServicoPublisher ordemServicoPublisher;

	@BeforeEach
	void limparBanco() {
		ordemServicoRepository.deleteAll();
	}

	@Test
	void deveCriarOrdemServicoEPublicarEvento() throws Exception {
		String body = """
				{
				  "nomeCliente": "Enzo",
				  "modeloCarro": "Honda Civic 2018",
				  "placa": "ABC1D23",
				  "descricaoProblema": "Barulho na suspensao dianteira",
				  "mecanicoResponsavel": "Toretto",
				  "valorEstimado": 450.00
				}
				""";

		mockMvc.perform(post("/ordens-servico")
						.contentType(MediaType.APPLICATION_JSON)
						.content(body))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.id").exists())
				.andExpect(jsonPath("$.status").value("RECEBIDA"));

		assertThat(ordemServicoRepository.findAll()).hasSize(1);
		verify(ordemServicoPublisher).publicarCriacao(any(OrdemServico.class));
	}
}
