package mn.internhub.demo.data;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Data
@Builder
@Getter
@Setter
@Table(name = "notificaitons")
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    private Long notificationId;
    private Long UserId;
    private String message;
    private boolean isRead;
    private LocalDateTime createdAt ;
}
