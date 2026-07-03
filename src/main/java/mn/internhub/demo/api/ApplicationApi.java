package mn.internhub.demo.api;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mn.internhub.demo.api.dto.applicationApiDto.RequestApplication;
import mn.internhub.demo.api.dto.applicationApiDto.ResponseApplicationDetail;
import mn.internhub.demo.api.dto.applicationApiDto.ResponseApplicationsToOrganization;
import mn.internhub.demo.data.Application;
import mn.internhub.demo.data.User;
import mn.internhub.demo.data.enums.Status;
import mn.internhub.demo.service.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/applications")
public class ApplicationApi {
    @Autowired
    private ApplicationService applicationService;
    //тухайн суралцагч нь application илгээнэ үүсгэнэ
    @PostMapping("/{postId}")
    public Application createApplication(@AuthenticationPrincipal User user,@PathVariable Long postId ,@RequestBody RequestApplication request){
        return  applicationService.createApplication(user.getUserId(), postId ,request);
    }
    //компани эсвэл багш нь тухайн сурагчийн application-ийг илүү дэлгэрэнгүй cv шиг байдлаар авна
    @GetMapping("/{appId}")
    public ResponseApplicationDetail getApplicationDetail(@AuthenticationPrincipal User user, @PathVariable Long appId){
        return applicationService.getApplicationDetail(user.getUserId(),appId);
    }
    //Компани нь ирсэн application-ийг шүүгээд төлвийг нь өөрчилж болдог болгов
    @PutMapping("/{id}/")
    public void updateApplicationStatus(@AuthenticationPrincipal User user,@PathVariable Long id, @Valid @RequestBody Status status){
        applicationService.updateApplicationStatus(id, status);
    }
    //тухайн сурагч нь өөрийн илгээсэн application-ийн өөрөө цуцлах
    @PutMapping("/{id}/withdraw")
    public void updateApplicationStatusByStudent(@AuthenticationPrincipal User user,  @PathVariable Long id){
        applicationService.updateApplicationStatusByStudent(id);
    }
    //тухайн комтани нь өөр дээр нь ирсэн application-ний хүсэлтийг харах
    @GetMapping("/pending")
    public List<ResponseApplicationsToOrganization> getPendingApplications(@AuthenticationPrincipal User user){
        return applicationService.getPendingApplications(user.getUserId());
    }

}
