package ru.robotbot.parking_reservation.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.robotbot.parking_reservation.domain.dto.ParkingSpotDto;
import ru.robotbot.parking_reservation.domain.dto.ParkingSpotFullResponse;
import ru.robotbot.parking_reservation.domain.dto.ParkingSpotCreateRequest;
import ru.robotbot.parking_reservation.domain.entities.ParkingSpotEntity;
import ru.robotbot.parking_reservation.domain.enums.ParkingSpotZone;
import ru.robotbot.parking_reservation.services.ParkingSpotService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin/parking-spots")
@RequiredArgsConstructor
public class AdminParkingSpotsController {

    private final ParkingSpotService parkingSpotService;

    @GetMapping
    public ResponseEntity<List<ParkingSpotEntity>> getParkingSpots(@RequestParam(required = false) ParkingSpotZone zone) {
        if (zone == null) {
            return ResponseEntity.ok(parkingSpotService.getAllParkingSpots());
        }
        return ResponseEntity.ok(parkingSpotService.getParkingSpotsByZone(zone));
    }

    @GetMapping("/occupied")
    public ResponseEntity<List<ParkingSpotEntity>> getOccupiedParkingSpots(@RequestParam(required = false) Boolean occupied) {
        if (occupied == null) {
            occupied = true;
        }
        return ResponseEntity.ok(parkingSpotService.getOccupiedParkingSpots(occupied));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ParkingSpotFullResponse> getParkingSpotById(@PathVariable Long id) {
        return parkingSpotService.findFullInfoById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> addParkingSpot(@RequestBody @Validated ParkingSpotCreateRequest parkingSpotCreateRequest) {
        int parkingSpotAccept = parkingSpotService.createParkingSpot(parkingSpotCreateRequest);

        Map<String, Object> response = new HashMap<>();
        switch (parkingSpotAccept) {
            case 0:
                response.put("status", "success");
                response.put("message", "Parking spot created successfully");
                return ResponseEntity.ok(response);
            case 1:
                response.put("status", "error");
                response.put("message", "Parking spot with this zone and number already exists");
                return ResponseEntity.badRequest().body(response);
            default:
                response.put("status", "error");
                response.put("message", "Unknown error occurred");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PutMapping
    public ResponseEntity<?> editParkingSpot(@RequestBody @Validated ParkingSpotDto parkingSpotDto) {

        int parkingSpotAccept = parkingSpotService.updateParkingSpot(parkingSpotDto);

        Map<String, Object> response = new HashMap<>();
        switch (parkingSpotAccept) {
            case 0:
                response.put("status", "success");
                response.put("message", "Parking spot updated successfully");
                return ResponseEntity.ok(response);
            case 1:
                response.put("status", "error");
                response.put("message", "Parking spot with this id doesnt exist");
                return ResponseEntity.badRequest().body(response);
            case 2:
                response.put("status", "error");
                response.put("message", "Parking spot with this zone and number already exists");
                return ResponseEntity.badRequest().body(response);
            default:
                response.put("status", "error");
                response.put("message", "Unknown error occurred");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }

    }

    @DeleteMapping
    public ResponseEntity<?> deleteParkingSpot(@RequestParam Long id) {
        int parkingSpotAccept = parkingSpotService.deleteParkingSpotById(id);

        Map<String, Object> response = new HashMap<>();
        switch (parkingSpotAccept) {
            case 0:
                response.put("status", "success");
                response.put("message", "Parking spot deleted successfully");
                return ResponseEntity.ok(response);
            case 1:
                response.put("status", "error");
                response.put("message", "Parking spot with this id doesnt exist");
                return ResponseEntity.badRequest().body(response);
            case 2:
                response.put("status", "error");
                response.put("message", "Exists active reservation on this parking spot");
                return ResponseEntity.badRequest().body(response);
            default:
                response.put("status", "error");
                response.put("message", "Unknown error occurred");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }

    }

}
