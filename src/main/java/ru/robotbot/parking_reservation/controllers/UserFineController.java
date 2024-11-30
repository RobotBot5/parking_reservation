package ru.robotbot.parking_reservation.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.robotbot.parking_reservation.domain.entities.FineEntity;
import ru.robotbot.parking_reservation.security.UserPrincipal;
import ru.robotbot.parking_reservation.services.FineService;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/fines")
@RequiredArgsConstructor
public class UserFineController {

    private final FineService fineService;

    @GetMapping
    public ResponseEntity<FineEntity> getCurrentFine(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        Optional<FineEntity> fineByUser = fineService.getFineByUser(userPrincipal);
        if (fineByUser.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        FineEntity fineEntity = fineByUser.get();
        return ResponseEntity.ok(fineEntity);
    }

    @PutMapping
    public ResponseEntity<?> payAFine(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        int fineAccept = fineService.payForFine(userPrincipal);
        Map<String, Object> response = new HashMap<>();
        switch (fineAccept) {
            case 0:
                response.put("status", "success");
                response.put("message", "Fine is paid");
                return ResponseEntity.ok(response);
            case 1:
                response.put("status", "error");
                response.put("message", "User has no fine");
                return ResponseEntity.badRequest().body(response);
            case 2:
                response.put("status", "error");
                response.put("message", "Fine is already paid");
                return ResponseEntity.badRequest().body(response);
            default:
                response.put("status", "error");
                response.put("message", "Unknown error occurred");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }

    }

}
