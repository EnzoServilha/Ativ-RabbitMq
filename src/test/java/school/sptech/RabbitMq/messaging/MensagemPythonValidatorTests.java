package school.sptech.RabbitMq.messaging;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

class MensagemPythonValidatorTests {

	private final MensagemPythonValidator validator = new MensagemPythonValidator(
			"python",
			"src/main/python/validar_mensagem.py"
	);

	@Test
	void deveAceitarMensagemValida() {
		assertThatCode(() -> validator.validar(mensagemValida()))
				.doesNotThrowAnyException();
	}

	@Test
	void deveRejeitarMensagemVazia() {
		assertThatThrownBy(() -> validator.validar(""))
				.isInstanceOf(IllegalArgumentException.class)
				.hasMessageContaining("Mensagem vazia");
	}

	@Test
	void deveRejeitarMensagemComDescricaoMuitoCurta() {
		String mensagem = mensagemValida()
				.replace("Barulho na suspensao dianteira", "ruim");

		assertThatThrownBy(() -> validator.validar(mensagem))
				.isInstanceOf(IllegalArgumentException.class)
				.hasMessageContaining("descricaoProblema");
	}

	private String mensagemValida() {
		return """
				{
				  "evento": "ORDEM_SERVICO_CRIADA",
				  "ordemServicoId": 1,
				  "nomeCliente": "Enzo",
				  "modeloCarro": "Honda Civic 2018",
				  "placa": "ABC1D23",
				  "descricaoProblema": "Barulho na suspensao dianteira",
				  "mecanicoResponsavel": "Toretto",
				  "valorEstimado": 450.00,
				  "status": "RECEBIDA",
				  "criadaEm": "2026-10-04T18:51:55"
				}
				""";
	}
}
