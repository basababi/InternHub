package mn.internhub.demo.api;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mn.internhub.demo.api.dto.authApiDto.*;
import mn.internhub.demo.data.User;
import mn.internhub.demo.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.stringtemplate.v4.ST;

@Slf4j
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthApi {

    private final AuthService authService;

    //оюутан бүртгэх
    @PostMapping("/register/student")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterStudentRequest request) {
        return ResponseEntity.ok(authService.registerStudent(request));
    }

    //багш бүртгэх
    @PostMapping("/register/teacher")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterTeacherRequest request) {
        return ResponseEntity.ok(authService.registerTeacher(request));
    }

    //байгуулга бүртгэх
    @PostMapping("/register/organization")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterOrganizationRequest request) {
        return ResponseEntity.ok(authService.registerOrganization(request));
    }
    //админ бүртгэх
    @PostMapping("/register/admin")
    public ResponseEntity<AuthResponse> registerAdmin(@Valid @RequestBody RegisterAdminRequest request){
        return ResponseEntity.ok(authService.registerAdmin(request));
    }
    //хэрэглэгч нэвтрэх
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    //token refresh
    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refresh(@RequestBody RequestRefreshToken request){
        return ResponseEntity.ok(authService.refresh(request));
    }
    //reset password
    @PostMapping("/change-password")
    public ResponseEntity<String> resetPass(@AuthenticationPrincipal User user, @RequestBody RequestPassword request){
        log.info("явц 0");
        return authService.resetPass(user, request);
    }
}
