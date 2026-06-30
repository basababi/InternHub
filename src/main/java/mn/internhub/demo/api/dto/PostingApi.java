package mn.internhub.demo.api.dto;

import mn.internhub.demo.service.PostingApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/postings")
public class PostingApi {
    @Autowired
    private PostingApiService postingApiService;


}
