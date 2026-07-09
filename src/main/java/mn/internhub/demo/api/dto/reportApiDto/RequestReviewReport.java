package mn.internhub.demo.api.dto.reportApiDto;

import mn.internhub.demo.data.enums.Status;

public record RequestReviewReport(
        String teacherComment,
        Integer score,
        Status status
) {
}
