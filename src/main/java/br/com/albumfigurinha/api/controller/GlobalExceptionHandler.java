package br.com.albumfigurinha.api.controller;

import br.com.albumfigurinha.api.dto.ErrorDTO;
import br.com.albumfigurinha.api.exception.InvalidImageException;
import br.com.albumfigurinha.api.exception.UserAlreadyExistsException;
import br.com.albumfigurinha.api.exception.UserNotFoundException;

import com.auth0.jwt.exceptions.JWTVerificationException;

import com.auth0.jwt.exceptions.SignatureVerificationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler({UserNotFoundException.class})
    public ResponseEntity<Object> handleStudentNotFoundException(UserNotFoundException exception) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorDTO(exception.getMessage()));
    }

    @ExceptionHandler({UserAlreadyExistsException.class})
    public ResponseEntity<Object> handleStudentAlreadyExistsException(UserAlreadyExistsException exception) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorDTO(exception.getMessage()));
    }

    @ExceptionHandler({InvalidImageException.class})
    public ResponseEntity<Object> handleInvalidImageException(InvalidImageException exception) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorDTO(exception.getMessage()));
    }
    @ExceptionHandler({JWTVerificationException.class})
    public ResponseEntity<Object> handleJWTVerificationException() {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorDTO("Error while validating token"));
    }
    @ExceptionHandler({InternalAuthenticationServiceException.class})
    public ResponseEntity<Object> handleInternalAuthenticationServiceException() {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(new ErrorDTO("Authentication failed. User not found or invalid credentials."));
    }

    @ExceptionHandler({RuntimeException.class})
    public ResponseEntity<Object> handleRuntimeException(RuntimeException exception) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorDTO(exception.getMessage()));
    }
}
