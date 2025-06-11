package com.example.music_management.form;

import lombok.Data;
import java.time.LocalDate;

//Lombokを使ったGetterやSetterを自動生成するためのアノテーション
@Data 
//入力を受け付ける項目
public class AlbumForm {
    //入力された値を格納する変数
    private String title;
    private String artist;
    private LocalDate releaseDate;
}
