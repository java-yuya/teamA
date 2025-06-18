package com.example.music_management.entity;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
// import java.time.LocalTime;

@Data
public class Music{
    private long musicId;//ID
    private String title;//内容
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate duration;//決済日
    private int price;//金額
    private int method;//収入支出判定
    private long albumId;//
    private LocalDateTime createdAt;
}