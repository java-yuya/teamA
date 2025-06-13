package com.example.music_management.viewmodel;

import lombok.Data;

//import java.time.LocalDate;

@Data
public class AlbumViewModel {
    private long albumId;
    private String title;
    private String artist;
    private int releaseDate;
    private int musicCount;
}