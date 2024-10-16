package com.sparta.tse.domain.file.controller;

import com.sparta.tse.common.entity.ApiResponse;
import com.sparta.tse.config.AuthUser;
import com.sparta.tse.domain.file.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tse")
public class FileController {

    private final FileService fileService;
    @DeleteMapping("/files/{fileId}/source/{sourceId}")
    public ApiResponse deleteFiles (@AuthenticationPrincipal AuthUser authUser,
                                    @PathVariable Long sourceId,
                                    @PathVariable Long fileId) {
        return fileService.deleteImages(fileId, authUser, sourceId);
    }
}
