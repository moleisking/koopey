package com.koopey.api.controller;

import com.koopey.api.exception.JwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.*;
import java.text.ParseException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class ExceptionController  {

  @ExceptionHandler(ParseException.class)
  public ResponseEntity<Void> handleParseException( ParseException ex) {
    log.error("ParseException {}", ex.getMessage());
    return new ResponseEntity<Void>( HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler({JwtException.class, AccessDeniedException.class})
  public ResponseEntity<Void> handleJwtExpiredException( JwtException ex) {
    log.error("AccessDeniedException or JwtException {}", ex.getMessage());
    return new ResponseEntity<Void>( HttpStatus.UNAUTHORIZED);
  }

}
