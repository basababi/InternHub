package mn.internhub.demo.data;

import jakarta.persistence.*;
import lombok.*;
import mn.internhub.demo.data.enums.PaymentStatus;
import mn.internhub.demo.data.enums.Status;

import java.time.LocalDateTime;

@Entity
@Data
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "applicaitons")
public class Application {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    private Long applicationId;
    @Column(nullable = false, unique = true)
    private Long studentId;
    @Column(nullable = false, unique = true)
    private Long internshipPostId;
    private Status status;
    private String coverLetter;
    private String rejectionNote;
    private PaymentStatus paymentStatus;
    private LocalDateTime submittedAt;
    private LocalDateTime decidedAt;
    private Long decidedByUserId;
}
