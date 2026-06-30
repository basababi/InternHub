package mn.internhub.demo.service;

import lombok.AllArgsConstructor;
import mn.internhub.demo.api.ApplicationApi;
import mn.internhub.demo.api.dto.RequestApplication;
import mn.internhub.demo.data.Application;
import mn.internhub.demo.data.enums.PaymentStatus;
import mn.internhub.demo.data.enums.Status;
import mn.internhub.demo.repository.ApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ApplicationService {
    @Autowired
    private ApplicationRepository applicationRepository;

    public Application createApplication(RequestApplication request) {
        Application application = Application.builder()
                .studentId(request.studentId())
                .internshipPostId(request.internshipPostId())
                .status(Status.PENDING)
                .coverLetter(request.coverLetter())
                .paymentStatus(PaymentStatus.SUCCESS)
                .submittedAt(LocalDateTime.now())
                .build();
        return applicationRepository.save(application);
    }
}
