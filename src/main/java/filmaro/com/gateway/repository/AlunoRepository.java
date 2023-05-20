package filmaro.com.gateway.repository;

import filmaro.com.gateway.repository.entity.AlunoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlunoRepository extends JpaRepository<AlunoEntity, String> {
}
