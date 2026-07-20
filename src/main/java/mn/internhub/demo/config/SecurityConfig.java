package mn.internhub.demo.config;

import java.util.List;

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
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final AuthenticationManager authenticationManager;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(csrf -> csrf.disable())
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
                        .requestMatchers(HttpMethod.GET,"/api/evaluations/avg/*").permitAll()

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

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        //front end талаас ирэх хүсэлйиг зөвхөөрөх port
        config.setAllowedOrigins(List.of("http://localhost:3000"));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("Content-Type", "Authorization"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

}
