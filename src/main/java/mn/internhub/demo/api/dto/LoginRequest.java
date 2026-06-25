package mn.internhub.demo.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
        @NotBlank(message = "email дутуу байна") @Email(message = "email чинь буруу байна")
        String email,
        @NotBlank(message = "password дутуу байна") String password
) {
}
