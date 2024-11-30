package ru.robotbot.parking_reservation.services;

import ru.robotbot.parking_reservation.domain.entities.FineEntity;
import ru.robotbot.parking_reservation.domain.entities.UserEntity;
import ru.robotbot.parking_reservation.security.UserPrincipal;

import java.util.Optional;

public interface FineService {

    Optional<FineEntity> getFineByUser(UserPrincipal userPrincipal);

    int payForFine(UserPrincipal userPrincipal);

}
