package mn.internhub.demo.data;

import jakarta.persistence.*;
import lombok.*;
import mn.internhub.demo.data.enums.ContentTypes;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data

@Table(name = "files")
public class FileEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    private Long fileId;
    @Column(unique = true)
    private Long reportId;
    private Long userId;
    private String fileName;
    private String fileType;

    @Enumerated(EnumType.STRING)
    private ContentTypes contentTypes;

    @Column(columnDefinition = "bytea")
    private byte[] data;


}
