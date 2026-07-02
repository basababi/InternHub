package mn.internhub.demo.repository;

import mn.internhub.demo.data.InternshipPost;
import org.springframework.data.domain.Limit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InternshipPostRepository extends JpaRepository<InternshipPost,Long> {
    List<InternshipPost> findAllByOrganizationId(Long organizationId);
    Integer countAll();
}
