package mn.internhub.demo.repository;

import mn.internhub.demo.data.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin, Long> {
    boolean existsByUserId(long id);
}
