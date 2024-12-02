package ru.robotbot.parking_reservation.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserInfoResponse {

    private String phoneNumber;

    private String firstName;

    private List<String> carStates;

    private String email;

}
