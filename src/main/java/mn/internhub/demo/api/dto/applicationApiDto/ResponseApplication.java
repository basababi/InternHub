package mn.internhub.demo.api.dto.applicationApiDto;

import lombok.Builder;
import mn.internhub.demo.data.enums.Status;

import java.math.BigDecimal;
// энэ дангаараа ашиглахгүй дээ
@Builder
public record ResponseApplication(
        Long userId,
        String firstName,
        String lastName,
        String major,
        String university,
        Integer courseYear,
        BigDecimal gpa,
        Status status,
        String coverLetter
) {
}
