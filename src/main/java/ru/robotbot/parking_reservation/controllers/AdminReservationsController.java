package ru.robotbot.parking_reservation.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.robotbot.parking_reservation.domain.entities.ReservationEntity;
import ru.robotbot.parking_reservation.domain.enums.ReservationType;
import ru.robotbot.parking_reservation.services.ReservationService;

import java.util.List;

@RestController
@RequestMapping("/admin/reservations")
@RequiredArgsConstructor
public class AdminReservationsController {

    private final ReservationService reservationService;

    @GetMapping
    public List<ReservationEntity> getReservationsByType(@RequestParam ReservationType reservationType) {
        return reservationService.getAllByStatus(reservationType);
    }

}
