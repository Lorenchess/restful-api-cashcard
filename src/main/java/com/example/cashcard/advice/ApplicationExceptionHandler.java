package com.example.cashcard.advice;

import com.example.cashcard.exception.CashCardNotFoundException;
import com.example.cashcard.exception.PrincipalForbiddenException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ApplicationExceptionHandler {
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(CashCardNotFoundException.class)
    public Map<String, String> handleCashCardException(CashCardNotFoundException ex) {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("Error message:", ex.getMessage());

        return errorMap;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleInvalidArguments(MethodArgumentNotValidException ex) {
        Map<String, String> errorMap = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(e -> errorMap.put(e.getField(), e.getDefaultMessage()));
        return errorMap;
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(PrincipalForbiddenException.class)
    public Map<String, String> handleInvalidAction(PrincipalForbiddenException ex) {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("Error message: ", ex.getMessage());

        return errorMap;
    }
}
