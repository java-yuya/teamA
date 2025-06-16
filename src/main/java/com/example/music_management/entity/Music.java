package com.example.music_management.entity;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
// import java.time.LocalTime;

@Data
public class Music{
    private long musicId;
    private String title;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate duration;
    private int price;
    private int method;
    private long albumId;
    private LocalDateTime createdAt;
}