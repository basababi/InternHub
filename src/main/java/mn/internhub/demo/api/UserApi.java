package mn.internhub.demo.api;

import mn.internhub.demo.data.User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserApi {

    /** Returns the currently authenticated user — requires a valid Bearer token. */
    @GetMapping("/me")
    public Map<String, Object> me(@AuthenticationPrincipal User user) {
        return Map.of(
                "id", user.getUserId(),
                "email", user.getEmail(),
                "role", user.getRole());
    }

}
