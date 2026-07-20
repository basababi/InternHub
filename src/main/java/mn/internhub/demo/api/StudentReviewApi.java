package mn.internhub.demo.api;

import mn.internhub.demo.api.dto.studentReviewApiDto.requestStudentReview;
import mn.internhub.demo.data.StudentReview;
import mn.internhub.demo.data.User;
import mn.internhub.demo.service.StudentReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/studentReview")
public class StudentReviewApi {
    @Autowired
    private StudentReviewService studentReviewService;

    @PostMapping("/{studentId}")
    public StudentReview postStudentReview(@AuthenticationPrincipal User user, @PathVariable Long studentId, @RequestBody requestStudentReview request){
        return studentReviewService.postStudentReview(user.getUserId(), studentId, request);
    }
}
