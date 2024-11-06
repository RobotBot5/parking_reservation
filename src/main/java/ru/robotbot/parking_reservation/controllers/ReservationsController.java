package ru.robotbot.parking_reservation.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.robotbot.parking_reservation.domain.dto.ReservationDto;
import ru.robotbot.parking_reservation.security.UserPrincipal;
import ru.robotbot.parking_reservation.services.ReservationService;

@RestController
@RequestMapping("/reservations")
@RequiredArgsConstructor
public class ReservationsController {

    private final ReservationService reservationService;

    @PostMapping
    public ResponseEntity<String> createReservation(
            @RequestBody @Validated ReservationDto reservationDto,
            @AuthenticationPrincipal UserPrincipal userPrincipal
    ) {
        boolean reservationAccept = reservationService.createReservation(reservationDto, userPrincipal);
        if (reservationAccept) {
            return ResponseEntity.ok("Reservation accepted");
        } else {
            return ResponseEntity.badRequest().body("Reservation failed");
        }
    }

}
