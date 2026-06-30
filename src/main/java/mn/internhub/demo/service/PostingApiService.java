package mn.internhub.demo.service;

import mn.internhub.demo.repository.OrganizationRepository;
import mn.internhub.demo.repository.PostingApiRepository;
import mn.internhub.demo.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostingApiService {
    @Autowired
    private PostingApiRepository postingApiRepository;
    @Autowired
    private OrganizationRepository organizationRepository;
    @Autowired
    private StudentRepository studentRepository;
}
