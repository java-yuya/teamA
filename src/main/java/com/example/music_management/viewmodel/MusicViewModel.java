package com.example.music_management.viewmodel;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
// import java.time.LocalTime;

@Data
public class MusicViewModel {
    private long musicId;
    private String title;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate duration;
    private int price;
    private String method;
    private boolean isFavorite;
}