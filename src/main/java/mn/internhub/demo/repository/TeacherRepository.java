package mn.internhub.demo.repository;

import mn.internhub.demo.data.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeacherRepository extends JpaRepository<Teacher,Long> {
    boolean existsByUserId(Long id);
    Teacher findByUserId(Long userId);
}
