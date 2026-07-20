package mn.internhub.demo.api.dto.studentReviewApiDto;

import java.time.LocalDate;

public record requestStudentReview(
        Long userId,
        Float rate,
        String Comment,
        LocalDate createdAt
) {

}
