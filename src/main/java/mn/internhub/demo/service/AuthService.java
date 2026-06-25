package mn.internhub.demo.service;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import mn.internhub.demo.api.dto.AuthResponse;
import mn.internhub.demo.api.dto.LoginRequest;
import mn.internhub.demo.api.dto.RegisterRequest;
import mn.internhub.demo.data.Student;
import mn.internhub.demo.data.User;
import mn.internhub.demo.data.enums.Role;
import mn.internhub.demo.repository.UserRepository;
import mn.internhub.demo.security.JwtService;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthResponse register(RegisterRequest request) {
        LocalDateTime now =LocalDateTime.now();
        if (userRepository.existsByEmail(request.email())) {
            throw new IllegalStateException("Email already registered: " + request.email());
        }
        if (request.role() == Role.STUDENT){
//            User user = User.builder()
//                    .email(request.email())
//                    .password(passwordEncoder.encode(request.password()))
//                    .role(Role.STUDENT)
//                    .isActive(true)
//                    .createdAt(now)
//                    .lastLoginAt(now)
//                    .build();
//            userRepository.save(user);
//
//            Student student = Student.builder()
//                    .
//                    .build();
//
        }
        if(request.role() == Role.ADMIN){
//            userRepository.save(user);
//            String token = jwtService.generateToken(user);

        } else if (request.role() == Role.TEACHER) {

        }
        else if (request.role() == Role.COMPANY){

        }
        else {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE,"Role doesn't found");
        }


        return new AuthResponse(token);
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
