package mn.internhub.demo.service;

import lombok.RequiredArgsConstructor;
import mn.internhub.demo.api.dto.AuthResponse;
import mn.internhub.demo.api.dto.LoginRequest;
import mn.internhub.demo.api.dto.RegisterRequest;
import mn.internhub.demo.data.User;
import mn.internhub.demo.data.enums.Role;
import mn.internhub.demo.repository.UserRepository;
import mn.internhub.demo.security.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.email())) {
            throw new IllegalStateException("Email already registered: " + request.email());
        }

        User user = User.builder()
                .name(request.name())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .role(Role.USER)
                .build();
        userRepository.save(user);

        String token = jwtService.generateToken(user);
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
