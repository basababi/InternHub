package mn.internhub.demo.api.dto.postingApiDto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record RequestCreatePost(
        String title,
        String description,
        List<String> requiredMajors,
        BigDecimal minGpa,
        List<String> requiredSkills,
        Integer salaryMin,
        Integer salaryMax,
        boolean isSalaryUnspecified,
        Integer vacancyCount,
        LocalDateTime deadline
) {
}
