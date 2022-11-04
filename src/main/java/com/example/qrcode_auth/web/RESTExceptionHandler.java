package com.example.qrcode_auth.web;

import com.example.qrcode_auth.exception.HTTPException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.MethodNotAllowedException;

import javax.naming.AuthenticationException;
import javax.persistence.EntityNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;

@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
public class RESTExceptionHandler{


    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<HTTPException> handleEntityNotFound(EntityNotFoundException ex){
        HTTPException httpException = HTTPException.builder()
                .status(HttpStatus.NOT_FOUND.value())
                .message(ex.getMessage())
                .timestamp(System.currentTimeMillis())
                .build();
        return new ResponseEntity<>(httpException, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<HTTPException> handleAuthException(AuthenticationException ex) {
        HTTPException httpException = HTTPException.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .message(ex.getMessage())
                .timestamp(System.currentTimeMillis())
                .build();
        return new ResponseEntity<>(httpException, HttpStatus.BAD_REQUEST);

    }
        @ExceptionHandler(BadCredentialsException.class)
        public ResponseEntity<HTTPException> handleBadCredentials(BadCredentialsException ex){
            HTTPException httpException = HTTPException.builder()
                    .status(HttpStatus.BAD_REQUEST.value())
                    .message(ex.getMessage())
                    .timestamp(System.currentTimeMillis())
                    .build();
            return new ResponseEntity<>(httpException, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(MethodNotAllowedException.class)
    public ResponseEntity<HTTPException> handleMethodNotAllowed (MethodNotAllowedException ex){
        ex.printStackTrace();
            HTTPException httpException = HTTPException.builder()
                    .status(HttpStatus.METHOD_NOT_ALLOWED.value())
                    .message(ex.getMessage())
                    .timestamp(System.currentTimeMillis())
                    .build();
            return new ResponseEntity<>(httpException, HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<HTTPException> handleMethodNotAllowed (HttpRequestMethodNotSupportedException ex){
        HTTPException httpException = HTTPException.builder()
                .status(HttpStatus.METHOD_NOT_ALLOWED.value())
                .message(ex.getMessage())
                .timestamp(System.currentTimeMillis())
                .build();
        return new ResponseEntity<>(httpException, HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<HTTPException> handleNoBody (HttpMessageNotReadableException ex){
        HTTPException httpException = HTTPException.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .message(ex.getLocalizedMessage().split(":")[0])
                .timestamp(System.currentTimeMillis())
                .build();
        return new ResponseEntity<>(httpException, HttpStatus.BAD_REQUEST);
    }

}



