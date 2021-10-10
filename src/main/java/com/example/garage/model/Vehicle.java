package com.example.garage.model;

import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(of = {"plate"})
public class Vehicle {

    @NotNull(message = "is missing")
    String plate;

    @NotNull(message = "is missing")
    String color;

    @NotNull(message = "is missing")
    VehicleType type;
}
