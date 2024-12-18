package ru.robotbot.parking_reservation.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.robotbot.parking_reservation.domain.dto.UserDto;
import ru.robotbot.parking_reservation.domain.dto.LoginRequest;
import ru.robotbot.parking_reservation.domain.dto.LoginResponse;
import ru.robotbot.parking_reservation.services.AuthService;
import ru.robotbot.parking_reservation.services.UserService;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public LoginResponse login(@RequestBody @Validated LoginRequest request) {
        return authService.attemptLogin(request.getPhoneNumber(), request.getPassword());
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody @Validated UserDto userDto) {

        if (userService.isUserExistsByPhoneNumber(userDto.getPhoneNumber())) {
            return ResponseEntity.badRequest().body("Phone number already in use");
        }

        userService.addUser(userDto, passwordEncoder);
        return ResponseEntity.ok("User registered successfully");
    }

}
