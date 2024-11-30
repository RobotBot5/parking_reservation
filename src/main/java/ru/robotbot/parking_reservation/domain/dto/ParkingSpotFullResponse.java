package ru.robotbot.parking_reservation.domain.dto;

import lombok.Builder;
import lombok.Getter;
import ru.robotbot.parking_reservation.domain.enums.ParkingSpotZone;

import java.util.List;

@Getter
@Builder
public class ParkingSpotFullResponse {

    private Long id;

    private Integer number;

    private ParkingSpotZone zone;

    private boolean occupied;

    private List<ParkingSpotFullReservationResponse> reservations;

}
