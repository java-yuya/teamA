package com.example.music_management.form;

import lombok.Data;

import java.time.LocalDate;
// import java.time.LocalTime;

@Data
public class MusicForm {
    private String title;
    private LocalDate duration;
    private int price;
    private int method;
    private long albumId;
}