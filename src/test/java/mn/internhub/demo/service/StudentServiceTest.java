package mn.internhub.demo.service;

import mn.internhub.demo.data.enums.EvaluationStatus;
import mn.internhub.demo.repository.EvaluationRepository;
import mn.internhub.demo.service.helperFunctions.gimmeId;
import mn.internhub.demo.service.helperFunctions.isItExist;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class StudentServiceTest {

    @Test
    void getAvgStudentScoreReturnsZeroWhenStudentHasNoEvaluations() {
        StudentService studentService = new StudentService();
        isItExist existenceChecker = mock(isItExist.class);
        gimmeId idConverter = mock(gimmeId.class);
        EvaluationRepository evaluationRepository = mock(EvaluationRepository.class);

        ReflectionTestUtils.setField(studentService, "isItExist", existenceChecker);
        ReflectionTestUtils.setField(studentService, "gimmeId", idConverter);
        ReflectionTestUtils.setField(studentService, "evaluationRepository", evaluationRepository);
        when(idConverter.userIdToStudentId(1L)).thenReturn(10L);
        when(evaluationRepository.findAllByStudentIdAndStatus(10L, EvaluationStatus.EVALUATED))
                .thenReturn(List.of());

        float averageScore = studentService.getAvgStudentScore(1L);

        assertEquals(0.0f, averageScore);
    }
}
