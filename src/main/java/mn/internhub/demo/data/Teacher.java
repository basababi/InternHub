package mn.internhub.demo.data;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "teachers")
public class Teacher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true )
    private Long teacherId;

    @Column(nullable = false, unique = true)
    private Long userId;

    private String firstName;
    private String lastName;
    private Double phone;
}
