package mn.internhub.demo.api.dto.studentApiDto;

import lombok.Builder;

import java.math.BigDecimal;
import java.util.List;

@Builder
public record ResponseStudentPro(
        String firstName,
        String lastName,
        String major,
        String university,
        Integer courseYear,
        BigDecimal gpa,
        Double phone,
        String shortBio,
        List<String> skills,
        List<String> language,
        String email,
        String teacherFirstName,
        Double teacherPhone
){
}
