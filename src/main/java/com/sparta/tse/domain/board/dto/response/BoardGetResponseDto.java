package com.sparta.tse.domain.board.dto.response;

import com.sparta.tse.domain.file.dto.ImagesResponseDto;
import com.sparta.tse.domain.file.entity.File;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class BoardGetResponseDto {
    private List<ListDto> Lists;
    private List<ImagesResponseDto> responseDtoList;

    public BoardGetResponseDto(List<ListDto> listDtos, List<File> image) {
        this.Lists = listDtos;
        this.responseDtoList = image.stream()
                .map(ImagesResponseDto::of)
                .collect(Collectors.toList());
    }

}
