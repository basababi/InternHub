package mn.internhub.demo.service;

import lombok.extern.slf4j.Slf4j;
import mn.internhub.demo.api.dto.reportApiDto.RequestCreateReport;
import mn.internhub.demo.api.dto.reportApiDto.RequestUpdateReport;
import mn.internhub.demo.data.Report;
import mn.internhub.demo.data.enums.Status;
import mn.internhub.demo.repository.ReportRepository;
import mn.internhub.demo.repository.StudentRepository;
import mn.internhub.demo.repository.TeacherRepository;
import mn.internhub.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.parameters.P;
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
    @Autowired
    private TeacherRepository teacherRepository;

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
    //тайланг id-гаар нь авах
    public Report getReportById(Long userId, Long id) {
        boolean isTeacher = teacherRepository.existsByUserId(userId);
        if (!isTeacher){
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE,"Not acceptable");
        }
        return reportRepository.findById(id).orElseThrow(IllegalAccessError::new);
    }
    //тухайн сурачг нь өөрийн оруулсан тайлангаа засах
    public Report updateReport(Long userId,Long reportId, RequestUpdateReport request) {
        isStudentExists(userId);
        Long studentId = studentRepository.findByUserId(userId).getStudentId();
        Report report = reportRepository.findById(reportId).orElseThrow(IllegalAccessError::new);
        if(report.getStudentId() != studentId){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Not found ");
        }
        if(request.title() != null){
            report.setTitle(report.getTitle());
        }
        reportRepository.save(report);
        return report;
    }
    //багш нь өөр дээр нь ирсэн тайлангуудыг нь харах pending төлөвтай байгаа зүйлсийг
    public List<Report> getPendingReports(Long userId) {
        boolean isTeacherExist = teacherRepository.existsByUserId(userId);
        if (!isTeacherExist){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"not found");
        }
        Long teacherId = teacherRepository.findByUserId(userId).getTeacherId();
        return reportRepository.findAllByTeacherId(teacherId);
    }

    //helper function
    public void isStudentExists(Long userId){
        boolean isExists = studentRepository.existsByUserId(userId);
        if (!isExists){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Not found");
        }
    }


}
