package mn.internhub.demo.api;

import mn.internhub.demo.api.dto.evaluationApiDto.RequestEvaluation;
import mn.internhub.demo.api.dto.evaluationApiDto.ResponseEvaluation;
import mn.internhub.demo.api.dto.evaluationApiDto.ResponseGetAppEva;
import mn.internhub.demo.api.dto.evaluationApiDto.updateEvaluation;
import mn.internhub.demo.data.Evaluation;
import mn.internhub.demo.data.User;
import mn.internhub.demo.service.EvaluationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/evaluations")
public class EvaluationsApi {
    @Autowired
    private EvaluationService evaluationService;

    //тухайн байгууллаг нь өөр дээр нь дадлга хийсэн сурагчийн үнэлэх
    @PostMapping("/{appId}")
    public Evaluation createEvaluation(@AuthenticationPrincipal User user,@PathVariable long appId, @RequestBody RequestEvaluation request){
        return evaluationService.createEvaluation(user.getUserId(), appId ,request);
    }
    //тухайн байгууллаг нь өөр дээр нь дадлага хийж байгаа сурагчдаа үнэлэх жагсаалт авах
    @GetMapping
    public List<ResponseGetAppEva> getAllAppEva(@AuthenticationPrincipal User user){
        return evaluationService.getAllAppEva(user.getUserId());
    }
    //тухайн байгуулга нь үнэлсэн сурагчийн үнэлгээг өөрчлөх
    @PutMapping("/{evaId}")
    public Evaluation updateEvaluation(@AuthenticationPrincipal User user, @PathVariable long evaId,@RequestBody updateEvaluation request){
        return evaluationService.updateEvaluation(user.getUserId(),evaId,request);
    }
    //сурагч болон багш нь үнэлгээг нь дэлгэрэнгүй харах
    @GetMapping("/student/{evaId}")
    public Evaluation getEvaluationById(@AuthenticationPrincipal User user, @PathVariable Long evaId){
        return evaluationService.getEvaluationById(user.getUserId(), evaId);
    }
    //сурагч нь өөрийн бүх үнэлгээг ерөнхий байдлаар харах
    @GetMapping("/student")
    public List<ResponseEvaluation> getAllEvaluation(@AuthenticationPrincipal User user){
        return evaluationService.getAllEvaluation(user.getUserId());
    }
    //байгууллаг нь үнэлгээний дундажийг нь авах
    @GetMapping("/avg/{orgId}")
    public float getAvgScore(@PathVariable Long orgId){
        return evaluationService.getAvgScore(orgId);
    }



}
