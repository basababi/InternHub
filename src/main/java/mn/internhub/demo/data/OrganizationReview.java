package mn.internhub.demo.data;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "organizationReviews")
public class OrganizationReview {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false,unique = true)
    private Long organizationReviewId;
    private String studentName;
    private Long organizationId;
    private Long studentId;
    private Float rating;
    private String comment;
    private boolean isAnonymous;
    private LocalDate createdAt;
}
