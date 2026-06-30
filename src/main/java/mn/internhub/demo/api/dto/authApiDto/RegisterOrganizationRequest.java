package mn.internhub.demo.api.dto.authApiDto;

import jakarta.validation.constraints.NotBlank;

public record RegisterOrganizationRequest(
        RegisterBaseRequest baseRequest,

        @NotBlank(message = "oragnizationName дутуу байна") String organizationName,
        @NotBlank(message = "industry дутуу байна") String industry
) {
}
