package org.prgrms.cream.global.error;

import lombok.extern.slf4j.Slf4j;
import org.prgrms.cream.domain.deal.exception.NotFoundBidException;
import org.prgrms.cream.domain.product.exception.NotFoundProductException;
import org.prgrms.cream.domain.user.exception.DuplicateUserException;
import org.prgrms.cream.domain.user.exception.InvalidArgumentException;
import org.prgrms.cream.domain.user.exception.NotFoundUserException;
import org.prgrms.cream.global.error.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> handleException(Exception exception) {
		log.error("handleException", exception);
		ErrorResponse errorResponse = ErrorResponse.of(ErrorCode.INTERNAL_SERVER_ERROR);
		return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorResponse> handleMethodArgumentNotValid(
		MethodArgumentNotValidException exception
	) {
		log.error("handleMethodArgumentNotValidException", exception);
		ErrorResponse errorResponse = ErrorResponse.of(
			ErrorCode.INVALID_INPUT, exception.getBindingResult());
		return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(
		{
			NotFoundProductException.class,
			NotFoundUserException.class,
			NotFoundBidException.class
		}
	)
	public ResponseEntity<ErrorResponse> handleNotFound(NotFoundException exception) {
		log.error("handleNotFoundException", exception);
		ErrorResponse errorResponse = ErrorResponse.of(ErrorCode.NOT_FOUND_RESOURCE);
		return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(DuplicateUserException.class)
	public ResponseEntity<ErrorResponse> handleDuplicateUserException(DuplicateUserException exception) {
		log.error("handleDuplicateUserException", exception);
		ErrorResponse errorResponse = ErrorResponse.of(ErrorCode.CONFLICT_ERROR);
		return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
	}

	@ExceptionHandler(InvalidArgumentException.class)
	public ResponseEntity<ErrorResponse> handleInvalidArgumentException(InvalidArgumentException exception) {
		log.error("handleInvalidArgumentException", exception);
		ErrorResponse errorResponse = ErrorResponse.of(ErrorCode.INVALID_INPUT);
		return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
	}
}
