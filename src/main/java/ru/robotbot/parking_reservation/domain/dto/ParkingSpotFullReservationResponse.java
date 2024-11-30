package ru.robotbot.parking_reservation.domain.dto;

import lombok.Builder;
import lombok.Getter;
import ru.robotbot.parking_reservation.domain.entities.UserEntity;
import ru.robotbot.parking_reservation.domain.enums.ReservationType;

import java.time.LocalDateTime;

@Getter
@Builder
public class ParkingSpotFullReservationResponse {

    private Long id;

    private UserEntity userEntity;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private Boolean isPaid;

    private ReservationType reservationType;

}
