package mn.internhub.demo.api;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import mn.internhub.demo.api.dto.postingApiDto.RequestCreatePost;
import mn.internhub.demo.api.dto.postingApiDto.ResponsePubPosts;
import mn.internhub.demo.data.InternshipPost;
import mn.internhub.demo.data.User;
import mn.internhub.demo.service.PostingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/postings")
public class PostingApi {
    @Autowired
    private PostingService postingService;

    //дадлагын ажилын заруудийг авах
    @GetMapping
    public List<ResponsePubPosts> getInternshipPosts(){
        return postingService.getInternshipPosts();
    }
    //тухайн нэг зарын дэлгэрэнгүйг харх/авах
    @GetMapping("/{postId}")
    public InternshipPost getInternshipPostDetail(@PathVariable Long postId){
        return postingService.getInternshipPostDetail(postId);
    }
    //байгууллаг нь зар оруулах
    @PostMapping
    public InternshipPost createPost(@AuthenticationPrincipal User user, @Valid @RequestBody RequestCreatePost request) {
        log.info("мэдээлэлээ амжилттай авсан");
        return postingService.createPost(user.getUserId(),request);
    }


}
