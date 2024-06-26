package org.example.serverinfoproyectofinalfranciscodasilva.spring.errors;

import org.example.serverinfoproyectofinalfranciscodasilva.common.Constantes;
import org.example.serverinfoproyectofinalfranciscodasilva.domain.exceptions.*;
import org.example.serverinfoproyectofinalfranciscodasilva.domain.model.AppMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ControllerErrores extends ResponseEntityExceptionHandler {

    @ExceptionHandler(PublicKeyException.class)
    public ResponseEntity<AppMessage> handleKeyException(PublicKeyException e) {
        AppMessage appMessage = new AppMessage(e.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(appMessage);
    }

    @ExceptionHandler(UsersException.class)
    public ResponseEntity<AppMessage> handleKeyException(UsersException e) {
        AppMessage appMessage = new AppMessage(e.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(appMessage);
    }

    @ExceptionHandler(FilesException.class)
    public ResponseEntity<AppMessage> handleKeyException(FilesException e) {
        AppMessage appMessage = new AppMessage(e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(appMessage);
    }

    @ExceptionHandler(FilesNotFoundException.class)
    public ResponseEntity<AppMessage> handleKeyException(FilesNotFoundException e) {
        AppMessage appMessage = new AppMessage(e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(appMessage);
    }


    @ExceptionHandler(BalanceException.class)
    public ResponseEntity<AppMessage> handleKeyException(BalanceException e) {
        AppMessage appMessage = new AppMessage(e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(appMessage);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<AppMessage> handleKeyException(AccessDeniedException e) {
        AppMessage appMessage = new AppMessage(Constantes.NO_TIENE_ROL_PERMITIDO);
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(appMessage);
    }
}
