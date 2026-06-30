package mn.internhub.demo.api;

import mn.internhub.demo.api.dto.organizationApiDto.RequestOwnprofileUpdate;
import mn.internhub.demo.api.dto.organizationApiDto.ResponseGetAllOrganization;
import mn.internhub.demo.data.OrganizationReview;
import mn.internhub.demo.data.Organizations;
import mn.internhub.demo.data.User;
import mn.internhub.demo.service.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/organization")
public class OrganizationApi {
    @Autowired
    private OrganizationService organizationService;

    //бүх байгуулгын мэдэллийг харах
    @GetMapping
    public List<ResponseGetAllOrganization> getAllOrganization(){
        return organizationService.getAllOrganization();
    }
    //тухайн байгууллаг нь өөрийн мэдээллээ/профайлыг харах
    @GetMapping("/profile")
    public Organizations getOrgProfile(@AuthenticationPrincipal User user){
        return organizationService.getOrgProfile(user.getUserId());
    }
    //өөрийн мэдээллийг өөрчлөх
    @PutMapping("/profile")
    public Organizations updateOrgProfile(@AuthenticationPrincipal User user, @RequestBody RequestOwnprofileUpdate request){
        return organizationService.updateOrgProfile(user.getUserId(), request);
    }
    //тухайн байгууллагын үнэлгээ сэтгэгдлийг авах ингэхдэ тухайн байгуулгын id-гаар нь авна
    @GetMapping("/{id}/reviews")
    public List<OrganizationReview> getOrgReview(@AuthenticationPrincipal User user, @PathVariable long Id){
        return organizationService.getOrgReview(Id);
    }



}
