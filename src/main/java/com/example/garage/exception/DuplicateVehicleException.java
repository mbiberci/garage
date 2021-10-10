package com.example.garage.exception;

public class DuplicateVehicleException extends RuntimeException {

    public DuplicateVehicleException(String plate) {
        super(String.format("A vehicle with plate %s is already parked.", plate));
    }
}
