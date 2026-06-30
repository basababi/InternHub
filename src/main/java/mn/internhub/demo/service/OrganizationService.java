package mn.internhub.demo.service;

import mn.internhub.demo.api.dto.organizationApiDto.ResponseGetAllOrganization;
import mn.internhub.demo.data.Organizations;
import mn.internhub.demo.repository.OrganizationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
                            .build();
                })
                .toList();
        return publicAllOrganization;
    }
}
