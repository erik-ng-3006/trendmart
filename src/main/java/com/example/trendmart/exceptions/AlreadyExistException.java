package com.example.trendmart.exceptions;

public class AlreadyExistException extends RuntimeException {
  public AlreadyExistException(String message) {
    super(message);
  }
}
