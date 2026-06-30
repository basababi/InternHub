package mn.internhub.demo.api;

import lombok.RequiredArgsConstructor;
import mn.internhub.demo.api.dto.RequestApplication;
import mn.internhub.demo.api.dto.ResponseApplicationDetail;
import mn.internhub.demo.data.Application;
import mn.internhub.demo.data.User;
import mn.internhub.demo.data.enums.Status;
import mn.internhub.demo.service.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/applications")
public class ApplicationApi {
    @Autowired
    private ApplicationService applicationService;
    //тухайн суралцагч нь application илгээнэ үүсгэнэ
    @PostMapping
    public Application createApplication(@AuthenticationPrincipal User user, @RequestBody RequestApplication request){
        return  applicationService.createApplication(request);
    }
    //компани эсвэл багш нь тухайн сурагчийн application-ийг илүү дэлгэрэнгүй cv шиг байдлаар авна
    @GetMapping("/{id}")
    public ResponseApplicationDetail getApplicationDetail(@AuthenticationPrincipal User user, @PathVariable Long applicationId){
        return applicationService.getApplicationDetail(applicationId);
    }
    //
    @PutMapping("/{id}/")
    public void updateApplicationStatus(@AuthenticationPrincipal User user,@PathVariable Long id, @RequestBody Status status){
        applicationService.updateApplicationStatus(id, status);
    }

}
