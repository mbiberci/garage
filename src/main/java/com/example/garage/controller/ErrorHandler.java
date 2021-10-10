package com.example.garage.controller;

import com.example.garage.exception.DuplicateVehicleException;
import com.example.garage.exception.GarageFullException;
import com.example.garage.exception.TicketAlreadyReturnedException;
import com.example.garage.exception.TicketNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.stream.Collectors;

@ControllerAdvice
public class ErrorHandler {

    @ResponseBody
    @ExceptionHandler(DuplicateVehicleException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    String duplicateVehicleHandler(DuplicateVehicleException e) {
        return e.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(GarageFullException.class)
    @ResponseStatus(HttpStatus.INSUFFICIENT_STORAGE)
    String garageFullHandler(GarageFullException e) {
        return e.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(TicketNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String ticketNotFoundHandler(TicketNotFoundException e) {
        return e.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(TicketAlreadyReturnedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String ticketAlreadyReturnedHandler(TicketAlreadyReturnedException e) {
        return e.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String handleMethodArgumentNotValid(MethodArgumentNotValidException e) {
        return e.getFieldErrors().stream()
                .map(fieldError -> String.format("%s %s", fieldError.getField(), fieldError.getDefaultMessage()))
                .collect(Collectors.joining("\n"));
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    String handleHttpMessageNotReadable(HttpMessageNotReadableException e) {
        return e.getMessage();
    }
}
