package school.sptech.RabbitMq.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "ordens_servico")
public class OrdemServico {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, length = 80)
	private String nomeCliente;

	@Column(nullable = false, length = 80)
	private String modeloCarro;

	@Column(nullable = false, length = 8)
	private String placa;

	@Column(nullable = false, length = 300)
	private String descricaoProblema;

	@Column(nullable = false, length = 80)
	private String mecanicoResponsavel;

	@Column(precision = 10, scale = 2)
	private BigDecimal valorEstimado;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 30)
	private StatusOrdemServico status;

	@Column(nullable = false, updatable = false)
	private LocalDateTime criadaEm;

	protected OrdemServico() {
	}

	public OrdemServico(
			String nomeCliente,
			String modeloCarro,
			String placa,
			String descricaoProblema,
			String mecanicoResponsavel,
			BigDecimal valorEstimado
	) {
		this.nomeCliente = nomeCliente;
		this.modeloCarro = modeloCarro;
		this.placa = placa;
		this.descricaoProblema = descricaoProblema;
		this.mecanicoResponsavel = mecanicoResponsavel;
		this.valorEstimado = valorEstimado;
		this.status = StatusOrdemServico.RECEBIDA;
	}

	@PrePersist
	void aoCriar() {
		if (status == null) {
			status = StatusOrdemServico.RECEBIDA;
		}
		if (criadaEm == null) {
			criadaEm = LocalDateTime.now();
		}
	}

	public Long getId() {
		return id;
	}

	public String getNomeCliente() {
		return nomeCliente;
	}

	public String getModeloCarro() {
		return modeloCarro;
	}

	public String getPlaca() {
		return placa;
	}

	public String getDescricaoProblema() {
		return descricaoProblema;
	}

	public String getMecanicoResponsavel() {
		return mecanicoResponsavel;
	}

	public BigDecimal getValorEstimado() {
		return valorEstimado;
	}

	public StatusOrdemServico getStatus() {
		return status;
	}

	public LocalDateTime getCriadaEm() {
		return criadaEm;
	}
}
