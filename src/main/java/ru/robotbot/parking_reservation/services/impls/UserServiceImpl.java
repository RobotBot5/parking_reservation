package ru.robotbot.parking_reservation.services.impls;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.robotbot.parking_reservation.domain.dto.UserDto;
import ru.robotbot.parking_reservation.domain.entities.UserEntity;
import ru.robotbot.parking_reservation.mappers.Mapper;
import ru.robotbot.parking_reservation.repositories.UserRepository;
import ru.robotbot.parking_reservation.services.UserService;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final Mapper<UserEntity, UserDto> userMapper;

    @Override
    public void addUser(UserDto userDto, PasswordEncoder passwordEncoder) {
        UserEntity userEntity = userMapper.mapFrom(userDto);
        userEntity.setRole("ROLE_USER");
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
        userRepository.save(userEntity);
    }

    @Override
    public boolean isUserExists(String phoneNumber) {
        return userRepository.findByPhoneNumber(phoneNumber).isPresent();
    }

    @Override
    public Optional<UserEntity> getUserByPhoneNumber(String phoneNumber) {
        System.out.println(userRepository.findByPhoneNumber(phoneNumber));
        return userRepository.findByPhoneNumber(phoneNumber);
    }
}
