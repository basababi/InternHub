package mn.internhub.demo.api.dto.applicationApiDto;

import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.Builder;

@Builder
public record ResponseStatusCount(
        Integer pending,
        Integer rejected,
        Integer accepted
) {
}
