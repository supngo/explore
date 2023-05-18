package com.naturecode.explore.exception;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

public class HandleException {
  private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");

  public static CustomException generateException(int errorCode, String errorMessage) {
    CustomException error = new CustomException(errorCode, errorMessage);
    sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
    error.setTimestamp(sdf.format(new Timestamp(System.currentTimeMillis())));
    return error;
  }
}
