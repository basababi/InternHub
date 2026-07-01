package mn.internhub.demo.api.dto;

import mn.internhub.demo.data.InternshipPost;
import mn.internhub.demo.data.User;
import mn.internhub.demo.service.PostingApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/postings")
public class PostingApi {
    @Autowired
    private PostingApiService postingApiService;

    //дадлагын ажилын заруудийг авах
    @GetMapping
    public InternshipPost getInternshipPosts(){
        return postingApiService.getInternshipPosts();
    }
    //тухайн нэг зарын дэлгэрэнгүйг харх/авах
    @GetMapping("/{id}")
    public InternshipPost getInternshipPostDetail(@PathVariable Long postId){
        return postingApiService.getInternshipPostDetail(postId);

    }


}
