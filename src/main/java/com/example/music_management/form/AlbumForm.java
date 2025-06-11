package com.example.music_management.form;

import lombok.Data;
import java.time.LocalDate;

@Data
public class AlbumForm {
    //入力された値を格納する変数
    private String title;
    private String artist;
    private LocalDate releaseDate;
}
