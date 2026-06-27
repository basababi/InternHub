package mn.internhub.demo.api.dto;

import java.util.List;

public record UpdateProfileRequest(
    String  firstName,
    String  lastName,
    String  major,
    String  university,
    Integer courseYear,
    Integer gpa,
    Double  phone,
    String  shortBio,
    List<String>skills,
    List<String> languages
) {
}