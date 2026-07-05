package mn.internhub.demo.api.dto.evaluationApiDto;

import lombok.Builder;
import mn.internhub.demo.data.enums.EvaluationStatus;
import mn.internhub.demo.data.enums.PostStatus;
import mn.internhub.demo.data.enums.Status;

import java.util.List;

@Builder
public record ResponseGetAppEva(
        Long internshipPostId,
        Long applicationId,
        String title,
        Integer vacancyCount,
        List<AccaptedStudents> students
) {
}
