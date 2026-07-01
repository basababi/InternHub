package mn.internhub.demo.api.dto.evaluationApiDto;

import java.time.LocalDate;

public record RequestEvaluation(
        Long studentId,
        Long applicationId,
        Integer score,
        String comment,
        LocalDate createdAt
) {
}
