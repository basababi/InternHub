package mn.internhub.demo.service;

import jakarta.validation.Valid;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import mn.internhub.demo.api.dto.postingApiDto.RequestCreatePost;
import mn.internhub.demo.api.dto.postingApiDto.RequestUpdatePost;
import mn.internhub.demo.api.dto.postingApiDto.ResponsePubPosts;
import mn.internhub.demo.data.InternshipPost;
import mn.internhub.demo.data.Organizations;
import mn.internhub.demo.data.PostViews;
import mn.internhub.demo.data.enums.PostStatus;
import mn.internhub.demo.data.enums.Status;
import mn.internhub.demo.repository.InternshipPostRepository;
import mn.internhub.demo.repository.OrganizationRepository;
import mn.internhub.demo.repository.StudentRepository;
import mn.internhub.demo.repository.UserRepository;
import mn.internhub.demo.service.helperFunctions.isItExist;
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
    @Autowired
    private isItExist isItExist;

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
                            .status(post.getStatus())
                            .createdAt(post.getCreatedAt())
                            .updatedAt(post.getUpdatedAt())
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
        log.info("post шалгасан байна");
        PostViews postViews = postViewsRepository.findByInternshipPostId(postId);
        log.info("postID гаар нь үзэлтйг нь авсан");
        Integer viewCount = postViews.getViewCount()+1;
        log.info("Нэмсэн");
        postViews.setViewCount(viewCount);
        log.info("Хадгалсан");
        postViewsRepository.save(postViews);

        return internshipPostRepository.findById(postId).orElseThrow(IllegalAccessError::new);
    }
    //байгууллаг нь зар оруулах
    public InternshipPost createPost(Long userId, @Valid RequestCreatePost request) {
        isItExist.isOrgByUserId(userId);
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
                .status(PostStatus.OPEN)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        log.info("post-оо амжилттай үүсгэсэн");
        internshipPostRepository.save(createpost);
        log.info("post-оо амжилттай хадгаллаа");
        PostViews postViews = PostViews.builder()
                .internshipPostId(createpost.getInternshipPostId())
                .viewCount(0)
                .createdAt(LocalDateTime.now())
                .build();
        log.info("postView-оо амжилттай үүсгэсэн");
        postViewsRepository.save(postViews);
        log.info("postView-оо амжилттай хадгаллаа");
        return createpost;
    }
    //байгууллага нь өөрийн оруулсан зарыг өөрчлөх
    public InternshipPost updatePost(Long userId, @Valid Long postId, @Valid RequestUpdatePost request) {
        isItExist.isOrgByUserId(userId);
        boolean isExistPost = internshipPostRepository.existsById(postId);
        if (!isExistPost){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"post doesn't found");
        }
        Long orgId = organizationRepository.findByUserId(userId).getOrganizationId();
        InternshipPost post = internshipPostRepository.findById(postId).orElseThrow(IllegalStateException::new);
        if (post.getOrganizationId() != orgId){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"bad request");
        }
        if (request.description() != null){
            post.setDescription(request.description());
        }
        if (request.requiredMajors() != null){
            post.setRequiredMajors(request.requiredMajors());
        }
        if (request.minGpa() != null){
            post.setMinGpa(request.minGpa());
        }
        if (request.requiredSkills() != null){
            post.setRequiredSkills(request.requiredSkills());
        }
        if (request.salaryMin() != null){
            post.setSalaryMin(request.salaryMin());
        }
        if (request.salaryMax() != null){
            post.setSalaryMax(request.salaryMax());
        }
        if (request.vacancyCount() != null){
            post.setVacancyCount(request.vacancyCount());
        }
        if (request.deadline() != null){
            post.setDeadline(request.deadline());
        }
        if (request.status() != null){
            post.setStatus(request.status());
        }
        post.setUpdatedAt(LocalDateTime.now());
        internshipPostRepository.save(post);
        return post;
    }
    //байгууллаг нь өөрийн оруулсан зараа устгах
    public void deletePost(Long userId, Long postId) {
        isItExist.isOrgByUserId(userId);
        boolean isExistPost = internshipPostRepository.existsById(postId);
        if (!isExistPost){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"post doesn't found");
        }
        Long orgId = organizationRepository.findByUserId(userId).getOrganizationId();
        InternshipPost post = internshipPostRepository.findById(postId).orElseThrow(IllegalStateException::new);
        if (post.getOrganizationId() != orgId){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"bad request");
        }
        internshipPostRepository.deleteById(postId);
    }
    //өөрийн хийсэн заруудаа харах
    public List<InternshipPost> getMyPosts(Long userId) {
        isItExist.isOrgByUserId(userId);
        Long orgId = organizationRepository.findByUserId(userId).getOrganizationId();
        return internshipPostRepository.findAllByOrganizationId(orgId);
    }
}
