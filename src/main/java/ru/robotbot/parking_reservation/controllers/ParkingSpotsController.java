package ru.robotbot.parking_reservation.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.robotbot.parking_reservation.domain.dto.ParkingSpotsResponse;
import ru.robotbot.parking_reservation.domain.entities.ParkingSpotEntity;
import ru.robotbot.parking_reservation.domain.enums.ParkingSpotZone;
import ru.robotbot.parking_reservation.services.ParkingSpotService;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/parking-spots")
@RequiredArgsConstructor
public class ParkingSpotsController {

    private final ParkingSpotService parkingSpotService;

    @GetMapping
    public ResponseEntity<List<ParkingSpotEntity>> getParkingSpots(
            @RequestParam(required = false) ParkingSpotZone zone
    ) {
        if (zone == null) {
            return ResponseEntity.ok(parkingSpotService.getAllParkingSpots());
        }
        return ResponseEntity.ok(parkingSpotService.getParkingSpotsByZone(zone));
    }

    @GetMapping("/occupied")
    public ResponseEntity<List<ParkingSpotsResponse>> getOccupiedParkingSpots(
            @RequestParam Boolean occupied,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end
    ) {
        return ResponseEntity.ok(parkingSpotService.getOccupiedParkingSpots(occupied, start, end));
    }

}
