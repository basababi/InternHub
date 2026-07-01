package mn.internhub.demo.service;

import mn.internhub.demo.api.dto.postingApiDto.ResponsePubPosts;
import mn.internhub.demo.data.InternshipPost;
import mn.internhub.demo.data.Organizations;
import mn.internhub.demo.repository.InternshipPostRepository;
import mn.internhub.demo.repository.OrganizationRepository;
import mn.internhub.demo.repository.StudentRepository;
import mn.internhub.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class PostingApiService {
    @Autowired
    private InternshipPostRepository internshipPostRepository;
    @Autowired
    private OrganizationRepository organizationRepository;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private UserRepository userRepository;
    //олон нийт оруулсан байгаа бүх зарыг харах
    public List<ResponsePubPosts> getInternshipPosts() {
        List<InternshipPost> posts = internshipPostRepository.findAll();
        return posts.stream()
                .map(post ->{
                    Organizations org = organizationRepository.findById(post.getOrganizationId()).orElseThrow(IllegalStateException::new);
                    return ResponsePubPosts.builder()
                            .internshipPostId(post.getInternshipPostId())
                            .organizationId(post.getOrganizationId())
                            .organizationName(org.getOrganizationName())
                            .industry(org.getIndustry())
                            .title(post.getTitle())
                            .city(org.getCity())
                            .salaryMin(post.getSalaryMin())
                            .salaryMax(post.getSalaryMax())
                            .requiredMajor(post.getRequiredMajors())
                            .build();
                })
                .toList();
    }

    //олон нийт тухайн зарын дэлгэрэнгүйг харах
    public InternshipPost getInternshipPostDetail(Long postId) {
        boolean postExist = internshipPostRepository.existsById(postId);
        if (!postExist){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"post doesn't found");
        }
        return internshipPostRepository.findById(postId).orElseThrow(IllegalAccessError::new);
    }
    //Helper function
    public void isOrg(Long userId){
        boolean isOrg = organizationRepository.existsByUserId(userId);
        if(!isOrg){
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE,"Not acceptable");
        }
    }

}
