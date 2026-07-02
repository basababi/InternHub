package mn.internhub.demo.repository;

import mn.internhub.demo.data.metaData.Anonymous;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnonymousRepository extends JpaRepository<Anonymous,Long> {
}
