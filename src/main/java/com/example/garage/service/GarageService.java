package com.example.garage.service;

import com.example.garage.exception.DuplicateVehicleException;
import com.example.garage.exception.GarageFullException;
import com.example.garage.exception.TicketAlreadyReturnedException;
import com.example.garage.exception.TicketNotFoundException;
import com.example.garage.model.Ticket;
import com.example.garage.model.Vehicle;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@Slf4j
public class GarageService {

    static int ticketCounter = 0;

    @Value("${garage.capacity}")
    int capacity;

    Set<Vehicle> vehicles;
    BitSet slots;
    List<Ticket> tickets;

    @PostConstruct
    public void init() {
        vehicles = new LinkedHashSet<>();
        tickets = new ArrayList<>();
        slots = new BitSet(capacity + 2);
        slots.set(0, slots.size());
        slots.clear(0, capacity + 2);
    }

    public Ticket park(Vehicle vehicle) {

        if (vehicles.contains(vehicle)) {
            throw new DuplicateVehicleException(vehicle.getPlate());
        }

        int size = vehicle.getType().getSize();
        int nextFreeSlot = slots.nextClearBit(1);
        boolean isParkable = slots.get(nextFreeSlot - 1, nextFreeSlot + size + 1).isEmpty();

        while (nextFreeSlot < capacity && !isParkable) {
            nextFreeSlot++;
            isParkable = slots.get(nextFreeSlot - 1, nextFreeSlot + size + 1).isEmpty();
        }

        if (!isParkable) {
            throw new GarageFullException();
        }

        int[] place = IntStream.range(nextFreeSlot, nextFreeSlot + size).toArray();
        vehicles.add(vehicle);
        slots.set(nextFreeSlot, nextFreeSlot + size);
        log.info("Allocated {} slot{}.", size, size == 1 ? "" : "s");
        Ticket ticket = Ticket.of(ticketCounter++, vehicle, place, false);
        tickets.add(ticket);
        return ticket;
    }

    public void leave(int ticketId) {
        int lastIndex = tickets.size() - 1;
        if (ticketId > lastIndex) {
            throw new TicketNotFoundException();
        }

        Ticket ticket = tickets.get(ticketId);
        if (ticket.isReturned()) {
            throw new TicketAlreadyReturnedException();
        }

        vehicles.remove(ticket.getVehicle());
        Arrays.stream(ticket.getSlots()).forEach(value -> slots.clear(value));
        int vehicleSize = ticket.getVehicle().getType().getSize();
        log.info("Freed {} slot{}.", vehicleSize, vehicleSize == 1 ? "" : "s");
        ticket.setReturned(true);
    }

    public List<Ticket> getActiveTickets() {
        List<Ticket> activeTickets = tickets.stream().filter(ticket -> !ticket.isReturned()).collect(Collectors.toList());
        log.info("Status:\n{}", activeTickets.stream().map(Ticket::toString).collect(Collectors.joining("\n")));
        return activeTickets;
    }
}
