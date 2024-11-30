package ru.robotbot.parking_reservation.domain.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import ru.robotbot.parking_reservation.domain.enums.ParkingSpotZone;

@Builder
@Getter
public class ParkingSpotCreateRequest {

    @NotBlank(message = "Number of parking spot cannot be blank")
    private Integer number;

    @NotBlank(message = "Zone of parking spot cannot be blank")
    private ParkingSpotZone zone;

}
