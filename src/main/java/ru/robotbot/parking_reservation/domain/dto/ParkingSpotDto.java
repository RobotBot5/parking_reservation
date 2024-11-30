package ru.robotbot.parking_reservation.domain.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import ru.robotbot.parking_reservation.domain.enums.ParkingSpotZone;

@Builder
@Getter
public class ParkingSpotDto {

    @NotBlank(message = "Parking spot id cannot be blank")
    private Long id;

    private Integer number;

    private ParkingSpotZone zone;

}
