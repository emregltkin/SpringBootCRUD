package com.crud.exceptions;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CustomExceptionHandler {

	@ExceptionHandler(CustomNotFoundException.class)
	public ResponseEntity<?> customNotFound(CustomNotFoundException customNotFoundException){
		List<String> detailList = new ArrayList<String>();
		detailList.add(customNotFoundException.getMessage());
		ErrorResponse errorResponse = new ErrorResponse("CustomNotFoundException", detailList);
		return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
	}

}
