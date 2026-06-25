package mn.internhub.demo.api;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mn.internhub.demo.api.dto.*;
import mn.internhub.demo.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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



    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }
}
