package mn.internhub.demo.api;

import mn.internhub.demo.data.Student;
import mn.internhub.demo.data.User;
import mn.internhub.demo.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminApi {
    @Autowired
    private AdminService adminService;
    //систесийн бүх сурагчийн авна
    @GetMapping("/users")
    public List<Student> getAllStudent(@AuthenticationPrincipal User user){
        return adminService.getAllStudent(user.getUserId());
    }
    //сурагчийн дэлгэрэнгүй мэдээллйиг авна
    @GetMapping("/users/{studentId}")
    public Student getStudentDetailById(@AuthenticationPrincipal User user, @PathVariable Long studentId){
        return adminService.getAllStudentDetailById(user.getUserId(), studentId);
    }
    
}
