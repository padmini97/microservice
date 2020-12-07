package com.eatza.deliveryservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomGlobalExceptionHandler extends ResponseEntityExceptionHandler{
	
	@ExceptionHandler(UnauthorizedException.class)
	public ResponseEntity<Object> exception(UnauthorizedException exception) {
		
		 return new ResponseEntity<>("Invalid Credentials", HttpStatus.UNAUTHORIZED);
	}
	
	@ExceptionHandler(InvalidTokenException.class)
	ResponseEntity<Object> exception(InvalidTokenException exception) {
		
		 return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(DeliveryIdNotFounException.class)
	ResponseEntity<Object> exception(DeliveryIdNotFounException exception) {
		
		 return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
	}
}
