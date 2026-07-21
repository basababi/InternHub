package mn.internhub.demo.service;

import lombok.extern.slf4j.Slf4j;
import mn.internhub.demo.data.FileEntity;
import mn.internhub.demo.data.Report;
import mn.internhub.demo.data.User;
import mn.internhub.demo.data.enums.ContentTypes;
import mn.internhub.demo.repository.FileEntityRepository;
import mn.internhub.demo.repository.ReportRepository;
import mn.internhub.demo.service.helperFunctions.gimmeId;
import mn.internhub.demo.service.helperFunctions.isItExist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;


@Slf4j
@Service
public class FileEntityService {
    @Autowired
    private FileEntityRepository fileRepository;
    @Autowired
    private isItExist isItExist;
    @Autowired
    private gimmeId gimmeId;
    @Autowired
    private ReportRepository reportRepository;

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
            FileEntity cv = fileRepository.findByUserIdAndContentTypes(userId, ContentTypes.CV)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "CV файл олдсонгүй"));
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
        FileEntity cv = fileRepository.findByUserIdAndContentTypes(user.getUserId(), ContentTypes.CV)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "CV файл олдсонгүй"));
        boolean isOwner = user.getUserId().equals(cv.getUserId());
        if (!isOwner){
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE,"чиний файл биш байна");
        }
        return cv;
    }

    public ResponseEntity<String> createProfileImg(Long userId, MultipartFile file, ContentTypes contentTypes) {
        isItExist.isStudentByUserId(userId);
        if (file.isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"emptyy");
        }
        try {
            FileEntity proImg = FileEntity.builder()
                    .userId(userId)
                    .fileName(file.getOriginalFilename())
                    .fileType(file.getContentType())
                    .contentTypes(contentTypes)
                    .data(file.getBytes())
                    .build();
            fileRepository.save(proImg);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok("ажмилттай");
    }
    //өөрйин profile зургыг авах
    public FileEntity getProfileImg(Long userId, ContentTypes contentTypes) {
        isItExist.isStudentByUserId(userId);
        return fileRepository.findByUserIdAndContentTypes(userId, contentTypes).orElse(null);
    }
    //update own profile image
    public FileEntity updateProfileImg(Long userId, MultipartFile file, ContentTypes contentTypes) {
        isItExist.isStudentByUserId(userId);
        if(file.isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"emptyyyyy");
        }
        log.info("энэ хүртэл асуудалгүй");
        FileEntity proImg = fileRepository.findByUserIdAndContentTypes(userId,contentTypes).orElse(null);
        proImg.setFileName(file.getOriginalFilename());
        proImg.setFileType(file.getContentType());
        try {
            proImg.setData(file.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        fileRepository.save(proImg);
        return proImg;
    }

    public ResponseEntity<String> createReportFile(
            Long userId,
            MultipartFile file,
            ContentTypes contentTypes,
            Long reportId) {

        isItExist.isStudentByUserId(userId);

        Report report = reportRepository.findById(reportId)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Report байхгүй байна"));

        if (!gimmeId.userIdToStudentId(userId).equals(report.getStudentId())) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "Энэ report таны биш");
        }

        if (file.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Файл хоосон байна");
        }

        try {
            FileEntity entity = FileEntity.builder()
                    .userId(userId)
                    .reportId(reportId)
                    .fileName(file.getOriginalFilename())
                    .fileType(file.getContentType())
                    .contentTypes(contentTypes)
                    .data(file.getBytes())
                    .build();

            fileRepository.save(entity);

        } catch (IOException e) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Файл хадгалж чадсангүй",
                    e);
        }

        return ResponseEntity.ok("Амжилттай");
    }

    public ResponseEntity<String> updateFile(Long userId, Long reportId, MultipartFile file) {
        isItExist.isStudentByUserId(userId);
        boolean isExist = reportRepository.existsById(reportId);
        if (!isExist){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"байхгүй байна");
        }
        log.info("дада 2");
        boolean isOwner = gimmeId.userIdToStudentId(userId).equals(reportRepository.findById(reportId).orElseThrow(IllegalAccessError::new).getStudentId());
        if (!isOwner){
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE,"хэн юм бэээ чи");
        }
        if (file.isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"emptyy");
        }
        FileEntity fileEntity = fileRepository.findByReportIdAndContentTypes(reportId, ContentTypes.REPORT);
        try {
            fileEntity.setData(file.getBytes());
            fileEntity.setFileName(fileEntity.getFileName());
            fileEntity.setFileType(file.getContentType());
            fileEntity.setReportId(reportId);

            fileRepository.save(fileEntity);
        }
        catch (IOException e) {
        throw new RuntimeException(e);

    }
        return ResponseEntity.ok("ажмилттай");


    }
}
