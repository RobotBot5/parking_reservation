package ru.robotbot.parking_reservation.domain.enums;

import lombok.Getter;

@Getter
public enum ParkingSpotZone {
    A(100),
    B(200),
    C(300),
    D(400);

    private final int rate;

    ParkingSpotZone(int rate) {
        this.rate = rate;
    }
}
