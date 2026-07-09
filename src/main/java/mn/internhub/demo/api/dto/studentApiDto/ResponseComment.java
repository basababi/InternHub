package mn.internhub.demo.api.dto.studentApiDto;

import lombok.Builder;
import mn.internhub.demo.data.enums.Role;

@Builder
public record ResponseComment(
        Role role,
        String name,
        String comment,
        Integer score

) {
}
