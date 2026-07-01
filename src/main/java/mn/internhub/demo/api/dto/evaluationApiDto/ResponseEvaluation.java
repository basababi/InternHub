package mn.internhub.demo.api.dto.evaluationApiDto;

import lombok.Builder;

@Builder
public record ResponseEvaluation(
        Long evaluationId,
        String organizationName,
        String InternshipPostTitle,
        Integer score
) {
}
