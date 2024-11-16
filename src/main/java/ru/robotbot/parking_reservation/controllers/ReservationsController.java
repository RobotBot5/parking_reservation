package ru.robotbot.parking_reservation.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.robotbot.parking_reservation.domain.dto.ReservationDto;
import ru.robotbot.parking_reservation.domain.entities.ReservationEntity;
import ru.robotbot.parking_reservation.security.UserPrincipal;
import ru.robotbot.parking_reservation.services.ReservationService;

import java.util.Optional;

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
        int reservationAccept = reservationService.createReservation(reservationDto, userPrincipal);
        if (reservationAccept == 0) {
            return ResponseEntity.ok("Reservation accepted");
        } else if (reservationAccept == 1) { // parkingSpot with this id doesn't exist
            return ResponseEntity.badRequest().body("Parking spot with this id doesn't exist");
        } else { // exist reservation on this time
            return ResponseEntity.badRequest().body("This time of this parking spot is reserved");
        }
    }

    @GetMapping
    public ResponseEntity<ReservationEntity> getReservation(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        Optional<ReservationEntity> reservationByUser = reservationService.getReservationByUser(userPrincipal);
        ReservationEntity reservationEntity = reservationByUser.orElse(null);
        if (reservationEntity == null) return ResponseEntity.notFound().build();
        else return ResponseEntity.ok(reservationEntity);
    }

}
