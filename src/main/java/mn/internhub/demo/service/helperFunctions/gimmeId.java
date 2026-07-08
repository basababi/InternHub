package mn.internhub.demo.service.helperFunctions;

import mn.internhub.demo.repository.OrganizationRepository;
import mn.internhub.demo.repository.StudentRepository;
import mn.internhub.demo.repository.TeacherRepository;
import mn.internhub.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class gimmeId {
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private TeacherRepository teacherRepository;
    @Autowired
    private OrganizationRepository organizationRepository;

    //userId-гаар нь байгууллагын id-ийг буцаана
    public Long userIdToOrgId(Long userId){
        return organizationRepository.findByUserId(userId).getOrganizationId();
    }
    //userId-гаар нь багшийн id-ийг буцаана
    public Long userIdToTeacherId(Long userId){
        return teacherRepository.findByUserId(userId).getTeacherId();
    }
    //userId-гаар нь сурагчийн id-ийг буцаана
    public Long userIdToStudentId(Long userId){
        return studentRepository.findByUserId(userId).getStudentId();
    }


}
