package mn.internhub.demo.service;

import mn.internhub.demo.data.FileEntity;
import mn.internhub.demo.data.User;
import mn.internhub.demo.data.enums.ContentTypes;
import mn.internhub.demo.repository.FileEntityRepository;
import mn.internhub.demo.service.helperFunctions.isItExist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.rmi.RemoteException;

@Service
public class FileEntityService {
    @Autowired
    private FileEntityRepository fileRepository;
    @Autowired
    private isItExist isItExist;

    //сурагч нь cv файлаа илгээнээ
    public void createCV(User user, MultipartFile file) throws IOException {
        if (file.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "empty");
        }
        isItExist.isStudentByUserId(user.getUserId());
        FileEntity fileVar = FileEntity.builder()
                .userId(user.getUserId())
                .fileName(file.getOriginalFilename())
                .fileType(file.getContentType())
                .contentTypes(ContentTypes.CV)
                .data(file.getBytes())
                .build();
        fileRepository.save(fileVar);
    }
    //сурагч нь cv-файлаа солих өөрчлөх
}
