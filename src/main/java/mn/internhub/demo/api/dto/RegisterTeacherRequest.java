package mn.internhub.demo.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import mn.internhub.demo.data.enums.Role;

public record RegisterTeacherRequest(
        @NotBlank RegisterBaseRequest baseRequest,

        @NotBlank String firstName,
        @NotBlank String lastName,
        @Size(min= 8) Double phone
) {
}
