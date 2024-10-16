package com.sparta.tse.domain.file.repository;

import com.sparta.tse.domain.file.entity.File;
import com.sparta.tse.domain.file.enums.FileEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FileRepository extends JpaRepository<File, Long> {

    List<File> findBySourceIdAndFileFolder(Long sourceId, FileEnum fileFolder);

    void deleteBySourceIdAndFileFolder(Long sourceId, FileEnum fileFolder);
//
//    void findByItemId(Long reviewId);
}
