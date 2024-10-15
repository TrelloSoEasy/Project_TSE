package com.sparta.tse.domain.image.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "images")
@NoArgsConstructor
public class Images {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ImagesId;

    @Column(name = "image_folder", nullable = false, length = 50)
    private String ImageFolder;

    @Column(name = "image_url", nullable = false, length = 300)
    private String ImageUrl;

    @Column(name = "source_id", nullable = false)
    private Long sourceId;




}
