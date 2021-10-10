package com.example.garage.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;

@Data
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
public class Ticket {

    int id;
    Vehicle vehicle;
    int[] slots;

    @JsonIgnore
    boolean isReturned;

    @Override
    public String toString() {
        return String.format("%s %s\t%s", vehicle.getPlate(), vehicle.getColor(), Arrays.toString(slots));
    }
}
