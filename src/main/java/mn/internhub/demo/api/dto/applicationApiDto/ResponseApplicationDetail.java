package mn.internhub.demo.api.dto.applicationApiDto;

import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Builder
public record ResponseApplicationDetail(
        Long appId,
        String firstName,
        String lastName,
        Long studentId,
        String major,
        String university,
        Integer courseYear,
        BigDecimal gpa,
        Double phone,
        String shortBio,
        List<String>skills,
        List<String> languages,
        String coverLetter,
        LocalDateTime createdAt
)
{}
