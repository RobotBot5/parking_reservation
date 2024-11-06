package ru.robotbot.parking_reservation.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import ru.robotbot.parking_reservation.services.UserService;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    private final UserService userService;


    @Override
    public UserDetails loadUserByUsername(String phoneNumber) throws UsernameNotFoundException {
        var user = userService.getUserByPhoneNumber(phoneNumber).orElseThrow(() -> new UsernameNotFoundException(phoneNumber));
        System.out.println(user.getPhoneNumber() + " " + user.getPassword());
        return UserPrincipal.builder()
                .userId(user.getId())
                .phoneNumber(user.getPhoneNumber())
                .authorities(List.of(new SimpleGrantedAuthority(user.getRole())))
                .password(user.getPassword())
                .build();
    }
}
