package com.example.music_management.entity;

import lombok.Data;
//import org.springframework.format.annotation.DateTimeFormat;

//import java.time.LocalDate;
import java.time.LocalDateTime;
//Lombokを使ったGetter や　Setter を自動生成するためのアノテーション
@Data
public class Album {
    private long albumId;//口座ID
    private String title;//口座名
    private int artist;//カテゴリ判別
    private int releaseDate;//金額
    private long userId;//ユーザーID
    private LocalDateTime createdAt;//作成日時
}
//Mybatis を使ってデータベースとやり取りするAlbumMapperクラスへ
