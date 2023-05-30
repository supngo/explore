package com.naturecode.explore.util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

public class ResponseHandler {
  private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");

  public static CustomResponse generateException(int errorCode, String errorMessage) {
    CustomResponse error = new CustomResponse(errorCode, errorMessage);
    sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
    error.setTimestamp(sdf.format(new Timestamp(System.currentTimeMillis())));
    return error;
  }
}
