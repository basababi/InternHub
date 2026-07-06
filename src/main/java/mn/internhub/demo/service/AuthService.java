package mn.internhub.demo.service;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mn.internhub.demo.api.dto.authApiDto.RegisterOrganizationRequest;
import mn.internhub.demo.api.dto.authApiDto.*;
import mn.internhub.demo.data.*;
import mn.internhub.demo.data.enums.Role;
import mn.internhub.demo.data.enums.UserStatus;
import mn.internhub.demo.repository.*;
import mn.internhub.demo.security.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private TeacherRepository teacherRepository;
    @Autowired
    private OrganizationRepository organizationRepository;
    @Autowired
    private AdminRepository adminRepository;

    public AuthResponse registerStudent(@Valid RegisterStudentRequest request) {
        User user = baseRegister(request.baseRequest());

        Student student = Student.builder()
                .userId(user.getUserId())
                .firstName(request.firstName())
                .lastName(request.lastName())
                .build();
        studentRepository.save(student);
        String token = jwtService.generateToken(user);
        return new AuthResponse(token);
    }
    //багш бүртгүүлэх
    public AuthResponse registerTeacher(@Valid RegisterTeacherRequest request) {
        User user = baseRegister(request.baseRequest());
        Teacher teacher = Teacher.builder()
                .userId(user.getUserId())
                .firstName(request.firstName())
                .lastName(request.lastName())
                .build();
        teacherRepository.save(teacher);
        String token = jwtService.generateToken(user);
        return new AuthResponse(token);
    }
    //admin бүртгүүлэх
    public AuthResponse registerAdmin(@Valid RegisterAdminRequest request) {
        User user = baseRegister(request.baseRequest());
        Admin admin = Admin.builder()
                .userId(user.getUserId())
                .adminName(request.adminName())
                .build();
        adminRepository.save(admin);
        String token = jwtService.generateToken(user);
        return new AuthResponse(token);
    }
    //байгуллаг бүртгүүлэх
    public AuthResponse registerOrganization(@Valid RegisterOrganizationRequest request) {
        User user = baseRegister(request.baseRequest());
        Organizations organizations = Organizations.builder()
                .userId(user.getUserId())
                .organizationName(request.organizationName())
                .industry(request.industry())
                .build();
        organizationRepository.save(organizations);
        String token = jwtService.generateToken(user);
        return new AuthResponse(token);
    }

    //Helper functions
    private User baseRegister(RegisterBaseRequest reqUser) {
        LocalDateTime now = LocalDateTime.now();
        User user = User.builder()
                .email(reqUser.email())
                .password(passwordEncoder.encode(reqUser.password()))
                .role(reqUser.role())
                .status(reqUser.role() == Role.COMPANY? UserStatus.PENDING:UserStatus.ACTIVE)
                .createdAt(now)
                .lastLoginAt(now)
                .build();
        userRepository.save(user);
        return user;
    }
    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.password()));

        User user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new IllegalStateException("User not found: " + request.email()));

        String token = jwtService.generateToken(user);
        return new AuthResponse(token);
    }


}
