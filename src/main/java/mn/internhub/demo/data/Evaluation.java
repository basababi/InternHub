package mn.internhub.demo.data;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Data
@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "evaluations")

public class Evaluation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false,unique = true)
    private Long evaluationId;

    private Long organizationId;
    private Long studentId;
    private Long applicationId;
    private Integer score;
    private String comment;
    private LocalDate createdAt;
    private LocalDate updatedAt;
}
