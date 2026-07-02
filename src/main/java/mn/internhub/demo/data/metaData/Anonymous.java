package mn.internhub.demo.data.metaData;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "anonymous")
public class Anonymous {
    @Id
    private Long anonymousID;
    private Long lastSequence;
}
