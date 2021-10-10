package com.example.garage.exception;

public class TicketNotFoundException extends RuntimeException {
    public TicketNotFoundException() {
        super("Ticket not found");
    }
}
