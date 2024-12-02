package ru.robotbot.parking_reservation.services;

import org.springframework.security.crypto.password.PasswordEncoder;
import ru.robotbot.parking_reservation.domain.dto.UserDto;
import ru.robotbot.parking_reservation.domain.dto.UserInfoResponse;
import ru.robotbot.parking_reservation.domain.entities.UserEntity;
import ru.robotbot.parking_reservation.security.UserPrincipal;

import java.util.Optional;

public interface UserService {

    void addUser(UserDto userDto, PasswordEncoder passwordEncoder);

    boolean isUserExistsByPhoneNumber(String phoneNumber);

    boolean isUserExistsById(Long id);

    Optional<UserEntity> getUserByPhoneNumber(String phoneNumber);

    UserInfoResponse getUserByPrincipal(UserPrincipal principal);

    int addCar(UserPrincipal userPrincipal, String carState);

    int deleteCar(UserPrincipal userPrincipal, String carState);
}
