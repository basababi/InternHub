package mn.internhub.demo.repository;

import mn.internhub.demo.data.InternshipPost;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostingApiRepository extends JpaRepository<InternshipPost,Long> {
}
