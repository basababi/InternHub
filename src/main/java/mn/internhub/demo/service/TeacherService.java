package mn.internhub.demo.service;

import lombok.extern.slf4j.Slf4j;
import mn.internhub.demo.api.dto.authApiDto.AuthResponse;
import mn.internhub.demo.api.dto.teacherApiDto.RequestProfile;
import mn.internhub.demo.api.dto.teacherApiDto.RequestSaveOwnStudent;
import mn.internhub.demo.data.Student;
import mn.internhub.demo.data.Teacher;
import mn.internhub.demo.repository.StudentRepository;
import mn.internhub.demo.repository.TeacherRepository;
import mn.internhub.demo.repository.UserRepository;
import mn.internhub.demo.service.helperFunctions.gimmeId;
import mn.internhub.demo.service.helperFunctions.isItExist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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
    @Autowired
    private UserRepository userRepository;

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
    //Add own student
    public Student saveOwnStudent(Long userId, RequestSaveOwnStudent request) {
        isItExist.isTeacherByUserId(userId);
        boolean isExist = userRepository.existsByEmail(request.email());
        if (!isExist){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"ийм хүн байхгүй байна");
        }
        boolean isStudent = studentRepository.existsByUserId(userRepository.findByEmail(request.email()).orElseThrow(IllegalStateException::new).getUserId());
        if (!isStudent){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"ийм сурагч байхгүй байна");
        }
        Student student = studentRepository.findByUserId(userRepository.findByEmail(request.email()).orElseThrow(IllegalStateException::new).getUserId());
        boolean isCorrectStudent = student.getFirstName().equals(request.firstName());
        if (!isCorrectStudent){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"сурагчийн мэдээлэл зөрүүтэй байна");
        }
        if (student.getTeacherId() == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"аль хэдийн багшид бүртгэлтэй байна");
        }
        Long teacherId = gimmeId.userIdToTeacherId(userId);
        student.setTeacherId(teacherId);
        studentRepository.save(student);
        return student;
    }
    //delete own student
    public void deleteOwnStudent(Long userId, Long studentId) {
        isItExist.isTeacherByUserId(userId);
        isItExist.isStudentByUserId(studentId);
        Long teacherId = gimmeId.userIdToTeacherId(userId);
        Student student = studentRepository.findById(studentId).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"сурагч олдсонгүй"));
        if (student.getTeacherId() != teacherId){
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE,"чи багш нь биш байна");
        }
        student.setTeacherId(null);
        studentRepository.save(student);
    }
}
