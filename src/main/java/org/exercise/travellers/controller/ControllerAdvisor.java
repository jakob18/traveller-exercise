package org.exercise.travellers.controller;

import org.exercise.travellers.exception.DuplicatedResourcesException;
import org.exercise.travellers.exception.TravellerNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
public class ControllerAdvisor extends ResponseEntityExceptionHandler {

    @ExceptionHandler(TravellerNotFoundException.class)
    public ResponseEntity<Object> handleTravellerNotFoundException(TravellerNotFoundException ex){
        return new ResponseEntity<>(createResponse(ex), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DuplicatedResourcesException.class)
    public ResponseEntity<Object> handleDuplicatedResourcesException(DuplicatedResourcesException ex){
        return new ResponseEntity<>(createResponse(ex), HttpStatus.CONFLICT);
    }

    private Map<String, Object> createResponse(RuntimeException ex) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("message", ex.getMessage());
        return body;
    }
}
