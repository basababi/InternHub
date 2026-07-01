package mn.internhub.demo.service;

import mn.internhub.demo.data.PostViews;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostViewsRepository extends JpaRepository<PostViews,Long> {
    PostViews findByInternshipPostId(Long internshipPostId);
}
