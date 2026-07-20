package mn.internhub.demo.repository;

import mn.internhub.demo.data.StudentReview;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentReviewRepository extends JpaRepository<StudentReview, Long> {
}
