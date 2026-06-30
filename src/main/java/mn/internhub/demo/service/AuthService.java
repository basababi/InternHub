package mn.internhub.demo.service;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mn.internhub.demo.api.dto.authApiDto.RegisterOrganizationRequest;
import mn.internhub.demo.api.dto.authApiDto.*;
import mn.internhub.demo.data.Organizations;
import mn.internhub.demo.data.Student;
import mn.internhub.demo.data.Teacher;
import mn.internhub.demo.data.User;
import mn.internhub.demo.repository.OrganizationRepository;
import mn.internhub.demo.repository.StudentRepository;
import mn.internhub.demo.repository.TeacherRepository;
import mn.internhub.demo.repository.UserRepository;
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


    public AuthResponse registerTeacher(@Valid RegisterTeacherRequest request) {
        log.info("Энэ хүртэл бол ямар ч асуудалгүй явлаа");
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
                .isActive(true)
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
