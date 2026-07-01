package mn.internhub.demo.api.dto.evaluationApiDto;

import lombok.Builder;
import mn.internhub.demo.data.enums.EvaluationStatus;

@Builder
public record AccaptedStudents(
        Long studentId,
        String firstName,
        String lastName,
        EvaluationStatus status,
        Long evaluationId
) {
}
