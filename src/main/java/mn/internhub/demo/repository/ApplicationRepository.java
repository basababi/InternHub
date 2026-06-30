package mn.internhub.demo.repository;

import jakarta.persistence.metamodel.SingularAttribute;
import mn.internhub.demo.data.Application;
import org.springframework.data.jpa.domain.AbstractPersistable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.io.Serializable;
import java.util.List;

public interface ApplicationRepository extends JpaRepository<Application,Long> {
    List<Application> findByStudentId(Long studentId);
}
