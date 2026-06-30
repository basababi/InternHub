package mn.internhub.demo.api.dto;

import jakarta.validation.constraints.NotBlank;

public record RequestApplication(
        @NotBlank
        String coverLetter,
        @NotBlank
        Long studentId,
        @NotBlank
        Long internshipPostId
) {
}
