package com.naturecode.explore.util;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomResponse {
  private String timestamp;
  private int status;
  private String error;

  public CustomResponse(int status, String error) {
    this.status = status;
    this.error = error;
  }
}
