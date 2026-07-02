package mn.internhub.demo.service.helperFunctions;

import mn.internhub.demo.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
public class isItExist {
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private TeacherRepository teacherRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private OrganizationRepository organizationRepository;
    @Autowired
    private AdminRepository adminRepository;
    //тухайн id-дээр хэрэглэгч байгааг шалгана
    public void isUserExistByUserId(long id){
        boolean isUserExist = userRepository.existsById(id);
        if (!isUserExist){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"this user doesn't exist");
        }
    }
    //тухайн багш нь байгааг шалгана
    public void isTeacherExistByTeacherId(long id){
        boolean isTeacherExist = teacherRepository.existsById(id);
        if (!isTeacherExist){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"this teacher doesn't exist");
        }
    }
    //тухайн байгууллаг нь байгааг шалгана
    public void isOrgExistByOrgId(long id){
        boolean isOrgExist = organizationRepository.existsById(id);
        if (!isOrgExist){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"this organization doesn't exist");
        }
    }
    //тухайн сурагч нь байгааг шалгана
    public void isStudentExistByStudentId(long id){
        boolean isStudentExist = studentRepository.existsById(id);
        if (!isStudentExist){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"this student doesn't exist");
        }
    }
    //тухайн хэрэглэгч нь багш байна
    public void isTeacherByUserId(long id){
        boolean isTeacherExist = teacherRepository.existsByUserId(id);
        if (!isTeacherExist){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"this user is not teacher");
        }
    }
    //тухайн байгууллаг нь байгааг шалгана
    public void isOrgByUserId(long id){
        boolean isOrgExist = organizationRepository.existsByUserId(id);
        if (!isOrgExist){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"this user is not organization");
        }
    }
    //тухайн сурагч нь байгааг шалгана
    public void isStudentByUserId(long id){
        boolean isStudentExist = studentRepository.existsByUserId(id);
        if (!isStudentExist){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"this user is not student");
        }
    }
    //тухайн админ байгааг шалгана
    public void isAdminByUserId(long id){
        boolean isAdminExist = adminRepository.existsByUserId(id);
        if (!isAdminExist){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"this user is not admin");
        }
    }
}
