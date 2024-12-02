package ru.robotbot.parking_reservation.services.impls;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.robotbot.parking_reservation.domain.dto.UserDto;
import ru.robotbot.parking_reservation.domain.dto.UserInfoResponse;
import ru.robotbot.parking_reservation.domain.entities.UserEntity;
import ru.robotbot.parking_reservation.mappers.UserMapper;
import ru.robotbot.parking_reservation.repositories.UserRepository;
import ru.robotbot.parking_reservation.security.UserPrincipal;
import ru.robotbot.parking_reservation.services.UserService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final UserMapper<UserDto, UserEntity, UserInfoResponse> userMapper;

    @Override
    public void addUser(UserDto userDto, PasswordEncoder passwordEncoder) {
        UserEntity userEntity = userMapper.mapDtoToEntity(userDto);
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
        userRepository.save(userEntity);
    }

    @Override
    public boolean isUserExistsByPhoneNumber(String phoneNumber) {
        return userRepository.findByPhoneNumber(phoneNumber).isPresent();
    }

    @Override
    public boolean isUserExistsById(Long id) {
        return userRepository.findById(id).isPresent();
    }

    @Override
    public Optional<UserEntity> getUserByPhoneNumber(String phoneNumber) {
        return userRepository.findByPhoneNumber(phoneNumber);
    }

    @Override
    public UserInfoResponse getUserByPrincipal(UserPrincipal principal) {
        Optional<UserEntity> userFromDb = userRepository.findById(principal.getUserId());
        if (userFromDb.isEmpty()) throw new RuntimeException("User not found");
        return userMapper.mapEntityToResponse(userFromDb.get());
    }

    @Override
    public int addCar(UserPrincipal userPrincipal, String carState) {
        Optional<UserEntity> userFromDb = userRepository.findById(userPrincipal.getUserId());
        if (userFromDb.isEmpty()) throw new RuntimeException("User not found");
        UserEntity userEntity = userFromDb.get();
        List<String> carStates = userEntity.getCarStates();
        if (carStates.contains(carState)) {
            return 1;
        }
        carStates.add(carState);
        userRepository.save(userEntity);
        return 0;
    }

    @Override
    public int deleteCar(UserPrincipal userPrincipal, String carState) {
        Optional<UserEntity> userFromDb = userRepository.findById(userPrincipal.getUserId());
        if (userFromDb.isEmpty()) throw new RuntimeException("User not found");
        UserEntity userEntity = userFromDb.get();
        List<String> carStates = userEntity.getCarStates();
        if (!carStates.contains(carState))
            return 1;
        if (carStates.size() == 1)
            return 2;
        carStates.remove(carState);
        userRepository.save(userEntity);
        return 0;
    }
}
