package ru.robotbot.parking_reservation.services.impls;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.robotbot.parking_reservation.domain.entities.FineEntity;
import ru.robotbot.parking_reservation.domain.entities.ReservationEntity;
import ru.robotbot.parking_reservation.domain.entities.UserEntity;
import ru.robotbot.parking_reservation.domain.enums.ReservationType;
import ru.robotbot.parking_reservation.repositories.FineRepository;
import ru.robotbot.parking_reservation.repositories.ReservationRepository;
import ru.robotbot.parking_reservation.repositories.UserRepository;
import ru.robotbot.parking_reservation.services.CameraService;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CameraServiceImpl implements CameraService {

    private final UserRepository userRepository;
    private final ReservationRepository reservationRepository;
    private final FineRepository fineRepository;

    @Override
    public int canOpenBarrierAtEntry(String stateNumber) {
        Optional<UserEntity> userEntityFromDB = userRepository.findByCarState(stateNumber);
        if (userEntityFromDB.isEmpty()) {
            return 4; // User not found
        }
        UserEntity userEntity = userEntityFromDB.get();
        Optional<ReservationEntity> reservationEntityFromDB = reservationRepository.findByUserEntityAndReservationType(userEntity, ReservationType.ACTIVE);
        if (reservationEntityFromDB.isEmpty())
            return 1; // Reservation doesn't exist
        ReservationEntity reservationEntity = reservationEntityFromDB.get();
        if (LocalDateTime.now().until(reservationEntity.getStartTime(), ChronoUnit.MINUTES) > 15) {
            return 2; // > 30 min before reservation
        }
        if (!reservationEntity.getIsPaid()) {
            return 3; // Reservation doesn't paid
        }
        return 0;
    }

    @Override
    public int canOpenBarrierAtExit(String stateNumber) {
        Optional<UserEntity> userEntityFromDB = userRepository.findByCarState(stateNumber);
        if (userEntityFromDB.isEmpty()) {
            return 4; // User not found
        }
        UserEntity userEntity = userEntityFromDB.get();
        Optional<ReservationEntity> reservationEntityFromDB = reservationRepository
                .findByUserEntityAndReservationType(userEntity, ReservationType.ACTIVE);
        if (reservationEntityFromDB.isEmpty())
            return 1; // Reservation doesn't exist
        ReservationEntity reservationEntity = reservationEntityFromDB.get();
        FineEntity fine = userEntity.getFine();
        if (fine != null) {
            if (fine.getIsPaid()) {
                userEntity.setFine(null);
                userRepository.save(userEntity);
                fineRepository.delete(fine);
                reservationRepository.updateTypeReservations(ReservationType.EXPIRED, userEntity);
                return 0;
            } else {
                return 5; // Pay a fine!
            }
        } else if (reservationEntity.getIsExtendedMustPay()) {
            return 6;
        }
        reservationRepository.updateTypeReservations(ReservationType.EXPIRED, userEntity);
        return 0;
    }
}
