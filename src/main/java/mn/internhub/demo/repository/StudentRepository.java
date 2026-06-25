package mn.internhub.demo.repository;

import mn.internhub.demo.data.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student,Long> {
}
