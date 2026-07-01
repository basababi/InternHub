package mn.internhub.demo.api.dto.postingApiDto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record RequestUpdatePost(
        String description,
        List<String> requiredMajors,
        BigDecimal minGpa,
        List<String> requiredSkills,
        Integer salaryMin,
        Integer salaryMax,
        Integer vacancyCount,
        LocalDateTime deadline
) {
}
