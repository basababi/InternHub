package mn.internhub.demo.service;

import lombok.extern.slf4j.Slf4j;
import mn.internhub.demo.data.FileEntity;
import mn.internhub.demo.data.User;
import mn.internhub.demo.data.enums.ContentTypes;
import mn.internhub.demo.repository.FileEntityRepository;
import mn.internhub.demo.service.helperFunctions.gimmeId;
import mn.internhub.demo.service.helperFunctions.isItExist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.rmi.RemoteException;

@Slf4j
@Service
public class FileEntityService {
    @Autowired
    private FileEntityRepository fileRepository;
    @Autowired
    private isItExist isItExist;
    @Autowired
    private gimmeId gimmeId;

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
    public Boolean updateCv(Long userId, MultipartFile file){
        if (!file.isEmpty()){
            FileEntity cv = fileRepository.findByUserId(userId);
            cv.setFileName(file.getOriginalFilename());
            cv.setFileType(file.getContentType());
            try {
                cv.setData(file.getBytes());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            fileRepository.save(cv);
            return true;
        }
        return false;
    }
    //өөрйин cv файлыг харах авах
    public FileEntity getCv(User user) {
        isItExist.isStudentByUserId(user.getUserId());
        boolean isOwner = user.getUserId().equals(fileRepository.findByUserId(user.getUserId()).getUserId());
        if (!isOwner){
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE,"чиний файл биш байна");
        }
        return fileRepository.findByUserIdAndContentTypes(user.getUserId(),ContentTypes.CV);
    }

    public ResponseEntity<String> createProfileImg(Long userId, MultipartFile file) {
        isItExist.isStudentByUserId(userId);
        if (file.isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"emptyy");
        }
        try {
            FileEntity proImg = FileEntity.builder()
                    .userId(userId)
                    .fileName(file.getOriginalFilename())
                    .fileType(file.getContentType())
                    .contentTypes(ContentTypes.PROFILE)
                    .data(file.getBytes())
                    .build();
            fileRepository.save(proImg);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok("ажмилттай");
    }
    //өөрйин profile зургыг авах
    public FileEntity getProfileImg(Long userId) {
        isItExist.isStudentByUserId(userId);
        return fileRepository.findByUserIdAndContentTypes(userId, ContentTypes.PROFILE);
    }

}
