package mn.internhub.demo.service;

import mn.internhub.demo.data.InternshipPost;
import mn.internhub.demo.data.User;
import mn.internhub.demo.repository.OrganizationRepository;
import mn.internhub.demo.repository.PostingApiRepository;
import mn.internhub.demo.repository.StudentRepository;
import mn.internhub.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class PostingApiService {
    @Autowired
    private PostingApiRepository postingApiRepository;
    @Autowired
    private OrganizationRepository organizationRepository;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private UserRepository userRepository;
    //олон нийт оруулсан байгаа бүх зарыг харах
    public InternshipPost getInternshipPosts() {
        return null;
    }

    //олон нийт тухайн зарын дэлгэрэнгүйг харах
    public InternshipPost getInternshipPostDetail(Long postId) {
        return null;
    }
    //Helper function
    public void isOrg(Long userId){
        boolean isOrg = organizationRepository.existsByUserId(userId);
        if(!isOrg){
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE,"Not acceptable");
        }
    }

}
