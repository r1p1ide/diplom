package org.example.diplom.exception.handler;

import org.example.diplom.dto.UserResponse;
import org.example.diplom.exception.ApiException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.example.diplom.resources.LoggerResources.THROW;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RestControllerAdvice
public class ExceptionControllerAdvice {

    private final static Logger LOG = Logger.getLogger(ExceptionControllerAdvice.class.getCanonicalName());

    @ResponseStatus(BAD_REQUEST)
    @RequestMapping(produces = "application/json;charset=utf-8")
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<UserResponse> handleMethodArgumentNotValid(MethodArgumentNotValidException vex,
                                                                     WebRequest request) {
        return ResponseEntity.status(BAD_REQUEST).body(
                new UserResponse(false, LocalDateTime.now(),
                        String.format("The parameter is incorrect: %s", vex.getFieldError().getField())));
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @RequestMapping(produces = "application/json;charset=utf-8")
    @ExceptionHandler({ApiException.class})
    public ResponseEntity<UserResponse> handleApiException(ApiException aex,
                                                           WebRequest request) {
        return handleExceptionByStatus(aex, request, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<UserResponse> handleExceptionByStatus(Exception ex,
                                                                 WebRequest request,
                                                                 HttpStatus httpStatus) {
        LOG.log(Level.WARNING, THROW, ex);

        return ResponseEntity.status(httpStatus).body(
                new UserResponse(false, LocalDateTime.now(), ex.getMessage()));
    }


}
