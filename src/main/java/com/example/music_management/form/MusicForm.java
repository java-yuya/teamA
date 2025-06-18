package com.example.music_management.form;

import java.time.LocalDate;

import lombok.Data;

//import java.time.LocalDate;
// import java.time.LocalTime;

@Data
public class MusicForm {
    private String title;
    private int price;
    private int method;
    private long albumId;
    private LocalDate duration;
}