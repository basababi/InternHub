package mn.internhub.demo.api;

import mn.internhub.demo.api.dto.OrganizationReviewApi.RequestPostReview;
import mn.internhub.demo.data.OrganizationReview;
import mn.internhub.demo.data.User;
import mn.internhub.demo.repository.OrganizationReviewRepository;
import mn.internhub.demo.service.OrganizationReviewService;
import org.hibernate.annotations.Audited;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reviews")
public class OrganizationReviewApi {
    @Autowired
    private OrganizationReviewService organizationReviewService;
    //Сурагч нь байгууллагаа үнэлэх
    @PostMapping("/{orgid}")
    public OrganizationReview createReview(@AuthenticationPrincipal User user, @PathVariable Long orgdId, @RequestBody RequestPostReview request){
        return organizationReviewService.createReview(user.getUserId(), orgdId ,request);
    }
}
