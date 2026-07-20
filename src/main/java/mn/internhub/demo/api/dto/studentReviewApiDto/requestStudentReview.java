package mn.internhub.demo.api.dto.studentReviewApiDto;

import java.time.LocalDate;

public record requestStudentReview(
        Long studentId,
        Float rate,
        String Comment
) {

}
