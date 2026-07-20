package mn.internhub.demo.api;

import mn.internhub.demo.api.dto.studentReviewApiDto.ResponseStudentReview;
import mn.internhub.demo.api.dto.studentReviewApiDto.requestStudentReview;
import mn.internhub.demo.data.StudentReview;
import mn.internhub.demo.data.User;
import mn.internhub.demo.service.StudentReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/studentReview")
public class StudentReviewApi {
    @Autowired
    private StudentReviewService studentReviewService;

    //тухайн нэг сурагч дээр сэтгэгдэл үлдээх
    @PostMapping("/{studentId}")
    public StudentReview postStudentReview(@AuthenticationPrincipal User user, @PathVariable Long studentId, @RequestBody requestStudentReview request){
        return studentReviewService.postStudentReview(user.getUserId(), studentId, request);
    }
    //тухайн нэг сурагч дээр ирсэн сэтгэгдэлийг авах
    @GetMapping("/{studentId}")
    public List<ResponseStudentReview> getStudentReviews(@AuthenticationPrincipal User user, @PathVariable Long studentId){
        return studentReviewService.getStudentReviews(user.getUserId(), studentId);
    }
    //тухайн нэг сурагч дээр сэтгэгдэлийн голчыг гаргах
    @GetMapping("/avg/{studentId}")
    public double getAvgScore(@AuthenticationPrincipal User user,@PathVariable Long studentId){
        return studentReviewService.getAvgScore(studentId);
    }

}
