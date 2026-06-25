package mn.internhub.demo.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import mn.internhub.demo.data.enums.Role;

import java.util.List;

public record RegisterStudentRequest(
        @NotBlank RegisterBaseRequest baseRequest,

        @NotBlank String firstName,
        @NotBlank String lastName,
        String major,
        String university,
        Integer courseYear,
        Integer gpa,
        @Size(min = 8) Double phone,
        String shortBio,
        List<String> skills,
        List<String> languages

) {
}


