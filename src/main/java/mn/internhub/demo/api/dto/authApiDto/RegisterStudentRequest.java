package mn.internhub.demo.api.dto.authApiDto;

import jakarta.validation.constraints.NotBlank;

public record RegisterStudentRequest(
        RegisterBaseRequest baseRequest,

        @NotBlank(message = "firstName дутуу байна") String firstName,
        @NotBlank(message = "lastName дутуу байна") String lastName)
{}


