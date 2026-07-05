package mn.internhub.demo.api.dto.evaluationApiDto;

import java.time.LocalDate;

public record RequestEvaluation(
        Integer score,
        String comment
) {
}
