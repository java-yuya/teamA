package com.example.music_management.controller;

import com.example.music_management.form.UserForm;
import com.example.music_management.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class RegistrationController {
    private final UserService userService;

    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("userForm", new UserForm());
        return "register";
    }
    
    @PostMapping("/register")
    public String registerUser(UserForm userForm,
                               @RequestParam("username") String username,
                               @RequestParam("password") String password,
                               RedirectAttributes redirectAttributes) {
        if (username.trim().isEmpty() || password.trim().isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "ユーザー名とパスワードは空白にできません");
            return "redirect:/register";
        }
        userService.createUser(userForm);
        return "redirect:/login?register";
    }
 }