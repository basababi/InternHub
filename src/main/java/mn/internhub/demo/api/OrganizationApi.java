package mn.internhub.demo.api;

import lombok.extern.slf4j.Slf4j;
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

@Slf4j
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
    //Тухайн байгууллагын мэдээллийг дэлгэрэнгүй харах
    @GetMapping("/{orgId}")
    public Organizations getOrganizationById(@PathVariable Long orgId){
        log.info("энэ ажиллаж байгаа шдээээээ");
        return organizationService.getOrganizationById(orgId);
    }
    //тухайн байгууллаг нь өөрийн мэдээллээ/профайлыг харах
    @GetMapping("/profile")
    public Organizations getOrgProfile(@AuthenticationPrincipal User user){
        return organizationService.getOrgProfile(user.getUserId());
    }
    //өөрийн мэдээллийг өөрчлөх ингэхдээ orgId
    @PutMapping("/profile")
    public Organizations updateOrgProfile(@AuthenticationPrincipal User user, @RequestBody RequestOwnprofileUpdate request){
        return organizationService.updateOrgProfile(user.getUserId(), request);
    }
    //тухайн байгууллагын үнэлгээ сэтгэгдлийг авах ингэхдэ тухайн байгуулгын id-гаар нь авна
    @GetMapping("/{orgId}/reviews")
    public List<OrganizationReview> getOrgReview(@PathVariable long orgId){
        log.info("энэ ажилсан 1");
        return organizationService.getOrgReview(orgId);
    }
    //бүртгэлтэй буй бүх байгуллагын тоог авах
    @GetMapping("/all")
    public Long getAllOrgNum(){
        return organizationService.getAllOrgNxum();
    }
}
