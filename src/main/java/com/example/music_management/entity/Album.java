package com.example.music_management.entity;

import lombok.Data;
//import org.springframework.format.annotation.DateTimeFormat;

//import java.time.LocalDate;
import java.time.LocalDateTime;
//Lombokを使ったGetter や　Setter を自動生成するためのアノテーション
@Data
public class Album {
    private long albumId;
    private String title;
    private int artist;
    private int releaseDate;
    private long userId;
    private LocalDateTime createdAt;
}
//Mybatis を使ってデータベースとやり取りするAlbumMapperクラスへ
