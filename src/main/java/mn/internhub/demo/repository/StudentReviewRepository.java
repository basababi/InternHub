package mn.internhub.demo.repository;

import mn.internhub.demo.data.StudentReview;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentReviewRepository extends JpaRepository<StudentReview, Long> {
    List<StudentReview> findAllByStudentId(Long studentId);
}
