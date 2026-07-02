package mn.internhub.demo.api.dto.authApiDto;

import jakarta.validation.constraints.NotBlank;

public record RegisterAdminRequest(
        RegisterBaseRequest baseRequest,
        @NotBlank(message = "adminName дутуу байна") String adminName
) {
}
