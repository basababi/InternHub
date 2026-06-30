package mn.internhub.demo.api.dto.organizationApiDto;

public record RequestOwnprofileUpdate(
        String organizationName,
        String industry,
        String address,
        String city,
        String site,
        String logoUrl,
        String description
) {
}
