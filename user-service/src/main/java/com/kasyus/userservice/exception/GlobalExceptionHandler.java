package com.kasyus.userservice.exception;

import com.kasyus.userservice.general.RestResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@ControllerAdvice
@RestController
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    public GlobalExceptionHandler() {
    }

    @ExceptionHandler(AddressNotFoundException.class)
    public ResponseEntity<Object> handleAddressNotFoundException(AddressNotFoundException ex, WebRequest request) {
        String message = ex.getMessage();
        String description = request.getDescription(false);
        GeneralErrorMessages generalErrorMessages = new GeneralErrorMessages(LocalDateTime.now(), message, description);
        RestResponse<GeneralErrorMessages> restResponse = RestResponse.error(generalErrorMessages, message);
        return new ResponseEntity<>(restResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(PaymentNotFoundException.class)
    public ResponseEntity<Object> handlePaymentNotFoundException(PaymentNotFoundException ex, WebRequest request) {
        String message = ex.getMessage();
        String description = request.getDescription(false);
        GeneralErrorMessages generalErrorMessages = new GeneralErrorMessages(LocalDateTime.now(), message, description);
        RestResponse<GeneralErrorMessages> restResponse = RestResponse.error(generalErrorMessages, message);
        return new ResponseEntity<>(restResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Object> handleUserNotFoundException(UserNotFoundException ex, WebRequest request) {
        String message = ex.getMessage();
        String description = request.getDescription(false);
        GeneralErrorMessages generalErrorMessages = new GeneralErrorMessages(LocalDateTime.now(), message, description);
        RestResponse<GeneralErrorMessages> restResponse = RestResponse.error(generalErrorMessages, message);
        return new ResponseEntity<>(restResponse, HttpStatus.NOT_FOUND);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request) {

        String errorMessage = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage())
                .collect(Collectors.joining(", "));

        RestResponse<Void> restResponse = RestResponse.of(null, errorMessage);
        return new ResponseEntity<>(restResponse, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(RuntimeException.class)
    public final ResponseEntity<Object> handleRunTimeExceptions(RuntimeException ex, WebRequest request){
        String message = ex.getMessage();
        String description = request.getDescription(false);
        GeneralErrorMessages generalErrorMessages = new GeneralErrorMessages(LocalDateTime.now(), message, description);
        RestResponse<GeneralErrorMessages> restResponse = RestResponse.error(generalErrorMessages, message);
        return new ResponseEntity<>(restResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGeneralException(Exception ex, WebRequest request) {
        String message = ex.getMessage();
        String description = request.getDescription(false);
        GeneralErrorMessages generalErrorMessages = new GeneralErrorMessages(LocalDateTime.now(), message, description);
        RestResponse<GeneralErrorMessages> restResponse = RestResponse.error(generalErrorMessages, message);
        return new ResponseEntity<>(restResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
