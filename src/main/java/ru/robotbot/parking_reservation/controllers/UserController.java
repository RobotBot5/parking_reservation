package ru.robotbot.parking_reservation.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.robotbot.parking_reservation.domain.dto.UserInfoResponse;
import ru.robotbot.parking_reservation.security.UserPrincipal;
import ru.robotbot.parking_reservation.services.UserService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<UserInfoResponse> getUserInfo(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        return ResponseEntity.ok(userService.getUserByPrincipal(userPrincipal));
    }

    @PostMapping("/car")
    public ResponseEntity<?> addCar(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @RequestParam String carState
    ) {
        int carAddAccept = userService.addCar(userPrincipal, carState);

        Map<String, Object> response = new HashMap<>();
        switch (carAddAccept) {
            case 0:
                response.put("status", "success");
                response.put("message", "Car is added");
                return ResponseEntity.ok(response);
            case 1:
                response.put("status", "error");
                response.put("message", "Car with this car state already exists");
                return ResponseEntity.badRequest().body(response);
            default:
                response.put("status", "error");
                response.put("message", "Unknown error occurred");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @DeleteMapping("/car")
    public ResponseEntity<?> deleteCar(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @RequestParam String carState
    ) {
        int carDeleteAccept = userService.deleteCar(userPrincipal, carState);

        Map<String, Object> response = new HashMap<>();
        switch (carDeleteAccept) {
            case 0:
                response.put("status", "success");
                response.put("message", "Car is deleted");
                return ResponseEntity.ok(response);
            case 1:
                response.put("status", "error");
                response.put("message", "Car with this car state does not exist");
                return ResponseEntity.badRequest().body(response);
            case 2:
                response.put("status", "error");
                response.put("message", "User must have at least one car");
                return ResponseEntity.badRequest().body(response);
            default:
                response.put("status", "error");
                response.put("message", "Unknown error occurred");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

}
