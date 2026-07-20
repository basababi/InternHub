package mn.internhub.demo.api.dto.studentReviewApiDto;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public record ResponseStudentReview(
        String name,
        Float rate,
        String Comment,
        String createdAt
) {
}
