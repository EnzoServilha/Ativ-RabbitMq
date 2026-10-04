package school.sptech.RabbitMq.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import school.sptech.RabbitMq.domain.OrdemServico;

public interface OrdemServicoRepository extends JpaRepository<OrdemServico, Long> {
}
