package mn.internhub.demo.data;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.sql.Timestamp;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "organizations")
public class Organizations {
    @Id
    @Column(nullable = false, unique = true)
    private Long organizationId;

    @Column(nullable = false, unique = true)
    private Long userId;

    @Column(nullable = false, unique = true)
    private String organizationName;

    private String industry;
    private String address;
    private String city;
    private String site;
    private String logoUrl;
    private String description;
    private Boolean isVerified;
    private Timestamp createdAt;
}
