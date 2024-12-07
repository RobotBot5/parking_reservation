package ru.robotbot.parking_reservation.services.impls;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.robotbot.parking_reservation.domain.entities.FineEntity;
import ru.robotbot.parking_reservation.domain.entities.UserEntity;
import ru.robotbot.parking_reservation.repositories.FineRepository;
import ru.robotbot.parking_reservation.repositories.UserRepository;
import ru.robotbot.parking_reservation.security.UserPrincipal;
import ru.robotbot.parking_reservation.services.FineService;

import java.time.LocalDateTime;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class FineServiceImpl implements FineService {

    private final FineRepository fineRepository;
    private final UserRepository userRepository;

    @Override
    public FineEntity getFineByUser(UserPrincipal userPrincipal) {
        UserEntity userEntity = userRepository
                .findById(userPrincipal.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        return userEntity.getFine();
    }

    @Override
    public int payForFine(UserPrincipal userPrincipal) {
        UserEntity userEntity = userRepository
                .findById(userPrincipal.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        FineEntity fineEntity = userEntity.getFine();
        if (fineEntity == null) {
            return 1; // User has no fine
        }
        if (fineEntity.getIsPaid()) {
            return 2; // Fine is already paid
        }
        fineRepository.updateIsPaid(fineEntity.getId(), LocalDateTime.now().plusMinutes(2));
        return 0;
    }
}
