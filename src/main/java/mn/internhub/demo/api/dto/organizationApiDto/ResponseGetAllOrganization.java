package mn.internhub.demo.api.dto.organizationApiDto;

import lombok.Builder;

@Builder
public record ResponseGetAllOrganization(
        String organizationName,
        String address,
        String city,
        String logoUrl,
        String description,
        String industry
) {
}
