package mn.internhub.demo.api;

import mn.internhub.demo.data.User;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;

import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class StudentApiTest {

    @Test
    void getAvgStudentScoreUsesAuthenticatedStudentWithoutPathVariable() throws NoSuchMethodException {
        Method method = StudentApi.class.getDeclaredMethod("getAvgStudentScore", User.class);

        GetMapping getMapping = method.getAnnotation(GetMapping.class);

        assertNotNull(getMapping);
        assertArrayEquals(new String[]{"/avg"}, getMapping.value());
        assertTrue(method.getParameters()[0].isAnnotationPresent(AuthenticationPrincipal.class));
    }
}
