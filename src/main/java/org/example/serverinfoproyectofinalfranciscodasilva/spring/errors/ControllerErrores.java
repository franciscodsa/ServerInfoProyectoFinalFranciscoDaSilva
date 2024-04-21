package org.example.serverinfoproyectofinalfranciscodasilva.spring.errors;

import org.example.serverinfoproyectofinalfranciscodasilva.domain.exceptions.PublicKeyException;
import org.example.serverinfoproyectofinalfranciscodasilva.domain.model.AppError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ControllerErrores extends ResponseEntityExceptionHandler {

    @ExceptionHandler(PublicKeyException.class)
    public ResponseEntity<AppError> handleKeyException(PublicKeyException e){
        AppError appError = new AppError(e.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(appError);
    }
}
