package mn.internhub.demo.repository;

import mn.internhub.demo.data.Evaluation;
import mn.internhub.demo.data.enums.EvaluationStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EvaluationRepository extends JpaRepository<Evaluation,Long> {
    Evaluation findByStudentId(Long studentId);
    List<Evaluation> findAllByStudentId(Long studentId);

    List<Evaluation> findAllOrganizationId(Long orgId);

    List<Evaluation> findAllByStudentIdAndStatus(Long studentId, EvaluationStatus evaluationStatus);
}
