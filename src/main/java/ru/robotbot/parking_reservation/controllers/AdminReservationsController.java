package ru.robotbot.parking_reservation.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteReservationById(@PathVariable Long id) {
        int deleteAccept = reservationService.deleteById(id);

        Map<String, Object> response = new HashMap<>();
        switch (deleteAccept) {
            case 0:
                response.put("status", "success");
                response.put("message", "Reservation is deleted");
                return ResponseEntity.ok(response);
            case 1:
                response.put("status", "error");
                response.put("message", "Reservation with this id doesn't exist");
                return ResponseEntity.badRequest().body(response);
            default:
                response.put("status", "error");
                response.put("message", "Unknown error occurred");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }


    private Map<String, String> createErrorResponse(String message) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", message);
        return errorResponse;
    }
}
