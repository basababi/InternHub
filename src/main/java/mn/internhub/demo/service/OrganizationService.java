package mn.internhub.demo.service;

import lombok.extern.slf4j.Slf4j;
import mn.internhub.demo.api.dto.organizationApiDto.RequestOwnprofileUpdate;
import mn.internhub.demo.api.dto.organizationApiDto.ResponseGetAllOrganization;
import mn.internhub.demo.data.Organizations;
import mn.internhub.demo.repository.OrganizationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Slf4j
@Service
public class OrganizationService {
    @Autowired
    private OrganizationRepository organizationRepository;
    //Нийтэд ил харагдах байдлаар бүх байгуулгын мэдээллийг авах
    public List<ResponseGetAllOrganization> getAllOrganization() {
        List<Organizations> allOrganizations = organizationRepository.findAll();
        List<ResponseGetAllOrganization> publicAllOrganization = allOrganizations.stream()
                .map(perOrganization ->{
                    return ResponseGetAllOrganization.builder()
                            .organizationName(perOrganization.getOrganizationName())
                            .address(perOrganization.getAddress())
                            .city(perOrganization.getCity())
                            .logoUrl(perOrganization.getLogoUrl())
                            .description(perOrganization.getDescription())
                            .industry(perOrganization.getIndustry())
                            .build();
                })
                .toList();
        return publicAllOrganization;
    }
    //өөрийн мэдээллээ харах
    public Organizations getOrgProfile(Long userId) {
        isOrg(userId);
        log.info("энэ хүртэл бол амжилттай");
        return organizationRepository.findByUserId(userId);
    }
    //өөрийн мэдээллийг оруулах/өөрчлөх
    public Organizations updateOrgProfile(Long userId, RequestOwnprofileUpdate request){
        isOrg(userId);
        Organizations organization = organizationRepository.findByUserId(userId);
        if(request.organizationName() != null){
            organization.setOrganizationName(request.organizationName());
        }
        if(request.industry() != null){
            organization.setIndustry(request.industry());
        }
        if(request.address() != null){
            organization.setAddress(request.address());
        }
        if(request.city() != null){
            organization.setCity(request.city());
        }
        if(request.site() != null){
            organization.setSite(request.site());
        }
        if(request.logoUrl() != null){
            organization.setLogoUrl(request.logoUrl());
        }
        if(request.description() != null){
            organization.setDescription(request.description());
        }
        return organizationRepository.save(organization);
    }
    //helper function
    public void isOrg(Long userId){
        boolean isOrg = organizationRepository.existsByUserId(userId);
        if (!isOrg){
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE,"Not acceptable");
        }
    }
}
