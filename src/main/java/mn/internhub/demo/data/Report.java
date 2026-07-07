package mn.internhub.demo.data;

import jakarta.persistence.*;
import lombok.*;
import mn.internhub.demo.data.enums.Status;

import java.time.LocalDate;

@Data
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "reports")
@Builder
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    private Long reportId;

    private Long teacherId;
    private Long studentId;
    private String title;
    private Status status;
    private String description;
    private String teacherComment;
    private LocalDate createdAt;
    private LocalDate approvedAt;

}
