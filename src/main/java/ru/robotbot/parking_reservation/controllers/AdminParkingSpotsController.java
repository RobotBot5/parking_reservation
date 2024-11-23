package ru.robotbot.parking_reservation.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.robotbot.parking_reservation.services.ParkingSpotService;

@RestController
@RequestMapping("/admin/parking-spots")
@RequiredArgsConstructor
public class AdminParkingSpotsController {

    private final ParkingSpotService parkingSpotService;

    @GetMapping
    public ResponseEntity<?> getParkingSpots() {
        return ResponseEntity.ok(parkingSpotService.getAllParkingSpots());
    }

}
