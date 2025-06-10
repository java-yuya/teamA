package com.example.music_management.security;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(requests -> requests
                    .requestMatchers("/register", "/login", "/error").permitAll()
                    .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                    // h2-consoleでログインを無効
                    .requestMatchers(AntPathRequestMatcher.antMatcher("/h2-console/**")).permitAll()
                    .anyRequest().authenticated())
            // Spring 4 以降はデフォルトでCSRFが有効だが、明示的に有効にする。
            .csrf(csrf -> csrf.csrfTokenRepository(new HttpSessionCsrfTokenRepository())
            // h2-consoleでCSRFを無効にする
            .ignoringRequestMatchers(AntPathRequestMatcher.antMatcher("/h2-console/**"))
            ) 
            // h2-consoleの設定
            .securityMatcher("/h2-console/**")
                .headers(headers -> headers.frameOptions(
                         frame -> frame.sameOrigin()))
            // 全体への設定
            .securityMatcher("/**")
            .formLogin(login -> login
                    .loginProcessingUrl("/login")
                    .loginPage("/login")
                    .defaultSuccessUrl("/albums")
                    .failureUrl("/login?error")
                    .permitAll());
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}