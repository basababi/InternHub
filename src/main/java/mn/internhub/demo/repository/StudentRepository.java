package mn.internhub.demo.repository;

import mn.internhub.demo.data.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student,Long> {
    Student findByUserId(Long id);
    boolean existsByUserId(Long userId);

    List<Student> findAllByTeacherId(Long teacherId);
}
