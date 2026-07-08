package mn.internhub.demo.api.dto.teacherApiDto;

import jakarta.validation.constraints.NotBlank;

public record RequestSaveOwnStudent(
        @NotBlank String firstName,
        @NotBlank String email
) {
}
