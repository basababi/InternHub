package mn.internhub.demo.api;

import mn.internhub.demo.api.dto.OrganizationReviewApi.RequestPostReview;
import mn.internhub.demo.api.dto.OrganizationReviewApi.RequestUpdateReview;
import mn.internhub.demo.data.OrganizationReview;
import mn.internhub.demo.data.User;
import mn.internhub.demo.service.OrganizationReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
public class OrganizationReviewApi {
    @Autowired
    private OrganizationReviewService organizationReviewService;
    //Сурагч нь байгууллагаа үнэлэх
    @PostMapping("/{orgId}")
    public OrganizationReview createReview(@AuthenticationPrincipal User user, @PathVariable Long orgId, @RequestBody RequestPostReview request){
        return organizationReviewService.createReview(user.getUserId(), orgId ,request);
    }
    //тухайн байгууллаг дээр ирсэн бүх үнэлгээг сэтгэгдэл олон нийт харах
    @GetMapping("/org/{orgId}")
    public List<OrganizationReview> getAllReview(@PathVariable Long orgId){
        return organizationReviewService.getAllReview(orgId);
    }
    //сурагч нь оруулсан үнэлгээгээ засах
    @PutMapping("/{revId}")
    public OrganizationReview updateReview(@AuthenticationPrincipal User user, @PathVariable Long revId, RequestUpdateReview request){
        return organizationReviewService.updateReview(user.getUserId(), revId,request);
    }
    //сурагч нь үнэлсэн үнэлгээгээ устгах
    @DeleteMapping("/{revId}")
    public void deleteReview(@AuthenticationPrincipal User user, @PathVariable Long revId){
        organizationReviewService.deleteReview(user.getUserId(), revId);

    }



}
