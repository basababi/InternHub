package mn.internhub.demo.service;

import lombok.extern.slf4j.Slf4j;
import mn.internhub.demo.api.dto.teacherApiDto.RequestProfile;
import mn.internhub.demo.data.Student;
import mn.internhub.demo.data.Teacher;
import mn.internhub.demo.repository.StudentRepository;
import mn.internhub.demo.repository.TeacherRepository;
import mn.internhub.demo.service.helperFunctions.gimmeId;
import mn.internhub.demo.service.helperFunctions.isItExist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class TeacherService {
    @Autowired
    private TeacherRepository teacherRepository;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private isItExist isItExist;
    @Autowired
    private gimmeId gimmeId;

    //change own profile data
    public Teacher changeOwnProfile(Long userId, RequestProfile request) {
        isItExist.isTeacherByUserId(userId);
        Teacher teacher = teacherRepository.findByUserId(userId);
        if(request.firstName() != null){
            teacher.setFirstName(request.firstName());
        }
        if(request.lastName() != null){
            teacher.setLastName(request.lastName());
        }
        if(request.phone() != null){
            teacher.setPhone(request.phone());
        }
        teacherRepository.save(teacher);
        return teacher;
    }
    //get own profile data
    public Teacher getOwnProfile(Long userId) {
        isItExist.isTeacherByUserId(userId);
        return teacherRepository.findByUserId(userId);
    }
    //get own related students
    public List<Student> getAllOwnStudent(Long userId) {
        isItExist.isTeacherByUserId(userId);
        Long teacherId = gimmeId.userIdToTeacherId(userId);
        return studentRepository.findAllByTeacherId(teacherId);
    }
}
