package mn.internhub.demo.data.enums;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;

public enum Role {
    STUDENT,
    COMPANY,
    TEACHER,
    ADMIN
}
