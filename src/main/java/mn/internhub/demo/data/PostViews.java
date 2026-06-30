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
    @Column(nullable = false,unique = true)
    private Long internshipPostId;
    @Column(nullable = false,unique = true)
    private Long userId;
    private Integer viewCount;
    private LocalDateTime createdAt;
}
