package com.kasyus.cartservice.exception;

import com.kasyus.cartservice.exception.GeneralErrorMessages;
import com.kasyus.cartservice.exception.CartNotFoundException;
import com.kasyus.cartservice.general.RestResponse;
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

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgument(IllegalArgumentException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ex.getMessage());
    }

    @ExceptionHandler(CartNotFoundException.class)
    public ResponseEntity<Object> handleCategoryNotFoundException(CartNotFoundException ex, WebRequest request) {
        return new ResponseEntity<>(buildErrorResponse(ex, request), HttpStatus.NOT_FOUND);
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

        RestResponse<Void> restResponse = RestResponse.error(null, errorMessage);
        return new ResponseEntity<>(restResponse, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(RuntimeException.class)
    public final ResponseEntity<Object> handleRunTimeExceptions(RuntimeException ex, WebRequest request){
        return new ResponseEntity<>(buildErrorResponse(ex, request), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGeneralException(Exception ex, WebRequest request) {
        return new ResponseEntity<>(buildErrorResponse(ex, request), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private RestResponse<GeneralErrorMessages> buildErrorResponse(Exception ex, WebRequest request) {
        String message = ex.getMessage();
        String description = request.getDescription(false);
        GeneralErrorMessages generalErrorMessages = new GeneralErrorMessages(LocalDateTime.now(), message, description);
        return RestResponse.error(generalErrorMessages, message);
    }

}
