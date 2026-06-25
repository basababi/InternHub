package mn.internhub.demo.data;

import jakarta.persistence.*;
import lombok.*;


import java.util.List;

@Entity
@Getter
@Setter
@Builder
@Table(name = "students")
@NoArgsConstructor
@AllArgsConstructor
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long studentId;

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    private Long userId;

    private String firstName;
    private String lastName;

    private String major;
    private String university;
    private Integer courseYear;
    private Integer gpa;
    private Double phone;
    private String shortBio;
    private List<String> skills;
    private List<String> languages;

}
