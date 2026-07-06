package mn.internhub.demo.repository;


import mn.internhub.demo.data.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileEntityRepository extends JpaRepository<FileEntity,Long> {
}
