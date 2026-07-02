package mn.internhub.demo.service;

import mn.internhub.demo.data.metaData.Anonymous;
import mn.internhub.demo.repository.AnonymousRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AnonymousService {
    @Autowired
    private AnonymousRepository anonymousRepository;

    public long getSequence(){
        Anonymous anonymous = anonymousRepository.findById(123L).orElseGet(() -> {
            Anonymous newAnonymous = Anonymous.builder()
                    .anonymousID(123L)
                    .lastSequence(10000L)
                    .build();

            return anonymousRepository.save(newAnonymous);
        });
        Long sequence = anonymous.getLastSequence();
        sequence= sequence+1;
        anonymous.setAnonymousID(sequence);
        return anonymous.getLastSequence();

    }
}
