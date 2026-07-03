package mn.internhub.demo.api.dto.studentApiDto;

import java.math.BigDecimal;
import java.util.List;

public record UpdateProfileRequest(
    String  firstName,
    String  lastName,
    String  major,
    String  university,
    Integer courseYear,
    BigDecimal gpa,
    Double  phone,
    String  shortBio,
    List<String>skills,
    List<String> languages,
    Long teacherId
) {
}