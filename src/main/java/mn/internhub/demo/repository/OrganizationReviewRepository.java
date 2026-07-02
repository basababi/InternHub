package mn.internhub.demo.repository;

import mn.internhub.demo.data.OrganizationReview;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrganizationReviewRepository extends JpaRepository<OrganizationReview, Long> {
    List<OrganizationReview> findAllByOrganizationId(Long organizationId);

    List<OrganizationReview> findAllByOrganizationReviewId(Long orgId);
}
