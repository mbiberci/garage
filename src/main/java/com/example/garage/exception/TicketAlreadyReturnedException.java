package com.example.garage.exception;

public class TicketAlreadyReturnedException extends RuntimeException {
    public TicketAlreadyReturnedException() {
        super("Ticket is already returned.");
    }
}
