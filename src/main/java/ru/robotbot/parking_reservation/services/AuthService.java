package ru.robotbot.parking_reservation.services;

import ru.robotbot.parking_reservation.domain.entities.LoginResponse;

public interface AuthService {

    LoginResponse attemptLogin(String phoneNumber, String password);

}
