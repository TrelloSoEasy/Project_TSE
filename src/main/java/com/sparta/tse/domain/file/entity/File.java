package com.sparta.tse.domain.file.entity;

import com.sparta.tse.domain.file.enums.FileEnum;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "files")
@NoArgsConstructor
public class File {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long filesId;


    @Column(name = "file_url", nullable = false, length = 300)
    private String fileUrl;

    @Column(name = "source_id", nullable = false)
    private Long sourceId;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private FileEnum fileFolder;

    private File(String fileUrl, Long sourceId, FileEnum fileFolder) {
        this.fileFolder = fileFolder;
        this.fileUrl = fileUrl;
        this.sourceId = sourceId;

    }

    public static File of(String fileUrl, Long sourceId, FileEnum fileFolder) {
        return new File( fileUrl, sourceId, fileFolder);
    }

}
