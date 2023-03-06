package com.example.employeeManager.exceptions;

import lombok.Builder;
import lombok.RequiredArgsConstructor;

@Builder
@RequiredArgsConstructor
public class employeeNotFound extends RuntimeException{
  private String message;

  public employeeNotFound(String message) {
    super(message);
  }
}
