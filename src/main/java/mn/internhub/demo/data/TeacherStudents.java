package mn.internhub.demo.data;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "teacherStudents")
public class TeacherStudents {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    private Long relationId;
    private Long teacherId;
    private Long studentId;
    private LocalDate createdAt;
}
