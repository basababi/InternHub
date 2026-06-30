package mn.internhub.demo.api.dto.authApiDto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import mn.internhub.demo.data.enums.Role;

public record RegisterBaseRequest(
        @NotBlank(message = "email дутуу байна") @Email(message = "gmail чинь буруу байна дахин шалга")
        String email,
        @NotBlank(message = "password дутуу байна") @Size(min = 6, message = "Нууц үг чинь хамгийн багадаа6 оронтой байна")
        String password,
        @NotBlank(message = "Role-оо оруулаагүй байна") Role role,
        Boolean isActive
) {
}
