package ru.robotbot.parking_reservation.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.robotbot.parking_reservation.services.CameraService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/camera")
public class CameraController {

    private final CameraService cameraService;

    @GetMapping("/entry")
    public ResponseEntity<?> canOpenBarrierAtEntry(@RequestParam String carState) {
        int reservationAccept = cameraService.canOpenBarrierAtEntry(carState);

        Map<String, Object> response = new HashMap<>();
        switch (reservationAccept) {
            case 0:
                response.put("status", "success");
                response.put("message", "Open barrier...");
                return ResponseEntity.ok(response);
            case 1:
                response.put("status", "error");
                response.put("message", "User doesn't have active reservation");
                return ResponseEntity.badRequest().body(response);
            case 2:
                response.put("status", "error");
                response.put("message", "The reservation time is more than 15 minutes");
                return ResponseEntity.badRequest().body(response);
            case 3:
                response.put("status", "error");
                response.put("message", "The reservation is not paid");
                return ResponseEntity.badRequest().body(response);
            case 4:
                response.put("status", "error");
                response.put("message", "User with this car doesn't exist");
                return ResponseEntity.badRequest().body(response);
            default:
                response.put("status", "error");
                response.put("message", "Unknown error occurred");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/exit")
    public ResponseEntity<?> canOpenBarrierAtExit(@RequestParam String carState) {
        int reservationAccept = cameraService.canOpenBarrierAtExit(carState);

        Map<String, Object> response = new HashMap<>();
        switch (reservationAccept) {
            case 0:
                response.put("status", "success");
                response.put("message", "Open barrier...");
                return ResponseEntity.ok(response);
            case 1:
                response.put("status", "error");
                response.put("message", "User doesn't have active reservation");
                return ResponseEntity.badRequest().body(response);
            case 2:
                response.put("status", "error");
                response.put("message", "User didn't pay a fine at time, he has new fine to pay");
                return ResponseEntity.badRequest().body(response);
            case 3:
                response.put("status", "error");
                response.put("message", "End time is ended at 15 minutes ago or more, user has to pay a fine");
                return ResponseEntity.badRequest().body(response);
            case 4:
                response.put("status", "error");
                response.put("message", "User with this car doesn't exist");
                return ResponseEntity.badRequest().body(response);
            case 5:
                response.put("status", "error");
                response.put("message", "User has to pay a fine");
                return ResponseEntity.badRequest().body(response);
            default:
                response.put("status", "error");
                response.put("message", "Unknown error occurred");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

}
