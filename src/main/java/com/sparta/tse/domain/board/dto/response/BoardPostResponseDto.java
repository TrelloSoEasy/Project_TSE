package com.sparta.tse.domain.board.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sparta.tse.domain.file.dto.ImagesResponseDto;
import com.sparta.tse.domain.file.entity.File;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BoardPostResponseDto {
    private String boardName;
    private String boardBackgroundColor;
    private List<ImagesResponseDto> image;

    public BoardPostResponseDto(String boardName) {
        this.boardName = boardName;
    }


    public void addBoardBackgroundColor(String boardBackgroundColor) {
        this.boardBackgroundColor = boardBackgroundColor;
    }

    public void addBoardBackgroundImage(List<File> imageList) {
        this.image = imageList.stream().map(ImagesResponseDto::of).collect(Collectors.toList());

    }
}
