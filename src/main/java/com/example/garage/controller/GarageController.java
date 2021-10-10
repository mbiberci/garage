package com.example.garage.controller;

import com.example.garage.model.Ticket;
import com.example.garage.model.Vehicle;
import com.example.garage.service.GarageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequiredArgsConstructor
public class GarageController {

    final GarageService garageService;

    @PostMapping("/park")
    public ResponseEntity<Ticket> park(@Valid @RequestBody Vehicle vehicle) {
        return ResponseEntity.ok(garageService.park(vehicle));
    }

    @DeleteMapping("/leave")
    public void leave(@Positive @RequestParam("ticketId") int ticketId) {
        garageService.leave(ticketId);
    }

    @GetMapping("/status")
    public List<Ticket> status() {
        return garageService.getActiveTickets();
    }
}
