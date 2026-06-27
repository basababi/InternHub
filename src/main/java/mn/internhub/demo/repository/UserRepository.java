package mn.internhub.demo.repository;

import mn.internhub.demo.data.Student;
import mn.internhub.demo.data.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    Student findByUserId(Long id);
}
