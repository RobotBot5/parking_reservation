package ru.robotbot.parking_reservation.services;

import ru.robotbot.parking_reservation.domain.dto.LoginResponse;

public interface AuthService {

    LoginResponse attemptLogin(String phoneNumber, String password);

}
