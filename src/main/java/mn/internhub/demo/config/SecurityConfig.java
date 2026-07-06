package mn.internhub.demo.config;

import lombok.RequiredArgsConstructor;
import mn.internhub.demo.security.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final AuthenticationManager authenticationManager;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/**").permitAll()
                        .requestMatchers("/api/organization").permitAll()
                        .requestMatchers("/api/postings").permitAll()
                        .requestMatchers(HttpMethod.GET,"/api/reviews/org/*").permitAll()
                        .requestMatchers("/api/postings//All").permitAll()
                        .requestMatchers("/api/organization/all").permitAll()
                        .requestMatchers("/api/postings/search/**").permitAll()
                        .requestMatchers(HttpMethod.GET,"/api/organization/*").permitAll()
                        .requestMatchers(HttpMethod.GET,"/api/organization/*/reviews").permitAll()
                        .requestMatchers(HttpMethod.GET,"/api/postings/all").permitAll()
                        .requestMatchers(HttpMethod.GET,"/api/postings/search/*").permitAll()
                        .requestMatchers(HttpMethod.GET,"/api/students/all").permitAll()
                        .requestMatchers(HttpMethod.POST,"/api/auth/refresh").permitAll()

                        .requestMatchers(
                                HttpMethod.GET, "/api/postings/**").permitAll()
                        .anyRequest().authenticated())
//                        .anyRequest().permitAll())
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationManager(authenticationManager)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
