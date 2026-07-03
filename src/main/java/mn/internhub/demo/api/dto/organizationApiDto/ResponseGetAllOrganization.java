package mn.internhub.demo.api.dto.organizationApiDto;

import lombok.Builder;
import mn.internhub.demo.data.enums.UserStatus;

@Builder
public record ResponseGetAllOrganization(
        Long organizationId,
        String organizationName,
        String address,
        String city,
        String logoUrl,
        String description,
        String industry,
        UserStatus status
) {
}
