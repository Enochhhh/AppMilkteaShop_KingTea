package com.milkteashop.kingtea.exception;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.milkteashop.kingtea.dto.ResponseExceptionDto;

@ControllerAdvice
public class ApiExceptionHandler {
	
	@ExceptionHandler(value = {NotFoundValueException.class})
	public ResponseEntity<Object> handleNotFoundValueException(NotFoundValueException e) {
		ResponseExceptionDto response = new ResponseExceptionDto(e.getMessage(), e.getPath(), 
				HttpStatus.NOT_FOUND, new Date());
		return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(value = {UnauthorizedException.class})
	public ResponseEntity<Object> handleUnauthorizedException(UnauthorizedException e) {
		ResponseExceptionDto response = new ResponseExceptionDto(e.getMessage(), e.getPath(), 
				HttpStatus.UNAUTHORIZED, new Date());
		return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
	}
	
	@ExceptionHandler(value = {Exception.class})
	public ResponseEntity<Object> handleException(Exception e) {
		ResponseExceptionDto response = new ResponseExceptionDto("Some errors occured", "Unidentified the path occured eror", 
				HttpStatus.BAD_REQUEST, new Date());
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}
}
