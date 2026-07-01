package mn.internhub.demo.repository;

import mn.internhub.demo.data.Evaluation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EvaluationRepository extends JpaRepository<Evaluation,Long> {
}
