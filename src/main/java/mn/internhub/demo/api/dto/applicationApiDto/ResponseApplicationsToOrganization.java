package mn.internhub.demo.api.dto.applicationApiDto;

import lombok.Builder;
import mn.internhub.demo.data.enums.Status;

import java.time.LocalDateTime;
import java.util.List;
@Builder
public record ResponseApplicationsToOrganization(
        Long internshipId,
        Long organizationID,
        String title,
        String description,
        LocalDateTime createdAt,
        List<ResponseApplication> responseApplications
) {
}
