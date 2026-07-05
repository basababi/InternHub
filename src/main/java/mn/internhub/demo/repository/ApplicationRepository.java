package mn.internhub.demo.repository;

import jakarta.persistence.metamodel.SingularAttribute;
import mn.internhub.demo.data.Application;
import mn.internhub.demo.data.InternshipPost;
import mn.internhub.demo.data.enums.Status;
import org.springframework.data.jpa.domain.AbstractPersistable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public interface ApplicationRepository extends JpaRepository<Application,Long> {
    List<Application> findAllByStudentId(Long studentId);

    List<Application> findAllByInternshipPostId(Long id);

    List<Application> findAllByInternshipPostIdAndStatus(Long internshipPostId, Status status);

    Application findByInternshipPostId(Long evaluationId);

    Application findByStudentId(Long studentId);
}
