package mn.internhub.demo.api;

import lombok.extern.slf4j.Slf4j;
import mn.internhub.demo.api.dto.organizationApiDto.RequestOwnprofileUpdate;
import mn.internhub.demo.api.dto.organizationApiDto.ResponseGetAllOrganization;
import mn.internhub.demo.data.FileEntity;
import mn.internhub.demo.data.OrganizationReview;
import mn.internhub.demo.data.Organizations;
import mn.internhub.demo.data.User;
import mn.internhub.demo.data.enums.ContentTypes;
import mn.internhub.demo.service.FileEntityService;
import mn.internhub.demo.service.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/organization")
public class OrganizationApi {
    @Autowired
    private OrganizationService organizationService;
    @Autowired
    private FileEntityService fileEntityService;

    //бүх байгуулгын мэдэллийг харах
    @GetMapping
    public List<ResponseGetAllOrganization> getAllOrganization(){
        return organizationService.getAllOrganization();
    }
    //Тухайн байгууллагын мэдээллийг дэлгэрэнгүй харах
    @GetMapping("/{orgId}")
    public Organizations getOrganizationById(@PathVariable Long orgId){
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
        return organizationService.getOrgReview(orgId);
    }
    //бүртгэлтэй буй бүх байгуллагын тоог авах
    @GetMapping("/all")
    public Long getAllOrgNum(){
        return organizationService.getAllOrgNum();
    }
    //create own profile photo
    @PostMapping("/profile/logo")
    public ResponseEntity<String> createLogo(@AuthenticationPrincipal User user, @RequestParam("file")MultipartFile file){
        return fileEntityService.createProfileImg(user.getUserId(), file, ContentTypes.LOGO);
    }
    //GET own profile photo
    @GetMapping("/profile/logo")
    public FileEntity getOwnLogo(@AuthenticationPrincipal User user){
        return fileEntityService.getProfileImg(user.getUserId(), ContentTypes.LOGO);
    }
    //update own profile photo
    @PutMapping("/profile/logo")
    public FileEntity updateOwnProfile(@AuthenticationPrincipal User user, @RequestParam("file") MultipartFile file){
        return fileEntityService.updateProfileImg(user.getUserId(), file, ContentTypes.LOGO);
    }

}
