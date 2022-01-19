package com.koopey.api.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import java.text.ParseException;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class ErrorHandlerController implements ErrorController {

  @RequestMapping("/error")
  @ResponseBody
  public String handleError(HttpServletRequest request) {
    Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
    Exception exception = (Exception) request.getAttribute("javax.servlet.error.exception");
       return String.format(
        "<html><body><h2>Error Page</h2><div>Status code: <b>%s</b></div>"
            + "<div>Exception Message: <b>%s</b></div><body></html>",
        statusCode, exception == null ? "N/A" : exception.getMessage());
  }

  /*@Override
  public String getErrorPath() {
    return "/error";
  }*/

  @ExceptionHandler(ParseException.class)
  public ResponseEntity<String> handleParserException(ParseException e) {
    log.error( e.getMessage());
    return new ResponseEntity<>("Please supply all required fields in error. " + e.getMessage(), HttpStatus.BAD_REQUEST);
  }

}
