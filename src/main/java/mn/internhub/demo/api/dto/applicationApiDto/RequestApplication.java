package mn.internhub.demo.api.dto.applicationApiDto;

import jakarta.validation.constraints.NotBlank;

public record RequestApplication(
        @NotBlank
        String coverLetter
) {
}
