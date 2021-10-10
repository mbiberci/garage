package com.example.garage.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum VehicleType {

    Car(1),
    Jeep(2),
    Truck(4);

    int size;

}
