package mn.internhub.demo.api;

import mn.internhub.demo.api.dto.adminApi.RequestUpdate;
import mn.internhub.demo.api.dto.adminApi.ResponseUserDetail;
import mn.internhub.demo.data.Student;
import mn.internhub.demo.data.User;
import mn.internhub.demo.data.enums.UserStatus;
import mn.internhub.demo.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
    @RequestMapping("/api/admin")
public class AdminApi {
    @Autowired
    private AdminService adminService;
    //систесийн бүх хэрэглэгчийг авна
    @GetMapping("/users")
    public List<User> getAllUsers(@AuthenticationPrincipal User user){
        return adminService.getAllUser(user.getUserId());
    }
    //хэрэглэгчийн дэлгэрэнгүй мэдээллйиг авна
    @GetMapping("/users/{userId}")
    public ResponseUserDetail getStudentDetailById(@AuthenticationPrincipal User user, @PathVariable Long userId){
        return adminService.getAllUsersDetailById(user.getUserId(), userId);
    }
    //хэрэглэгчдийн status-ийг өөрчилөх
    @PutMapping("/users/{userId}/status")
    public UserStatus updateUserStatus(@AuthenticationPrincipal User admin, @PathVariable Long userId, @RequestBody RequestUpdate request){
        return adminService.updateUserStatus(admin.getUserId(), userId, request);
    }
    //хэрэглэгчийн устгах
    @DeleteMapping("/users/{userId}")
    public void deleteUser(@AuthenticationPrincipal User admin, @PathVariable Long userId){
        adminService.deleteUser(admin.getUserId(), userId);
    }


}
