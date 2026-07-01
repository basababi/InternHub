package mn.internhub.demo.api.dto.postingApiDto;

import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.Builder;

import java.util.List;

@Builder
public record ResponsePubPosts(
        Long internshipPostId,
        Long organizationId,
        String organizationName,
        String industry,
        String title,
        String city,
        Integer salaryMin,
        Integer salaryMax,
        List<String> requiredMajor,g
        Integer viewCount

) {
}
