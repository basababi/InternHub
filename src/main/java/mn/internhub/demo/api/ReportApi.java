package mn.internhub.demo.api;

import lombok.extern.slf4j.Slf4j;
import mn.internhub.demo.api.dto.reportApiDto.RequestCreateReport;
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
        return reportService.createReport(user.getUserId(), request);
    }
    //сурагч нь өөрийн явуулсан тайлангаа харах
    @GetMapping
    public List<Report> getStudentReports(@AuthenticationPrincipal User user){
        log.info("мэдээлэл авсан");
        return reportService.getStudentReport(user.getUserId());
    }
}
