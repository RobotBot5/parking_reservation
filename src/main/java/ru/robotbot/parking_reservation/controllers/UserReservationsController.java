package ru.robotbot.parking_reservation.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.robotbot.parking_reservation.domain.dto.ReservationDto;
import ru.robotbot.parking_reservation.domain.entities.ReservationEntity;
import ru.robotbot.parking_reservation.security.UserPrincipal;
import ru.robotbot.parking_reservation.services.ReservationService;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/reservations")
@RequiredArgsConstructor
public class UserReservationsController {

    private final ReservationService reservationService;

    @PostMapping
    public ResponseEntity<Map<String, Object>> createReservation(
            @RequestBody @Validated ReservationDto reservationDto,
            @AuthenticationPrincipal UserPrincipal userPrincipal
    ) {
        int reservationAccept = reservationService.createReservation(reservationDto, userPrincipal);

        Map<String, Object> response = new HashMap<>();
        switch (reservationAccept) {
            case 0:
                response.put("status", "success");
                response.put("message", "Reservation accepted");
                return ResponseEntity.ok(response);
            case 1:
                response.put("status", "error");
                response.put("message", "Parking spot with this ID doesn't exist");
                return ResponseEntity.badRequest().body(response);
            case 2:
                response.put("status", "error");
                response.put("message", "This time for this parking spot is already reserved");
                return ResponseEntity.badRequest().body(response);
            case 3:
                response.put("status", "error");
                response.put("message", "Start time must be before end time");
                return ResponseEntity.badRequest().body(response);
            case 4:
                response.put("status", "error");
                response.put("message", "Reservation's time must be at least 30 minutes");
                return ResponseEntity.badRequest().body(response);
            case 5:
                response.put("status", "error");
                response.put("message", "One user can have only one active reservation");
                return ResponseEntity.badRequest().body(response);
            case 6:
                response.put("status", "error");
                response.put("message", "Start time has passed");
                return ResponseEntity.badRequest().body(response);
            default:
                response.put("status", "error");
                response.put("message", "Unknown error occurred");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping
    public ResponseEntity<ReservationEntity> getReservation(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        Optional<ReservationEntity> reservationByUser = reservationService.getReservationByUser(userPrincipal);
        ReservationEntity reservationEntity = reservationByUser.orElse(null);
        if (reservationEntity == null) return ResponseEntity.notFound().build();
        else return ResponseEntity.ok(reservationEntity);
    }

    @DeleteMapping
    public ResponseEntity<Map<String, Object>> cancelReservation(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        int reservationAccept = reservationService.cancelReservation(userPrincipal);

        Map<String, Object> response = new HashMap<>();
        switch (reservationAccept) {
            case 0:
                response.put("status", "success");
                response.put("message", "Reservation is canceled");
                return ResponseEntity.ok(response);
            case 1:
                response.put("status", "error");
                response.put("message", "User doesn't have active reservation");
                return ResponseEntity.badRequest().body(response);
            case 2:
                response.put("status", "error");
                response.put("message", "Reservation can be canceled only at 30 minutes");
                return ResponseEntity.badRequest().body(response);
            default:
                response.put("status", "error");
                response.put("message", "Unknown error occurred");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PutMapping
    public ResponseEntity<?> extendReservation(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @RequestParam Integer minutes
    ) {
        int extendAccept = reservationService.extendTime(userPrincipal, minutes);

        Map<String, Object> response = new HashMap<>();
        switch (extendAccept) {
            case 0:
                response.put("status", "success");
                response.put("message", "Reservation's time is extended");
                return ResponseEntity.ok(response);
            case 1:
                response.put("status", "error");
                response.put("message", "User doesn't have active reservation");
                return ResponseEntity.badRequest().body(response);
            case 2:
                response.put("status", "error");
                response.put("message", "Exists reservation on this time");
                return ResponseEntity.badRequest().body(response);
            case 3:
                response.put("status", "error");
                response.put("message", "User must pay for previous extend");
                return ResponseEntity.badRequest().body(response);
            case 4:
                response.put("status", "error");
                response.put("message", "User can't extend reservation time with fine");
                return ResponseEntity.badRequest().body(response);
            default:
                response.put("status", "error");
                response.put("message", "Unknown error occurred");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PutMapping("/pay")
    public ResponseEntity<?> payReservation(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        int reservationAccept = reservationService.payReservation(userPrincipal);

        Map<String, Object> response = new HashMap<>();
        switch (reservationAccept) {
            case 0:
                response.put("status", "success");
                response.put("message", "Reservation is paid");
                return ResponseEntity.ok(response);
            case 1:
                response.put("status", "error");
                response.put("message", "User doesn't have active reservation");
                return ResponseEntity.badRequest().body(response);
            case 2:
                response.put("status", "error");
                response.put("message", "Reservation is already payed");
                return ResponseEntity.badRequest().body(response);
            default:
                response.put("status", "error");
                response.put("message", "Unknown error occurred");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PutMapping("/pay-extend")
    public ResponseEntity<?> payExtendedTime(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        int reservationAccept = reservationService.payExtendedTime(userPrincipal);

        Map<String, Object> response = new HashMap<>();
        switch (reservationAccept) {
            case 0:
                response.put("status", "success");
                response.put("message", "Extended time is paid");
                return ResponseEntity.ok(response);
            case 1:
                response.put("status", "error");
                response.put("message", "User doesn't have active reservation");
                return ResponseEntity.badRequest().body(response);
            case 2:
                response.put("status", "error");
                response.put("message", "User doesn't have to pay for extend");
                return ResponseEntity.badRequest().body(response);
            default:
                response.put("status", "error");
                response.put("message", "Unknown error occurred");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

}
