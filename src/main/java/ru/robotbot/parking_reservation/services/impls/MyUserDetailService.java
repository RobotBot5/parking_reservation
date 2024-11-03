package ru.robotbot.parking_reservation.services.impls;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.robotbot.parking_reservation.config.MyUserDetails;
import ru.robotbot.parking_reservation.domain.entities.UserEntity;
import ru.robotbot.parking_reservation.repositories.UserRepository;

import java.util.Optional;

@Service
public class MyUserDetailService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

//    public MyUserDetailService(UserRepository userRepository) {
//        this.userRepository = userRepository;
//    }

    @Override
    public UserDetails loadUserByUsername(String phoneNumber) throws UsernameNotFoundException {
        Optional<UserEntity> userEntity = userRepository.findByPhoneNumber(phoneNumber);
        return userEntity.map(MyUserDetails::new).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
