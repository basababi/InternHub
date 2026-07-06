package mn.internhub.demo.api.dto.authApiDto;

import lombok.Builder;

@Builder
public record AuthResponse(String token) {
}
