package br.movely.movelyapp.config;

import br.movely.movelyapp.exceptions.ForbiddenException;
import br.movely.movelyapp.exceptions.NotFoundException;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> handleRuntimeException(RuntimeException ex) {
        return ResponseEntity
                .status(HttpStatusCode.valueOf(404))
                .body(ex.getMessage());
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<?> handleNotFoundException(NotFoundException ex) {
        String message = ex.getMessage();
        if (message.isEmpty()) {
            message = "Resource Not Found";
        }
        return ResponseEntity
                .status(HttpStatusCode.valueOf(400))
                .body(message);
    }

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<?> handleForbiddenException(ForbiddenException ex) {
        String message = ex.getMessage();
        if (message.isEmpty()) {
            message = "Forbidden";
        }
        return ResponseEntity
                .status(HttpStatusCode.valueOf(403))
                .body(message);
    }
}
