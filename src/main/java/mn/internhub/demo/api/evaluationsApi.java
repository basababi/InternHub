package mn.internhub.demo.api;

import mn.internhub.demo.api.dto.evaluationApiDto.RequestEvaluation;
import mn.internhub.demo.data.Evaluation;
import mn.internhub.demo.data.User;
import mn.internhub.demo.service.EvaluationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/evaluations")
public class evaluationsApi {
    @Autowired
    private EvaluationService evaluationService;

    //тухайн байгууллаг нь өөр дээр нь дадлга хийсэн сурагчийн үнэлэх
    @PostMapping
    public Evaluation createEvaluation(@AuthenticationPrincipal User user, @RequestBody RequestEvaluation request){
        return evaluationService.createEvaluation(user.getUserId(), request);
    }
}
