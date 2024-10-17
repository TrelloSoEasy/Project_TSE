package com.sparta.tse.domain.file.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.sparta.tse.common.entity.ApiResponse;
import com.sparta.tse.common.entity.ErrorStatus;
import com.sparta.tse.common.exception.ApiException;
import com.sparta.tse.config.AuthUser;
import com.sparta.tse.domain.card.entity.Card;
import com.sparta.tse.domain.card.repository.CardRepository;
import com.sparta.tse.domain.file.entity.File;
import com.sparta.tse.domain.file.enums.FileEnum;
import com.sparta.tse.domain.file.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class FileService {

    private final AmazonS3 amazonS3;
    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;


    private final FileRepository fileRepository;
    private final CardRepository cardRepository;


    // 기존의 단일 파일 업로드 메서드
    public File uploadFiles(Long sourceId, List<MultipartFile> files, FileEnum fileFolder) throws IOException {

        // 지원되는 파일 확장자 리스트
        List<String> allowedFileTypes = Arrays.asList("image/jpeg", "image/png", "application/pdf", "text/csv");
        // 최대 파일 크기: 5MB
        long maxFileSize = 5 * 1024 * 1024;

        for (MultipartFile file : files) {


            if (file.getSize() > maxFileSize) {
                throw new ApiException(ErrorStatus._FILE_SIZE_OVER_ERROR);
            }

            // 파일 형식 체크
            if (!allowedFileTypes.contains(file.getContentType())) {
                throw new ApiException(ErrorStatus._FILE_TYPE_MISS_MATCH);
            }

            String fileName = generateFileName(file);
            String fileKey = fileFolder + "/" + fileName;

            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(file.getSize());
            metadata.setContentType(file.getContentType());

            try (InputStream inputStream = file.getInputStream()) {
                PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, fileKey, inputStream, metadata);
                amazonS3.putObject(putObjectRequest);
            }
            String url = amazonS3.getUrl(bucketName, fileKey).toString();
            File image = File.of(url, sourceId, fileFolder);
            fileRepository.save(image);
        }
        return null;
    }


    private String generateFileName(MultipartFile file) {
        return UUID.randomUUID().toString() + "-" + file.getOriginalFilename().replace(" ", "_");
    }

    @Transactional
    public ApiResponse deleteImages(Long fileId, AuthUser authUser, Long cardId) {
        cardRepository.findById(cardId).orElseThrow(() -> new ApiException(ErrorStatus._NOT_FOUND_CARD));
        fileRepository.findById(fileId).orElseThrow(() -> new NullPointerException("사진을 찾을 수 없습니다."));
        fileRepository.deleteBySourceIdAndFileFolder(cardId, FileEnum.CARD);
        return new ApiResponse("삭제 성공", HttpStatus.OK.value(), null);
    }

}

