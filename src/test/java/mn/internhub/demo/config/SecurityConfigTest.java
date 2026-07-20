package mn.internhub.demo.config;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.cors.CorsConfiguration;

class SecurityConfigTest {

    @Test
    void corsConfigurationAllowsLocalFrontendJwtRequests() {
        SecurityConfig securityConfig = new SecurityConfig(null, null);
        MockHttpServletRequest request = new MockHttpServletRequest("OPTIONS", "/api/auth/login");

        CorsConfiguration cors = securityConfig.corsConfigurationSource()
                .getCorsConfiguration(request);

        assertEquals(List.of("http://localhost:3000"), cors.getAllowedOrigins());
        assertEquals(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"), cors.getAllowedMethods());
        assertEquals(List.of(HttpHeaders.CONTENT_TYPE, HttpHeaders.AUTHORIZATION), cors.getAllowedHeaders());
    }
}
