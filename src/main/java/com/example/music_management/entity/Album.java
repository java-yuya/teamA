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
    private String artist;
    //日付型のフォーマットを指定yyyy-MM-dd にしておくことで <form> タグの type 属性 date とフォーマットを統一
    //@DateTimeFormat(pattern = "yyyy-MM-dd")
    private int releaseDate;
    //Mybatis の自動変換で利用するため、各フィールド名は albums テーブルのカラム名をキャメルケースに書き直した名前にします
    private LocalDateTime createdAt;
}
//Mybatis を使ってデータベースとやり取りするAlbumMapperクラスへ
