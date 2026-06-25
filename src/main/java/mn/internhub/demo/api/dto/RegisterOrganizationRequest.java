package mn.internhub.demo.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import mn.internhub.demo.data.enums.Role;
import org.hibernate.validator.constraints.URL;

public record RegisterOrganizationRequest(
        RegisterBaseRequest baseRequest,

        @NotBlank(message = "oragnizationName дутуу байна") String organizationName,
        @NotBlank(message = "industry дутуу байна") String industry
) {
}
