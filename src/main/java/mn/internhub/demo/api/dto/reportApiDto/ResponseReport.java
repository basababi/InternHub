package mn.internhub.demo.api.dto.reportApiDto;

import jakarta.persistence.*;
import lombok.*;
import mn.internhub.demo.data.enums.ContentTypes;
import mn.internhub.demo.data.enums.Status;

import java.time.LocalDate;

@Builder
public record ResponseReport(
        Long reportId,
        Long teacherId,
        Long studentId,
        String title,
        Status status,
        String description,
        String teacherComment,
        LocalDate createdAt,
        LocalDate approvedAt,
        Long fileId,
        Long userId,
        String fileName,
        String fileType,
        byte[] data,
        ContentTypes contentTypes
) {


}

