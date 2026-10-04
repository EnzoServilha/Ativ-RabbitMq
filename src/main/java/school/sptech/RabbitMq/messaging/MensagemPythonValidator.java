package school.sptech.RabbitMq.messaging;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MensagemPythonValidator {

	private static final Duration TIMEOUT = Duration.ofSeconds(5);

	private final String pythonCommand;
	private final String validatorScript;

	public MensagemPythonValidator(
			@Value("${app.python.command:python}") String pythonCommand,
			@Value("${app.python.validator-script:src/main/python/validar_mensagem.py}") String validatorScript
	) {
		this.pythonCommand = pythonCommand;
		this.validatorScript = validatorScript;
	}

	public void validar(String payload) {
		if (payload == null || payload.isBlank()) {
			throw new IllegalArgumentException("Mensagem vazia nao pode ser enviada ao RabbitMQ.");
		}

		ProcessBuilder processBuilder = new ProcessBuilder(pythonCommand, validatorScript);
		processBuilder.redirectErrorStream(true);

		try {
			Process process = processBuilder.start();
			try (var writer = process.outputWriter(StandardCharsets.UTF_8)) {
				writer.write(payload);
			}

			boolean finished = process.waitFor(TIMEOUT.toSeconds(), TimeUnit.SECONDS);
			if (!finished) {
				process.destroyForcibly();
				throw new IllegalStateException("Tempo limite excedido ao validar mensagem com Python.");
			}

			String output = new String(process.getInputStream().readAllBytes(), StandardCharsets.UTF_8).trim();
			if (process.exitValue() != 0) {
				throw new IllegalArgumentException("Mensagem rejeitada pelo validador Python: " + output);
			}
		} catch (IOException ex) {
			throw new IllegalStateException("Nao foi possivel executar o validador Python.", ex);
		} catch (InterruptedException ex) {
			Thread.currentThread().interrupt();
			throw new IllegalStateException("Validacao da mensagem com Python foi interrompida.", ex);
		}
	}
}
