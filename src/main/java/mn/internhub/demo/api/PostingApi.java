package mn.internhub.demo.api;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import mn.internhub.demo.api.dto.postingApiDto.RequestCreatePost;
import mn.internhub.demo.api.dto.postingApiDto.RequestUpdatePost;
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
    //оруулсан буй бүр зарын тоог авах
    @GetMapping("/All")
    public Long getAllPostNumber(){
        return postingService.getAllPostNumber();
    }
    //тухайн нэг зарын дэлгэрэнгүйг харх/авах
    @GetMapping("/{postId}")
    public InternshipPost getInternshipPostDetail(@PathVariable Long postId){
        log.info("мэдээлэлээ амжилттай авсан");
        return postingService.getInternshipPostDetail(postId);
    }
    //байгууллаг нь зар оруулах
    @PostMapping
    public InternshipPost createPost(@AuthenticationPrincipal User user, @Valid @RequestBody RequestCreatePost request) {
        log.info("мэдээлэлээ амжилттай авсан");
        return postingService.createPost(user.getUserId(),request);
    }
    //байгууллаг нь оруулсан зараа өөрчилж шинчлэлт оруулах
    @PutMapping("/{postId}")
    public InternshipPost updatePost(@AuthenticationPrincipal User user, @Valid @PathVariable Long postId, @Valid @RequestBody RequestUpdatePost request){
        return postingService.updatePost(user.getUserId(), postId, request);
    }
    //байгууллаг нь өөрсдийн оруулсан зараа устгах
    @DeleteMapping("/{postId}")
    public void deletePost(@AuthenticationPrincipal User user, @PathVariable Long postId){
        postingService.deletePost(user.getUserId(), postId);
    }
    //байгууддаг нь өөрсдийг оруулсан зараа харах
    @GetMapping("/my")
    public List<InternshipPost> getMyPosts(@AuthenticationPrincipal User user){
        return postingService.getMyPosts(user.getUserId());
    }


}
