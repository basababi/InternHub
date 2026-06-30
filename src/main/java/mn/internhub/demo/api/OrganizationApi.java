package mn.internhub.demo.api;

import mn.internhub.demo.api.dto.organizationApiDto.ResponseGetAllOrganization;
import mn.internhub.demo.data.Organizations;
import mn.internhub.demo.service.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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



}
