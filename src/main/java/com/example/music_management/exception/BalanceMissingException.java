package com.example.music_management.exception;

import lombok.Getter;

@Getter
public class BalanceMissingException extends RuntimeException {
  private final long albumId;

  // 決済処理時に残高不足が発生したときの例外処理
  public BalanceMissingException(String message, long albumId) {
    super(message);
    this.albumId = albumId;
  }
}
