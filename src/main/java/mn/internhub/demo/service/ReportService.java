package mn.internhub.demo.service;

import lombok.extern.slf4j.Slf4j;
import mn.internhub.demo.api.dto.reportApiDto.RequestCreateReport;
import mn.internhub.demo.api.dto.reportApiDto.RequestReviewReport;
import mn.internhub.demo.api.dto.reportApiDto.RequestUpdateReport;
import mn.internhub.demo.api.dto.reportApiDto.ResponseReport;
import mn.internhub.demo.data.FileEntity;
import mn.internhub.demo.data.Report;
import mn.internhub.demo.data.User;
import mn.internhub.demo.data.enums.ContentTypes;
import mn.internhub.demo.data.enums.Status;
import mn.internhub.demo.repository.*;
import mn.internhub.demo.service.helperFunctions.gimmeId;
import mn.internhub.demo.service.helperFunctions.isItExist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.File;
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
    @Autowired
    private isItExist isItExist;
    @Autowired
    private FileEntityRepository fileEntityRepository;
    @Autowired
    private FileEntityService fileEntityService;
    @Autowired
    private gimmeId gimmeId;

    //сурагч нь тайлангаа багшруу явуулах
    public Report createReport(User user, RequestCreateReport request) {
        isItExist.isStudentByUserId(user.getUserId());
        Long studentId = studentRepository.findByUserId(user.getUserId()).getStudentId();
        Long teacherId = studentRepository.findById(studentId).orElseThrow(IllegalAccessError::new).getTeacherId();
        Report report = Report.builder()
                .teacherId(teacherId)
                .studentId(studentId)
                .title(request.title())
                .description(request.description())
                .status(Status.PENDING)
                .createdAt(LocalDate.now())
                .build();
        reportRepository.save(report);
        return report;
    }
    //сурагч нь өөрйин явуулсан тайлангаа авах
    public List<ResponseReport> getStudentReport(Long userId) {
        isItExist.isStudentByUserId(userId);
        Long studentId = gimmeId.userIdToStudentId(userId);

        List<Report> reports = reportRepository.findAllByStudentId(studentId);
        return reports.stream()
                .map((report)->{
                    FileEntity fileEntity = fileEntityRepository.findByReportIdAndContentTypes(report.getReportId(), ContentTypes.REPORT);


                    return ResponseReport.builder()
                            .reportId(report.getReportId())
                            .teacherId(report.getTeacherId())
                            .studentId(report.getStudentId())
                            .title(report.getTitle())
                            .status(report.getStatus())
                            .description(report.getDescription())
                            .teacherComment(report.getTeacherComment())
                            .createdAt(report.getCreatedAt())
                            .approvedAt(report.getApprovedAt())
                            .fileId(fileEntity != null ? fileEntity.getFileId() : null)
                            .fileName(fileEntity != null ? fileEntity.getFileName() : null)
                            .fileType(fileEntity != null ? fileEntity.getFileType() : null)
                            .data(fileEntity != null ? fileEntity.getData() : null)
                            .contentTypes(fileEntity != null ? fileEntity.getContentTypes() : null)
                            .build();

                })
                .toList();
    }
    //тайланг id-гаар нь авах
    public ResponseReport getReportById(Long userId, Long reportId) {
        //эхлээд тайлан нь байгаа эсхийг нь шалгаад
        boolean isExist = reportRepository.existsById(reportId);
        if (!isExist){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"тайлан чинь байхгүй байнаа хө");
        }
        //нэвтэрсэн хэрэглэгч нь багш эсвэл сурагч эсхийг нь шалгаад
        boolean isStudent = studentRepository.existsByUserId(userId);
        boolean isTeacher = teacherRepository.existsByUserId(userId);
        //сурагч эсхйиг нь шалгаад
        if(isStudent){
            //тайланг хийсэн сурагч мөн эсхийг нь шалгаад
            boolean isOwner = studentRepository.findByUserId(userId).getStudentId().equals(reportRepository.findById(reportId).orElseThrow(IllegalAccessError::new).getStudentId());
            if (!isOwner){
                throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE,"чиний тайлан биш байнаа хө");
            }
        }
        //багш мөн эсхийг нь шалгаад
        else if (isTeacher){
            //нэвтэрсэн багш нь тайлан дээрх багш мөн эсэх
            boolean relatedTeacher = teacherRepository.findByUserId(userId).getTeacherId().equals(reportRepository.findById(reportId).orElseThrow(IllegalAccessError::new).getTeacherId());
            if (!relatedTeacher){
                throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE,"чиний сурагчийн тайлан биш байнаа хө");
            }
        }
        else {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE,"хэн бээ чи");
        }
        Report report = reportRepository.findById(reportId).orElseThrow(IllegalAccessError::new);
        FileEntity fileEntity = fileEntityRepository.findByReportIdAndContentTypes(report.getReportId(), ContentTypes.REPORT);
        return ResponseReport.builder()
                .reportId(report.getReportId())
                .teacherId(report.getTeacherId())
                .studentId(report.getStudentId())
                .title(report.getTitle())
                .status(report.getStatus())
                .description(report.getDescription())
                .teacherComment(report.getTeacherComment())
                .createdAt(report.getCreatedAt())
                .approvedAt(report.getApprovedAt())
                .fileId(fileEntity.getReportId())
                .userId(fileEntity.getUserId())
                .fileName(fileEntity.getFileName())
                .fileType(fileEntity.getFileType())
                .data(fileEntity.getData())
                .contentTypes(fileEntity.getContentTypes())
                .build();
    }
    //тухайн сурачг нь өөрийн оруулсан тайлангаа засах
    public Report updateReport(Long userId,Long reportId, RequestUpdateReport request) {
        isItExist.isStudentByUserId(userId);
        if (!reportRepository.existsById(reportId)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"doesn't found");
        }
        Long studentId = studentRepository.findByUserId(userId).getStudentId();
        if (!reportRepository.findById(reportId).orElseThrow(IllegalAccessError::new).getStudentId().equals(studentId)){
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE,"чинийх биш байна");
        }
        Report report = reportRepository.findById(reportId).orElseThrow(IllegalAccessError::new);
        if(request.title() != null){
            report.setTitle(request.title());
        }
        if (request.description() != null){
            report.setDescription(request.description());
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
        return reportRepository.findAllByTeacherIdAndStatus(teacherId,Status.PENDING);
    }
    //багш нь тайланг үзсэний дараагаар тайлбар гэх мэт зүйл оруулна
    public Report reviewReport(Long userId,long reportId, RequestReviewReport request) {
        isItExist.isTeacherByUserId(userId);
        boolean isReportExist = reportRepository.existsById(reportId);
        if (!isReportExist){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Report doesn't found");
        }

        Report report = reportRepository.findById(reportId).orElseThrow(IllegalAccessError::new);

        report.setTeacherComment(request.teacherComment());
        report.setScore(report.getScore());
        report.setStatus(report.getStatus());
        reportRepository.save(report);
        return report;
    }

    public void deleteReport(Long userId, Long reportId) {
        isItExist.isStudentByUserId(userId);
        if (!reportRepository.existsById(reportId)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        boolean isOwner = reportRepository.findById(reportId).orElseThrow(IllegalAccessError::new).getStudentId().equals(gimmeId.userIdToStudentId(userId));
        if (!isOwner){
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE);
        }
        reportRepository.deleteById(reportId);
        fileEntityRepository.deleteByReportId(reportId);
    }
}
