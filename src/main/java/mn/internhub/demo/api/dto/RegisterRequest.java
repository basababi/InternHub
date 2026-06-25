package mn.internhub.demo.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import mn.internhub.demo.data.enums.Role;
import org.aspectj.weaver.ast.Not;

import java.net.URL;

public record RegisterRequest(

        @NotBlank @Email String email,
        @NotBlank @Size(min = 6, message = "Password must be at least 6 characters") String password,
        @NotBlank Role role
) {
}
