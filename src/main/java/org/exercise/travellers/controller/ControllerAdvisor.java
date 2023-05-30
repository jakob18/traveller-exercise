package org.exercise.travellers.controller;

import jakarta.validation.ConstraintViolationException;
import org.exercise.travellers.exception.DuplicatedResourcesException;
import org.exercise.travellers.exception.InvalidEmailException;
import org.exercise.travellers.exception.TravellerDeactivatedException;
import org.exercise.travellers.exception.TravellerNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class ControllerAdvisor extends ResponseEntityExceptionHandler {

	@ExceptionHandler(TravellerNotFoundException.class)
	public ResponseEntity<Object> handleTravellerNotFoundException(TravellerNotFoundException ex) {
		return new ResponseEntity<>(createResponse(ex), HttpStatus.NO_CONTENT);
	}

	@ExceptionHandler(DuplicatedResourcesException.class)
	public ResponseEntity<Object> handleDuplicatedResourcesException(DuplicatedResourcesException ex) {
		return new ResponseEntity<>(createResponse(ex), HttpStatus.CONFLICT);
	}

	@ExceptionHandler(TravellerDeactivatedException.class)
	public ResponseEntity<Object> handleTravellerDeactivatedException(TravellerDeactivatedException ex) {
		return new ResponseEntity<>(createResponse(ex), HttpStatus.UNPROCESSABLE_ENTITY);
	}

	@ExceptionHandler(InvalidEmailException.class)
	public ResponseEntity<Object> handleInvalidEmailException(InvalidEmailException ex) {
		return new ResponseEntity<>(createResponse(ex), HttpStatus.BAD_REQUEST);
	}

	private Map<String, Object> createResponse(RuntimeException ex) {
		Map<String, Object> body = new LinkedHashMap<>();
		body.put("timestamp", LocalDateTime.now());
		body.put("message", ex.getMessage());
		return body;
	}

	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<List<String>> validationErrors(ConstraintViolationException e) {
		List<String> errors = new ArrayList<>(e.getConstraintViolations().size());
		e.getConstraintViolations().forEach(constraintViolation -> errors.add(constraintViolation.getPropertyPath() + " : " + constraintViolation.getMessage()));
		return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
	}
}
