package io.uspeak.slight.exception;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@Log4j2
public class SlightExceptionResponseHandler extends ResponseEntityExceptionHandler {
  @ExceptionHandler(value = { SlightException.class })
  protected ResponseEntity<Object> handleConflict(RuntimeException ex, WebRequest request) {
    String msgBody = ex.getMessage();
    return handleExceptionInternal(ex, msgBody, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
  }
}
