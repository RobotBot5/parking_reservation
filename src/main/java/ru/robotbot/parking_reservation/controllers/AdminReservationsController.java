package ru.robotbot.parking_reservation.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.robotbot.parking_reservation.domain.entities.ReservationEntity;
import ru.robotbot.parking_reservation.domain.enums.ReservationType;
import ru.robotbot.parking_reservation.services.ReservationService;
import ru.robotbot.parking_reservation.services.UserService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin/reservations")
@RequiredArgsConstructor
public class AdminReservationsController {

    private final ReservationService reservationService;
    private final UserService userService;

    @GetMapping
    public ResponseEntity<?> getReservations(
            @RequestParam(required = false) ReservationType reservationType,
            @RequestParam(required = false) Long userId) {

        if (userId != null) {
            if (!userService.isUserExistsById(userId)) {
                return ResponseEntity.badRequest().body(createErrorResponse("User with id " + userId + " doesn't exist"));
            }
            return ResponseEntity.ok(reservationService.getAllByUserId(userId));
        }

        if (reservationType != null) {
            return ResponseEntity.ok(reservationService.getAllByStatus(reservationType));
        }

        return ResponseEntity.ok(reservationService.getAllReservations());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReservationEntity> getReservationById(@PathVariable Long id) {
        return reservationService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    private Map<String, String> createErrorResponse(String message) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", message);
        return errorResponse;
    }
}
