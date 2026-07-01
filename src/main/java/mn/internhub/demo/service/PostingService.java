package mn.internhub.demo.service;

import jakarta.validation.Valid;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import mn.internhub.demo.api.dto.postingApiDto.RequestCreatePost;
import mn.internhub.demo.api.dto.postingApiDto.ResponsePubPosts;
import mn.internhub.demo.data.InternshipPost;
import mn.internhub.demo.data.Organizations;
import mn.internhub.demo.data.PostViews;
import mn.internhub.demo.data.enums.Status;
import mn.internhub.demo.repository.InternshipPostRepository;
import mn.internhub.demo.repository.OrganizationRepository;
import mn.internhub.demo.repository.StudentRepository;
import mn.internhub.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
public class PostingService {
    @Autowired
    private InternshipPostRepository internshipPostRepository;
    @Autowired
    private OrganizationRepository organizationRepository;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PostViewsRepository postViewsRepository;

    //олон нийт оруулсан байгаа бүх зарыг харах
    public List<ResponsePubPosts> getInternshipPosts() {
        List<InternshipPost> posts = internshipPostRepository.findAll();
        return posts.stream()
                .map(post ->{
                    Organizations org = organizationRepository.findById(post.getOrganizationId()).orElseThrow(IllegalStateException::new);
                    PostViews postView = postViewsRepository.findByInternshipPostId(post.getInternshipPostId());
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
                            .viewCount(postView.getViewCount())
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
    //байгууллаг нь зар оруулах
    public InternshipPost createPost(Long userId, @Valid RequestCreatePost request) {
        isOrg(userId);
        log.info("хэрэглэгчээ шалгаад алдаагүй ");
        Long orgId = organizationRepository.findByUserId(userId).getOrganizationId();
        log.info("тухайн хэрэглэгчийн байгууллагын id авсан");
        InternshipPost createpost = InternshipPost.builder()
                .organizationId(orgId)
                .title(request.title())
                .description(request.description())
                .requiredMajors(request.requiredMajors())
                .minGpa(request.minGpa())
                .requiredSkills(request.requiredSkills())
                .salaryMin(request.salaryMin())
                .salaryMax(request.salaryMax())
                .isSalaryUnspecified(request.isSalaryUnspecified())
                .vacancyCount(request.vacancyCount())
                .deadline(request.deadline())
                .status(Status.PENDING)
                .createdAt(LocalDateTime.now())
                .build();
        log.info("post-оо амжилттай үүсгэсэн");
        internshipPostRepository.save(createpost);
        PostViews postViews = PostViews.builder()
                .internshipPostId(createpost.getInternshipPostId())
                .viewCount(0)
                .createdAt(LocalDateTime.now())
                .build();
        postViewsRepository.save(postViews);

        log.info("post-оо амжилттай хадгалсан");
        return createpost;
    }
    //Helper function
    public void isOrg(Long userId){
        boolean isOrg = organizationRepository.existsByUserId(userId);
        if(!isOrg){
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE,"Not acceptable");
        }
    }


}
