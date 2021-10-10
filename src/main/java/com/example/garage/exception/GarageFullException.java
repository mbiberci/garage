package com.example.garage.exception;

public class GarageFullException extends RuntimeException {

    public GarageFullException() {
        super("Garage is full.");
    }
}
