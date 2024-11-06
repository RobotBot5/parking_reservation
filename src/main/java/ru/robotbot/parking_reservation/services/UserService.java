package ru.robotbot.parking_reservation.services;

import org.springframework.security.crypto.password.PasswordEncoder;
import ru.robotbot.parking_reservation.domain.dto.UserDto;
import ru.robotbot.parking_reservation.domain.entities.UserEntity;

import java.util.Optional;

public interface UserService {

    void addUser(UserDto userDto, PasswordEncoder passwordEncoder);

    boolean isUserExists(String phoneNumber);

    Optional<UserEntity> getUserByPhoneNumber(String phoneNumber);

}
