package ru.robotbot.parking_reservation.services;

import org.springframework.security.crypto.password.PasswordEncoder;
import ru.robotbot.parking_reservation.domain.dto.UserDto;
import ru.robotbot.parking_reservation.domain.entities.UserEntity;

import java.util.Optional;

public interface UserService {

    void addUser(UserDto userDto, PasswordEncoder passwordEncoder);

    boolean isUserExistsByPhoneNumber(String phoneNumber);

    boolean isUserExistsById(Long id);

    Optional<UserEntity> getUserByPhoneNumber(String phoneNumber);

}
