package mn.internhub.demo.repository;

import mn.internhub.demo.data.Report;
import mn.internhub.demo.data.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReportRepository extends JpaRepository<Report, Long> {
    List<Report> findAllByStudentId(Long studentId);
    List<Report> findAllByTeacherIdAndStatus(Long teacherId, Status status);

    List<Report> findAllStudentIdAndStatus(Long studentId, Status status);
}
