package com.booking.rest.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.booking.rest.vo.RestApiErrorVO;

@ControllerAdvice
public class RestApiExceptionHandler {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ResponseBody
	public RestApiErrorVO handleValidationError(MethodArgumentNotValidException ex) {
		return new RestApiErrorVO(ex.getBindingResult());
	}

}
