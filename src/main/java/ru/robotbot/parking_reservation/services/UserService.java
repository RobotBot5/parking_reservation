package ru.robotbot.parking_reservation.services;

import ru.robotbot.parking_reservation.domain.entities.UserEntity;

import java.util.Optional;

public interface UserService {

    void addUser(UserEntity userEntity);

    boolean isUserExists(String phoneNumber);

    Optional<UserEntity> getUserByPhoneNumber(String phoneNumber);

}
