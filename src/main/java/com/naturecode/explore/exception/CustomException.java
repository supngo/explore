package com.naturecode.explore.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomException {
  private String timestamp;
  private int status;
  private String error;

  public CustomException(int status, String error) {
    this.status = status;
    this.error = error;
  }
}
