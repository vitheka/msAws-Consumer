package br.com.vitheka.customer.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalErrorHandlerAdvice {

    @ExceptionHandler(EmailAlreadyInUse.class)
    public ResponseEntity<DefaultErrorMessage> handleNotFoundException(EmailAlreadyInUse ex) {
        var errorResponse = new DefaultErrorMessage(HttpStatus.BAD_REQUEST.value(), ex.getReason());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

}
