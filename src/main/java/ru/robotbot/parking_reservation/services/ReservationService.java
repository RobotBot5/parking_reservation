package ru.robotbot.parking_reservation.services;

import ru.robotbot.parking_reservation.domain.dto.ReservationDto;
import ru.robotbot.parking_reservation.domain.entities.ReservationEntity;
import ru.robotbot.parking_reservation.domain.enums.ReservationType;
import ru.robotbot.parking_reservation.security.UserPrincipal;

import java.util.List;
import java.util.Optional;

public interface ReservationService {

    int createReservation(ReservationDto reservationDto, UserPrincipal userPrincipal);

    Optional<ReservationEntity> getReservationByUser(UserPrincipal user);

    List<ReservationEntity> getAllByStatus(ReservationType reservationType);

    List<ReservationEntity> getAllReservations();

    List<ReservationEntity> getAllByUserId(Long id);

    Optional<ReservationEntity> findById(Long id);

    int cancelReservation(UserPrincipal userPrincipal);

    void setCanceledReservations();

    int payReservation(UserPrincipal userPrincipal);

    int extendTime(UserPrincipal userPrincipal, Integer minutes);

    int payExtendedTime(UserPrincipal userPrincipal);
}
