package ru.robotbot.parking_reservation.services.impls;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.robotbot.parking_reservation.domain.entities.FineEntity;
import ru.robotbot.parking_reservation.domain.entities.UserEntity;
import ru.robotbot.parking_reservation.repositories.FineRepository;
import ru.robotbot.parking_reservation.repositories.UserRepository;
import ru.robotbot.parking_reservation.security.UserPrincipal;
import ru.robotbot.parking_reservation.services.FineService;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class FineServiceImpl implements FineService {

    private final FineRepository fineRepository;
    private final UserRepository userRepository;

    @Override
    public Optional<FineEntity> getFineByUser(UserPrincipal userPrincipal) {
        UserEntity userEntity = userRepository
                .findById(userPrincipal.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        return fineRepository.findByUser(userEntity);
    }

    @Override
    public int payForFine(UserPrincipal userPrincipal) {
        UserEntity userEntity = userRepository
                .findById(userPrincipal.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        Optional<FineEntity> fineByUserFromDB = fineRepository.findByUser(userEntity);
        if (fineByUserFromDB.isEmpty()) {
            return 1; // User has no fine
        }
        FineEntity fineEntity = fineByUserFromDB.get();
        if (fineEntity.getIsPaid()) {
            return 2; // Fine is already paid
        }
        fineRepository.updateIsPaid(userEntity);
        return 0;
    }
}
