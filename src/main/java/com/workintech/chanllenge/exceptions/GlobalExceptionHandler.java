package com.workintech.chanllenge.exceptions;

import ch.qos.logback.classic.Logger;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

@ExceptionHandler(CourseException.class)
    public ResponseEntity<CourseErrorResponse> handleException(CourseException courseException){
    log.error("api exception occured! Exception details: ",courseException.getMessage());
        CourseErrorResponse courseErrorResponse=new CourseErrorResponse(courseException.getStatus().value(),
                courseException.getMessage(), LocalDateTime.now());

        return new ResponseEntity<>(courseErrorResponse,courseException.getStatus());
    }

    @ExceptionHandler
    public ResponseEntity<CourseErrorResponse> handleException(Exception exception){

        log.error(" Exception occured! ",exception.getMessage());

        CourseErrorResponse errorResponse =
                new CourseErrorResponse(HttpStatus.BAD_REQUEST.value(),
                        exception.getMessage(),
                        LocalDateTime.now());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}


