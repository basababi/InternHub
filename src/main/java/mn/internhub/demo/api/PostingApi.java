package mn.internhub.demo.api;

import mn.internhub.demo.api.dto.postingApiDto.ResponsePubPosts;
import mn.internhub.demo.data.InternshipPost;
import mn.internhub.demo.service.PostingApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/postings")
public class PostingApi {
    @Autowired
    private PostingApiService postingApiService;

    //дадлагын ажилын заруудийг авах
    @GetMapping
    public List<ResponsePubPosts> getInternshipPosts(){
        return postingApiService.getInternshipPosts();
    }
    //тухайн нэг зарын дэлгэрэнгүйг харх/авах
    @GetMapping("/{id}")
    public InternshipPost getInternshipPostDetail(@PathVariable Long postId){
        return postingApiService.getInternshipPostDetail(postId);
    }


}
