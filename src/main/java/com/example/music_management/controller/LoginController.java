package com.example.music_management.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
//ヌメラ
@Controller
public class LoginController {
    
    @GetMapping("/login")
    public String login(@RequestParam(value = "continue", required = false) String continueParam) {
        return "login";
    }
}