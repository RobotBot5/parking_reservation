package ru.robotbot.parking_reservation.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Getter;
import ru.robotbot.parking_reservation.domain.enums.ParkingSpotZone;

@Builder
@Getter
public class ParkingSpotCreateRequest {

    private Integer number;

    private ParkingSpotZone zone;

}
