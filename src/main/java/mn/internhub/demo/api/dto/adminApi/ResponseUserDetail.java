package mn.internhub.demo.api.dto.adminApi;

import lombok.Builder;

import java.math.BigDecimal;
import java.util.List;

@Builder
public record ResponseUserDetail(
        // Student
        Long studentId,
        String firstName,
        String lastName,
        String major,
        String university,
        Integer courseYear,
        BigDecimal gpa,
        List<String> skills,
        List<String> languages,

        // Teacher
        Long teacherId,

        // Organization
        Long organizationId,
        String organizationName,
        String industry,
        String address,
        String city,
        String site,
        String logoUrl,
        String description,
        Boolean isVerified,

        // Common
        Double phone

) {
}
