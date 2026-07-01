package mn.internhub.demo.service;

import lombok.extern.slf4j.Slf4j;
import mn.internhub.demo.api.dto.reportApiDto.RequestCreateReport;
import mn.internhub.demo.data.Report;
import mn.internhub.demo.data.enums.Status;
import mn.internhub.demo.repository.ReportRepository;
import mn.internhub.demo.repository.StudentRepository;
import mn.internhub.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
public class ReportService {
    @Autowired
    private ReportRepository reportRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private StudentRepository studentRepository;

    //сурагч нь тайлангаа багшруу явуулах
    public Report createReport(Long userId, RequestCreateReport request) {
        boolean isUserExist = studentRepository.existsByUserId(userId);
        Long studentId = studentRepository.findByUserId(userId).getStudentId();
        if (!isUserExist){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Student doesn't found");
        }
        Report report = Report.builder()
                .teacherId(request.teacherId())
                .studentId(studentId)
                .title(request.title())
                .status(Status.PENDING)
                .submittedAt(LocalDate.now())
                .build();
        reportRepository.save(report);
        return report;
    }
    //сурагч нь өөрйин явуулсан тайлангаа авах
    public List<Report> getStudentReport(Long userId) {
        isStudentExists(userId);
        log.info("нэвтэрсэн");
        Long studentId = studentRepository.findByUserId(userId).getStudentId();
        log.info("id авсан");
        List<Report> ownReports = reportRepository.findAllByStudentId(studentId);
        log.info("report авсан");
        return ownReports;
    }
    //helper function
    public void isStudentExists(Long userId){
        boolean isExists = studentRepository.existsByUserId(userId);
        if (!isExists){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Not found");
        }
    }

}
