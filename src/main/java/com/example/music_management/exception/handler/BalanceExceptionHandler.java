package com.example.music_management.exception.handler;

import com.example.music_management.exception.BalanceMissingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice
public class BalanceExceptionHandler {
  
  // 残高不足時に例外処理（口座詳細ページに遷移し、メッセージを出力）
  @ExceptionHandler(BalanceMissingException.class)
  public String handleBalanceMissingException(BalanceMissingException e, RedirectAttributes redirectAttributes) {
    redirectAttributes.addFlashAttribute("error", "残高不足のため決済処理に失敗しました ");
    return "redirect:/album/" + e.getAlbumId();
  }
}
