package ru.robotbot.parking_reservation.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.robotbot.parking_reservation.security.UserPrincipal;

@RestController
@RequiredArgsConstructor
public class HelloController {

    @GetMapping("/")
    public String hello() {
        return "Hello World!";
    }

//    @GetMapping("/secured")
//    public String secured(@AuthenticationPrincipal UserPrincipal principal) {
//        return "If you see this, then you logged in as user " + principal.getEmail()
//                + " User ID: " + principal.getUserId();
//    }
}
