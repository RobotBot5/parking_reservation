package ru.robotbot.parking_reservation.domain.dto;

import lombok.Builder;
import lombok.Getter;
import ru.robotbot.parking_reservation.domain.enums.ParkingSpotZone;

@Builder
@Getter
public class ParkingSpotDto {

    private Long id;

    private Integer number;

    private ParkingSpotZone zone;

}
