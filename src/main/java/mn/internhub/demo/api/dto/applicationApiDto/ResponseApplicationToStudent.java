package mn.internhub.demo.api.dto.applicationApiDto;

import lombok.Builder;
import mn.internhub.demo.data.enums.Status;

import java.time.LocalDateTime;

@Builder
public record ResponseApplicationToStudent(
        String postTitle,
        LocalDateTime submittedAt,
        String description,
        Status status,
        String company
) {
}
