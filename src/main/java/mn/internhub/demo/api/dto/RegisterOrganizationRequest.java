package mn.internhub.demo.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import mn.internhub.demo.data.enums.Role;
import org.hibernate.validator.constraints.URL;

public record RegisterOrganizationRequest(
        @NotBlank RegisterBaseRequest baseRequest,

        @NotBlank String organizationName,
        @NotBlank String industry,
        String address,
        String city,
        @URL String site,
        @URL String logoUrl,
        String description,
        @NotBlank Boolean isVerified
) {
}
