package mn.internhub.demo.api.dto.reportApiDto;

import mn.internhub.demo.data.enums.Status;

import java.time.LocalDate;

public record RequestCreateReport(
        String title,
        Status status,
        Long teacherId
) {
}
