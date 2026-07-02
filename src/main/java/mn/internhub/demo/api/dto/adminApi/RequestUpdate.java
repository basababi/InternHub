package mn.internhub.demo.api.dto.adminApi;

import jakarta.validation.constraints.NotBlank;
import mn.internhub.demo.data.enums.Role;
import mn.internhub.demo.data.enums.UserStatus;

public record RequestUpdate(
        @NotBlank UserStatus status
) {
}
