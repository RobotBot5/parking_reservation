package ru.robotbot.parking_reservation.domain.entities;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LoginResponse {

    private final String accessToken;

}
