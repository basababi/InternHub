package mn.internhub.demo.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import mn.internhub.demo.data.enums.Role;

import java.util.List;

public record RegisterStudentRequest(
        RegisterBaseRequest baseRequest,

        @NotBlank(message = "firstName дутуу байна") String firstName,
        @NotBlank(message = "lastName дутуу байна") String lastName)
{}


