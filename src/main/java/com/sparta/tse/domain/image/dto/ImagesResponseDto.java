package com.sparta.tse.domain.image.dto;

import com.sparta.tse.domain.image.entity.Images;
import lombok.Getter;

@Getter
public class ImagesResponseDto {

    private Long imageId;
    private String imageUrl;
    private String imageFolder;
    private Long sourceId;

    public static ImagesResponseDto of(Images images) {
        return new ImagesResponseDto();
    }
}
