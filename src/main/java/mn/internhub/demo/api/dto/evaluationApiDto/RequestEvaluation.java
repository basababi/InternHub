package mn.internhub.demo.api.dto.evaluationApiDto;

import java.time.LocalDate;

public record RequestEvaluation(
        Long studentId,
        Integer score,
        String comment,
        LocalDate createdAt
) {
}
