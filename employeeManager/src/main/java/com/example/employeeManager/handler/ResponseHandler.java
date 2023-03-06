package com.example.employeeManager.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public class ResponseHandler {
  public static ResponseEntity<Object> generateResponse(String msg, HttpStatus status,Object obj){
    Map<String, Object> map = new HashMap<String, Object>();
    map.put("message", msg);
    map.put("status", status.value());
    map.put("data", obj);

    return new ResponseEntity<Object>(map,status);
  }
}
