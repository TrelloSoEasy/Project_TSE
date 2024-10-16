package com.sparta.tse.domain.file.dto;

import com.sparta.tse.domain.file.entity.File;
import com.sparta.tse.domain.file.enums.FileEnum;
import lombok.Getter;

@Getter
public class ImagesResponseDto {

    private final Long imageId;
    private final Long sourceId;
    private final FileEnum imageFolder;
    private final String imageUrl;



    private ImagesResponseDto(Long imagesId, Long sourceId, String imageUrl, FileEnum imageFolder) {
        this.imageId = imagesId;
        this.sourceId = sourceId;
        this.imageUrl = imageUrl;
        this.imageFolder = imageFolder;
    }

    public static ImagesResponseDto of(File file) {
        return new ImagesResponseDto(
                file.getFilesId(),
                file.getSourceId(),
                file.getFileUrl(),
                file.getFileFolder()
        );
    }
}
