package mn.internhub.demo.api.dto.teacherApiDto;

public record RequestProfile(
        String firstName,
        String lastName,
        Double phone,
        String major,
        String bio
) {
}
