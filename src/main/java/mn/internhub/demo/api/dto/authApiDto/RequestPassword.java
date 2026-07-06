package mn.internhub.demo.api.dto.authApiDto;

public record RequestPassword(
        String oldPassword,
        String newPassword
) {
}
