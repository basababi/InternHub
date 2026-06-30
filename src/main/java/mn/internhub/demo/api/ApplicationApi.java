package mn.internhub.demo.api;

import lombok.RequiredArgsConstructor;
import mn.internhub.demo.api.dto.RequestApplication;
import mn.internhub.demo.data.Application;
import mn.internhub.demo.data.User;
import mn.internhub.demo.service.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/applications")
public class ApplicationApi {
    @Autowired
    private ApplicationService applicationService;

    @PostMapping
    public Application createApplication(@AuthenticationPrincipal User user, @RequestBody RequestApplication request){
        return  applicationService.createApplication(request);
    }
}
