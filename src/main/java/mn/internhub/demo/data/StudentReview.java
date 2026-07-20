package mn.internhub.demo.data;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "studentReview")
public class StudentReview {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long StudentReviewId;

    private Long studentId;
    private Long userId;
    private Float rate;
    private String Comment;
    private LocalDateTime createdAt;

}
