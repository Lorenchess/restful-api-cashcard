package com.example.cashcard.advice;

import com.example.cashcard.exception.CashCardNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
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
}
