package mn.internhub.demo.api;

import lombok.extern.slf4j.Slf4j;
import mn.internhub.demo.api.dto.reportApiDto.RequestCreateReport;
import mn.internhub.demo.api.dto.reportApiDto.RequestReviewReport;
import mn.internhub.demo.api.dto.reportApiDto.RequestUpdateReport;
import mn.internhub.demo.data.Report;
import mn.internhub.demo.data.User;
import mn.internhub.demo.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/reports")
public class ReportApi {
    @Autowired
    private ReportService reportService;
    //Сурагч нь тайлан илгээх
    @PostMapping
    public Report createReport(@AuthenticationPrincipal User user, @RequestBody RequestCreateReport request){
        return reportService.createReport(user, request);
    }
    //сурагч нь өөрийн явуулсан тайлангаа харах
    @GetMapping
    public List<Report> getStudentReports(@AuthenticationPrincipal User user){
        return reportService.getStudentReport(user.getUserId());
    }
    //тайланг id-гаар нь авах
    @GetMapping("/{reportId}")
    public Report getReportById(@AuthenticationPrincipal User user, @PathVariable Long reportId){
        return reportService.getReportById(user.getUserId(), reportId);
    }
    //тайланг сурагч нь өөрчилөх засах тухайн сурагч нь
    @PutMapping("/{reportId}")
    public Report updateReport(@AuthenticationPrincipal User user,@PathVariable Long reportId , @RequestBody RequestUpdateReport request){
        return reportService.updateReport(user.getUserId(),reportId , request);
    }
    //багш нь өөр дээр нь ирсэн тайлангуудыг нь харах pending төлөвтай байгаа зүйлсийг
    @GetMapping("/pending")
    public List<Report> getPendingReports(@AuthenticationPrincipal User user){
        return reportService.getPendingReports(user.getUserId());
    }
    //багш нь тайланг үзсэний дараагаар тайлбар гэх мэт зүйл оруулна
    @PutMapping("/{reportId}/review")
    public Report reviewReports(@AuthenticationPrincipal User user,@PathVariable Long reportId ,@RequestBody RequestReviewReport request){

        return reportService.reviewReport(user.getUserId(), reportId,request);
    }
}
