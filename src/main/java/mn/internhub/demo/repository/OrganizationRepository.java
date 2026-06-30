package mn.internhub.demo.repository;

import mn.internhub.demo.data.Organizations;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrganizationRepository extends JpaRepository<Organizations,Long> {
    boolean existsByUserId(Long id);

    Organizations findByUserId(Long userId);
}
