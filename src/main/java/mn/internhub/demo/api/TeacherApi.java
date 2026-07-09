package mn.internhub.demo.api;

import jdk.dynalink.linker.LinkerServices;
import lombok.Data;
import mn.internhub.demo.api.dto.authApiDto.AuthResponse;
import mn.internhub.demo.api.dto.teacherApiDto.RequestProfile;
import mn.internhub.demo.api.dto.teacherApiDto.RequestSaveOwnStudent;
import mn.internhub.demo.api.dto.teacherApiDto.ResponseAllStudnets;
import mn.internhub.demo.data.Student;
import mn.internhub.demo.data.Teacher;
import mn.internhub.demo.data.User;
import mn.internhub.demo.service.TeacherService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/teacher")
public class TeacherApi {
    @Autowired
    private TeacherService teacherService;

    //change own profile data
    @PutMapping("/profile")
    public Teacher changeOwnProfile(@AuthenticationPrincipal User user, @RequestBody RequestProfile request) {
        return teacherService.changeOwnProfile(user.getUserId(), request);
    }

    //get own profile data
    @GetMapping("/profile")
    public Teacher geOwnProfile(@AuthenticationPrincipal User user) {
        return teacherService.getOwnProfile(user.getUserId());
    }
    //get teacher detail
    @GetMapping("/{teacherId}")
    public Teacher getTeacherDetail(@AuthenticationPrincipal User user, @PathVariable Long teacherId){
        return teacherService.getTeacherDetail(user.getUserId(),teacherId);
    }
    //get own related students
    @GetMapping("/students")
    public List<Student> getAllOwnStudent(@AuthenticationPrincipal User user){
        return teacherService.getAllOwnStudent(user.getUserId());
    }
    //Add own student
    @PutMapping("/student")
    public Student saveOwnStudent(@AuthenticationPrincipal User user, @RequestBody RequestSaveOwnStudent request){
        return teacherService.saveOwnStudent(user.getUserId(), request);
    }
    //delete own student
    @DeleteMapping("/student/{studentId}")
    public ResponseEntity<String> deleteOwnStudent(@AuthenticationPrincipal User user, @PathVariable Long studentId){
        teacherService.deleteOwnStudent(user.getUserId(), studentId);
        return ResponseEntity.ok("ажмилттай нууц үг солигдлоо");
    }
    //get count of own students
    @GetMapping("/studentsnum")
    public Integer getCountOfStudent(@AuthenticationPrincipal User user){
        return teacherService.getCountOfStudent(user.getUserId());
    }

}
