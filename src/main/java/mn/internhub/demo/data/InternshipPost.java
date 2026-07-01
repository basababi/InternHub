package mn.internhub.demo.data;

import jakarta.persistence.*;
import lombok.*;
import mn.internhub.demo.data.enums.PostStatus;
import mn.internhub.demo.data.enums.Status;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "InternshipPosts")
public class InternshipPost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    private Long internshipPostId;

    private Long organizationId;
    private String title;
    private String description;
    private List<String> requiredMajors;
    private BigDecimal minGpa;
    private List<String> requiredSkills;
    private Integer salaryMin;
    private Integer salaryMax;
    private boolean isSalaryUnspecified;
    private Integer vacancyCount;
    private LocalDateTime deadline;
    private PostStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
