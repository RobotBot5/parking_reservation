package ru.robotbot.parking_reservation.services;

import ru.robotbot.parking_reservation.domain.dto.ReservationDto;
import ru.robotbot.parking_reservation.security.UserPrincipal;

public interface ReservationService {

    boolean createReservation(ReservationDto reservationDto, UserPrincipal userPrincipal);

}
