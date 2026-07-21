package mn.internhub.demo.repository;


import mn.internhub.demo.data.FileEntity;
import mn.internhub.demo.data.enums.ContentTypes;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FileEntityRepository extends JpaRepository<FileEntity,Long> {
    Optional <FileEntity> findByUserIdAndContentTypes(Long userId, ContentTypes contentTypes);

    FileEntity findByUserId(Long userId);
    FileEntity findByReportIdAndContentTypes(Long reportId, ContentTypes contentTypes);


    void deleteByReportId(Long reportId);
}
