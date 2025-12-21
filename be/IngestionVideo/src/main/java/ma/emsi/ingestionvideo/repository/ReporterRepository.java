package ma.emsi.ingestionvideo.repository;

import ma.emsi.ingestionvideo.entity.Reporter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReporterRepository extends JpaRepository<Reporter, Long> {

    boolean existsByPhone(String phone);
    boolean existsByEmail(String email);
    Optional<Reporter> findByEmail(String email);
}
