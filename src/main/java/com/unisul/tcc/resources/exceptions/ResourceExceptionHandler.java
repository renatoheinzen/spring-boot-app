package com.unisul.tcc.resources.exceptions;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.unisul.tcc.services.exceptions.DataIntegrityException;
import com.unisul.tcc.services.exceptions.ObjectNotFoundException;

@ControllerAdvice
public class ResourceExceptionHandler {
	
	@ExceptionHandler(ObjectNotFoundException.class)
	public ResponseEntity<StandError> objectNotFound(ObjectNotFoundException e, HttpServletRequest request) {
		StandError err = new StandError(HttpStatus.NOT_FOUND.value(), e.getMessage(), System.currentTimeMillis());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
	}
	
	
	@ExceptionHandler(DataIntegrityException.class)
	public ResponseEntity<StandError> dataIntegrity(DataIntegrityException e, HttpServletRequest request) {
		StandError err = new StandError(HttpStatus.BAD_REQUEST.value(), e.getMessage(), System.currentTimeMillis());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
	}
	
}
