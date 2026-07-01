package mn.internhub.demo.data;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Table(name = "postViews")
public class PostViews {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    private Long viewId;
    private Long internshipPostId;
    private Integer viewCount;
    private LocalDateTime createdAt;
}
