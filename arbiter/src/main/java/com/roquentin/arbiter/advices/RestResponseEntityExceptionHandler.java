package com.roquentin.arbiter.advices;

import java.util.HashMap;
import java.util.Map;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.roquentin.arbiter.payloads.responses.ErrorResponse;
import com.roquentin.arbiter.payloads.responses.ErrorResponse.Causes;
import com.roquentin.arbiter.payloads.responses.Responses;





@ControllerAdvice
public class RestResponseEntityExceptionHandler{
	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseBody
	protected ErrorResponse<Map<String, String>> handleInvalidArgument(MethodArgumentNotValidException ex) {
		
		var errors = new HashMap<String, String>();
		
		ex.getBindingResult().getAllErrors().forEach(
				error -> {
					String fieldName = ((FieldError) error).getField();
					String errorMessage = error.getDefaultMessage();
					errors.put(fieldName, errorMessage);
				});
		
		return Responses.errorResponse(Causes.ARGUMENT_NOT_VALID, errors);
	}
	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	@ExceptionHandler(DataIntegrityViolationException.class)
	protected ErrorResponse handlePSQLException(DataIntegrityViolationException ex) {
		String cause = ex.getMostSpecificCause().toString();
		return Responses.errorResponse(Causes.INTEGRITY_EXCEPTION, cause.substring(cause.indexOf("Detail:")));
	}

}
